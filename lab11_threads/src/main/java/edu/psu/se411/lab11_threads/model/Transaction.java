package edu.psu.se411.lab11_threads.model;

import java.util.concurrent.atomic.AtomicInteger;

import edu.psu.se411.lab11_threads.exceptions.InsufficientBalanceException;

public class Transaction implements Runnable {

	/** false = Part 1 (no synchronization), true = Part 2 (synchronized). Set by App. */
	public static boolean useSynchronization = false;

	private static final AtomicInteger counter = new AtomicInteger();

	private String id;
	private CryptoAccount sender;
	private CryptoAccount receiver;
	private Double amount;

	/** Constructor used by the lab: new Transaction(randomA, randomB, randomAmount) */
	public Transaction(CryptoAccount sender, CryptoAccount receiver, Double amount) {
		this("txn_" + counter.getAndIncrement(), sender, receiver, amount);
	}

	public Transaction(String id, CryptoAccount sender, CryptoAccount receiver, Double amount) {
		if (id == null || sender == null || receiver == null || amount == null || amount < 0)
			throw new IllegalArgumentException();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
	}

	public void execute() throws InsufficientBalanceException {
		if (useSynchronization) {
			transferSynchronized(sender, receiver, amount);
		} else {
			transfer(sender, receiver, amount);
		}
	}

	/** Part 1: unsafe. Many threads can run this at the same time. */
	private static void transfer(CryptoAccount sender, CryptoAccount receiver, Double amount)
			throws InsufficientBalanceException {
		if (sender.getBalance() >= amount) {
			sender.retrieveFromBalance(amount);
			receiver.addToBalance(amount);
		} else {
			throw new InsufficientBalanceException("Insufficient balance in " + sender.getId()
					+ " to pay " + receiver.getId() + " amount " + amount);
		}
	}

	/**
	 * Part 2: the ONE synchronized method.
	 * It is static because every payment is a new Transaction object: an instance
	 * synchronized method would lock a different object each time and protect nothing.
	 * A static synchronized method locks Transaction.class, so only one transfer
	 * runs at a time and the total stays the same.
	 */
	private static synchronized void transferSynchronized(CryptoAccount sender, CryptoAccount receiver,
			Double amount) throws InsufficientBalanceException {
		transfer(sender, receiver, amount);
	}

	@Override
	public void run() {
		try {
			execute();
		} catch (InsufficientBalanceException e) {
			System.err.println("Transaction " + id + " failed: " + e.getMessage());
		}
	}

	public CryptoAccount getSender() { return sender; }
	public CryptoAccount getReceiver() { return receiver; }
	public Double getAmount() { return amount; }
	public String getId() { return id; }
}
