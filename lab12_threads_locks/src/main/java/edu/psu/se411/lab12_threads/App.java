package edu.psu.se411.lab12_threads;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main program 1: FIXED thread pool (one thread per CPU core).
 * Build and run using Maven goal: clean package exec:java
 */
public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);

	public static Integer nbTransactions = 1_000_000;
	public static int poolSize = Runtime.getRuntime().availableProcessors();

	public static void main(String[] args) {
		logger.info("Application (fixed thread pool) is starting...");
		System.out.println("Fixed thread pool with " + poolSize + " threads");

		long t1 = Simulation.run("Fixed pool", Executors.newFixedThreadPool(poolSize), 1000, nbTransactions);
		long t2 = Simulation.run("Fixed pool", Executors.newFixedThreadPool(poolSize), 10, nbTransactions);

		logger.info("Fixed pool: 1000 accounts = {} ms, 10 accounts = {} ms", t1, t2);
		logger.info("Application (fixed thread pool) is closing...");
	}
}
