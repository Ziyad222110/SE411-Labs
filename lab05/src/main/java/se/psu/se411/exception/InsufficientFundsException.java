package se.psu.se411.exception;

public class InsufficientFundsException extends Exception {
	private double requestedAmount;
	private double availableBalance;

	public InsufficientFundsException(double requestedAmount, double availableBalance) {
		super("Insufficient funds. Requested: " + requestedAmount + ", available: " + availableBalance);
		this.requestedAmount = requestedAmount;
		this.availableBalance = availableBalance;
	}

	public double getRequestedAmount() {
		return requestedAmount;
	}

	public double getAvailableBalance() {
		return availableBalance;
	}
}