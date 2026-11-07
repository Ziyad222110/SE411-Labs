package edu.psu.se411.lab12_threads.model;

import edu.psu.se411.lab12_threads.exceptions.InsufficientBalanceException;

public class Transaction implements Runnable {
	private String id;
	private CryptoAccount sender;
	private CryptoAccount receiver;
	private Double amount;

	public Transaction(String id, CryptoAccount sender, CryptoAccount receiver, Double amount) {
		if (id == null || sender == null || receiver == null || amount == null || amount < 0)
			throw new IllegalArgumentException();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
	}

	@Override
	public void run() {
		try {
			execute();
		} catch (InsufficientBalanceException e) {
			// transaction refused, money is not moved
		}
	}

	/**
	 * d) SOLUTION - resource ordering.
	 * Locks are always taken in ascending order of account id, whatever the
	 * direction of the payment. Two threads can never hold one lock each while
	 * waiting for the other's, so a deadlock is impossible.
	 */
	public void execute() throws InsufficientBalanceException {
		if (sender == receiver)
			throw new IllegalArgumentException("Sender and receiver must be different accounts");

		CryptoAccount first = sender.compareTo(receiver) < 0 ? sender : receiver;
		CryptoAccount second = (first == sender) ? receiver : sender;

		first.getLock().lock();
		try {
			second.getLock().lock();
			try {
				transfer();
			} finally {
				second.getLock().unlock();
			}
		} finally {
			first.getLock().unlock(); // always released, even if an exception is thrown
		}
	}

	/**
	 * b/c) Locks WITHOUT resource ordering: lock sender, then receiver.
	 * What can go wrong: DEADLOCK.
	 *   Thread 1 pays A -> B: locks A, waits for B
	 *   Thread 2 pays B -> A: locks B, waits for A
	 * Each thread holds the lock the other one needs, so both wait forever.
	 * Used only by DeadlockDemo.
	 */
	public void executeWithoutOrdering() throws InsufficientBalanceException {
		if (sender == receiver)
			throw new IllegalArgumentException("Sender and receiver must be different accounts");

		sender.getLock().lock();
		try {
			Thread.yield(); // widens the window between the two lock() calls
			receiver.getLock().lock();
			try {
				transfer();
			} finally {
				receiver.getLock().unlock();
			}
		} finally {
			sender.getLock().unlock();
		}
	}

	/** Moves the money. Must be called while holding both accounts' locks. */
	private void transfer() throws InsufficientBalanceException {
		if (sender.getBalance() < amount) throw new InsufficientBalanceException(sender.getId());
		sender.retrieveFromBalance(amount);
		receiver.addToBalance(amount);
	}

	public CryptoAccount getSender() { return sender; }
	public CryptoAccount getReceiver() { return receiver; }
	public Double getAmount() { return amount; }
	public String getId() { return id; }
}
