package se.psu.se411.exception;
import se.psu.se411.Config;

public class InvalidAgeException extends Exception {
	private Integer age;
	public InvalidAgeException(Integer age) {
		super("Age can not be less than 18. " + Config.MIN_AGE+ " , you entered: " + age);	
		 this.age = age;
	}
	public Integer getAge() {
		return age;
	}

}
