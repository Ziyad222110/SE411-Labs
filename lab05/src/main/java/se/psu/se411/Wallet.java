package se.psu.se411;

import se.psu.se411.exception.InsufficientFundsException;

public class Wallet {
	private double balance;

	public Wallet(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void withdrawToBank(double amount) throws InsufficientFundsException {
		if (amount > balance) {
			throw new InsufficientFundsException(amount, balance);
		}
		balance -= amount;
		System.out.println("Withdrew " + amount + " to bank account. Remaining balance: " + balance);
	}
}