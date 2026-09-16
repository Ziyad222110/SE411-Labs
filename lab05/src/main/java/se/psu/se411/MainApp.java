package se.psu.se411;
import se.psu.se411.exception.InsufficientFundsException;
import se.psu.se411.exception.InvalidAgeException;

public class MainApp {
	public static void main(String[] args) {
		try {
			validateAge(17);
		} catch (InvalidAgeException e) {
			e.printStackTrace();
		}

		Wallet wallet = new Wallet(100.0);
		try {
			wallet.withdrawToBank(150.0);
		} catch (InsufficientFundsException e) {
			System.out.println("Caught exception: " + e.getMessage());
		}

		try {
			wallet.withdrawToBank(50.0);
		} catch (InsufficientFundsException e) {
			System.out.println("Caught exception: " + e.getMessage());
		}
	}

	public static boolean validateAge(Integer age) throws InvalidAgeException {
		if (age < Config.MIN_AGE) {
			throw new InvalidAgeException(age);
		}
		System.out.println("Age valid message.");
		return true;
	}
}