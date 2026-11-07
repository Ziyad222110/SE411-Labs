package edu.psu.se411.lab11_threads.model;

import edu.psu.se411.lab11_threads.exceptions.InsufficientBalanceException;

public class CryptoAccount {
	private String id;
	private Double balance;

	public CryptoAccount(String id, Double balance) {
		this.id = id;
		this.balance = balance;
	}

	/**
	 * Read -> (pause) -> write. Not atomic: two threads can read the same
	 * old balance and one of the writes gets lost.
	 * Thread.yield() only widens the window so the race shows on every run.
	 */
	public void addToBalance(Double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException();
		}
		double current = this.balance;
		Thread.yield();
		this.balance = current + amount;
	}

	public void retrieveFromBalance(Double amount) throws InsufficientBalanceException {
		if (amount < 0) {
			throw new IllegalArgumentException();
		}
		double current = this.balance;
		if (current < amount) {
			throw new InsufficientBalanceException("Insufficient balance to retrieve the requested amount");
		}
		Thread.yield();
		this.balance = current - amount;
	}

	public String getId() {
		return id;
	}

	public Double getBalance() {
		return balance;
	}

	@Override
	public String toString() {
		return String.format("%s: %.2f SAR", id, balance);
	}
}
