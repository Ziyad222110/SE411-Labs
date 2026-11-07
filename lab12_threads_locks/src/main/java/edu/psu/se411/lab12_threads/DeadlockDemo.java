package edu.psu.se411.lab12_threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab12_threads.exceptions.InsufficientBalanceException;
import edu.psu.se411.lab12_threads.model.CryptoAccount;
import edu.psu.se411.lab12_threads.model.Transaction;

/**
 * c) What could go wrong when we use locks without resource ordering?
 * This program uses Transaction.executeWithoutOrdering() and usually freezes
 * in a DEADLOCK. It waits 10 seconds, then reports it and exits.
 */
public class DeadlockDemo {

	static Logger logger = LoggerFactory.getLogger(DeadlockDemo.class);

	public static void main(String[] args) throws InterruptedException {
		logger.info("Deadlock demo is starting...");
		int nbAccounts = 10;
		int nbTransactions = 100_000;

		CryptoAccount[] accounts = new CryptoAccount[nbAccounts];
		for (int i = 0; i < nbAccounts; i++) {
			accounts[i] = new CryptoAccount("acc_" + i, Simulation.INITIAL_BALANCE);
		}

		// daemon threads so the JVM can exit even if they are stuck forever
		ExecutorService executor = Executors.newFixedThreadPool(8, r -> {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		});

		for (int i = 0; i < nbTransactions; i++) {
			int s = (int) (Math.random() * nbAccounts);
			int r = (int) (Math.random() * nbAccounts);
			if (s == r) s = (s + 1) % nbAccounts;
			Transaction t = new Transaction("id_" + i, accounts[s], accounts[r], Math.random() * 100);
			executor.execute(() -> {
				try {
					t.executeWithoutOrdering();
				} catch (InsufficientBalanceException e) {
					// refused
				}
			});
		}
		executor.shutdown();

		if (executor.awaitTermination(10, TimeUnit.SECONDS)) {
			System.out.println("Finished without deadlock this time (it is random). Run again.");
		} else {
			System.out.println("DEADLOCK: threads are still waiting for each other's locks after 10 s.");
			System.out.println("Fix: use Transaction.execute(), which locks accounts in ascending id order.");
			logger.error("Deadlock detected when locking without resource ordering");
		}
		logger.info("Deadlock demo is closing...");
	}
}
