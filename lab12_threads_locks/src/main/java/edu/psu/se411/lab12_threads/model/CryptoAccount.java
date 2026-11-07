package edu.psu.se411.lab12_threads.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import edu.psu.se411.lab12_threads.exceptions.InsufficientBalanceException;

public class CryptoAccount implements Comparable<CryptoAccount> {
	private final String id;
	private Double balance;
	private final Lock lock = new ReentrantLock();   // b) one lock per account (replaces "synchronized")

	public CryptoAccount(String id, Double balance) {
		if (balance < 0) throw new IllegalArgumentException("Account balance cannot be negative");
		this.id = id;
		this.balance = balance;
	}

	public Lock getLock() {
		return lock;
	}

	public void addToBalance(Double amount) {
		if (amount < 0) throw new IllegalArgumentException("Amount added to balance cannot be negative");
		balance += amount;
	}

	public void retrieveFromBalance(Double amount) throws InsufficientBalanceException {
		if (amount > balance) throw new InsufficientBalanceException(id);
		balance -= amount;
	}

	public String getId() {
		return id;
	}

	public Double getBalance() {
		return balance;
	}

	/** d) Total order on accounts, based on the id. */
	@Override
	public int compareTo(CryptoAccount o) {
		return this.id.compareTo(o.id);
	}
}
