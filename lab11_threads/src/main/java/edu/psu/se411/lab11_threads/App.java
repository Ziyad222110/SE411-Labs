package edu.psu.se411.lab11_threads;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab11_threads.exceptions.InsufficientBalanceException;
import edu.psu.se411.lab11_threads.model.CryptoAccount;
import edu.psu.se411.lab11_threads.model.Transaction;

/**
 * Build and run using Maven goal: clean package exec:java
 * (or right-click App.java -> Run As -> Java Application)
 */
public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);

	public static Integer nbAccounts = 10;
	public static Double initialBalance = 1000.0;   // 10 x 1000 = 10000 riyals
	public static Integer nbTransactions = 10000;   // ten thousand threads

	public static void main(String[] args) {
		logger.info("Application is starting...");

		// Part 1: without synchronization -> total becomes inconsistent
		runSimulation(false);

		// Part 2: with the synchronized method -> total stays 10000
		runSimulation(true);

		logger.info("Application is closing...");
	}

	private static void runSimulation(boolean synchronizedMode) {
		String title = synchronizedMode ? "PART 2 - WITH synchronized" : "PART 1 - WITHOUT synchronization";
		System.out.println("===== " + title + " =====");
		logger.info(title);
		Transaction.useSynchronization = synchronizedMode;

		// a) ten accounts with 1000 riyals each
		List<CryptoAccount> accounts = new ArrayList<>();
		for (Integer i = 0; i < nbAccounts; i++) {
			accounts.add(new CryptoAccount("acc_" + i, initialBalance));
		}
		double totalBefore = getTotalBalance(accounts);
		System.out.printf("Total balance before transactions: %.2f%n", totalBefore);
		logger.info("Total balance before transactions: {}", totalBefore);

		// b) ten thousand threads, each making one random transaction
		Thread[] threads = new Thread[nbTransactions];
		for (int i = 0; i < nbTransactions; i++) {
			threads[i] = new Thread(() -> {
				CryptoAccount randomA = accounts.get((int) (Math.random() * 10));
				CryptoAccount randomB = accounts.get((int) (Math.random() * 10));
				Double randomAmount = (double) (Math.random() * 10 + 1);
				Transaction t = new Transaction(randomA, randomB, randomAmount);
				try {
					t.execute();
				} catch (InsufficientBalanceException e) {
					logger.error("Transaction {} failed", t.getId(), e);
				}
			});
			threads[i].start();
		}

		// wait for all transactions to finish
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				logger.error("Interrupted while waiting for {}", thread.getName(), e);
				Thread.currentThread().interrupt();
			}
		}

		// c) total money after the ten thousand transactions
		accounts.forEach(System.out::println);
		double totalAfter = getTotalBalance(accounts);
		System.out.printf("Total balance after transactions:  %.2f%n", totalAfter);
		logger.info("Total balance after transactions: {}", totalAfter);

		if (Math.abs(totalAfter - totalBefore) < 0.0001) {
			System.out.println("OK: total balance is preserved.");
			logger.info("Total balance is preserved");
		} else {
			System.out.println("ERROR: total balance changed! (race condition)");
			logger.error("Total balance changed: before={}, after={}", totalBefore, totalAfter);
		}
		System.out.println();
	}

	/** c) Sums the balances of all accounts. */
	public static double getTotalBalance(List<CryptoAccount> accounts) {
		double total = 0;
		for (CryptoAccount account : accounts) {
			total += account.getBalance();
		}
		return total;
	}
}
