package edu.psu.se411.lab12_threads;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * a) Main program 2: CACHED thread pool.
 * A cached pool creates a new thread whenever no idle thread is available,
 * so the number of threads is not bounded.
 */
public class AppCachedPool {

	static Logger logger = LoggerFactory.getLogger(AppCachedPool.class);

	public static Integer nbTransactions = 1_000_000;

	public static void main(String[] args) {
		logger.info("Application (cached thread pool) is starting...");

		long t1 = Simulation.run("Cached pool", Executors.newCachedThreadPool(), 1000, nbTransactions);
		long t2 = Simulation.run("Cached pool", Executors.newCachedThreadPool(), 10, nbTransactions);

		logger.info("Cached pool: 1000 accounts = {} ms, 10 accounts = {} ms", t1, t2);
		logger.info("Application (cached thread pool) is closing...");
	}
}
