package edu.psu.se411.lab12_threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import edu.psu.se411.lab12_threads.model.CryptoAccount;
import edu.psu.se411.lab12_threads.model.Transaction;

/** Shared code used by App (fixed pool) and AppCachedPool (cached pool). */
public class Simulation {

	public static final Double INITIAL_BALANCE = 10000.0;

	/** Runs nbTransactions random transactions on the given pool and returns the time in ms. */
	public static long run(String label, ExecutorService executor, int nbAccounts, int nbTransactions) {
		// 1- create accounts
		CryptoAccount[] accounts = new CryptoAccount[nbAccounts];
		for (int i = 0; i < nbAccounts; i++) {
			accounts[i] = new CryptoAccount("acc_" + i, INITIAL_BALANCE);
		}
		double before = sumTotalMoneyInAccounts(accounts);

		long startTime = System.currentTimeMillis();

		// 2- submit random transactions between two different accounts
		for (int i = 0; i < nbTransactions; i++) {
			int senderIndex = (int) (Math.random() * nbAccounts);
			int receiverIndex = (int) (Math.random() * nbAccounts);
			if (senderIndex == receiverIndex) {
				senderIndex = (senderIndex + 1) % nbAccounts;
			}
			Double amount = Math.random() * 100;
			executor.execute(new Transaction("id_" + i, accounts[senderIndex], accounts[receiverIndex], amount));
		}

		// 3- wait for all transactions to complete
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		long time = System.currentTimeMillis() - startTime;

		// 4- check total money in system
		double after = sumTotalMoneyInAccounts(accounts);
		System.out.printf("%-12s | %5d accounts | %,d transactions | %6d ms | total before %.2f, after %.2f%n",
				label, nbAccounts, nbTransactions, time, before, after);
		return time;
	}

	public static Double sumTotalMoneyInAccounts(CryptoAccount[] accounts) {
		Double total = 0.0;
		for (CryptoAccount c : accounts) {
			total += c.getBalance();
		}
		return total;
	}
}
