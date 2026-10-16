package edu.psu.se411.lab09_inventory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab09_inventory.model.Book;
import edu.psu.se411.lab09_inventory.model.ECategory;
import edu.psu.se411.lab09_inventory.model.ElectronicDevice;
import edu.psu.se411.lab09_inventory.model.Inventory;
import edu.psu.se411.lab09_inventory.model.Item;
import edu.psu.se411.lab09_inventory.model.SearchByAuthor;
import edu.psu.se411.lab09_inventory.model.SearchByCategory;
import edu.psu.se411.lab09_inventory.model.SearchById;
import edu.psu.se411.lab09_inventory.model.SearchByName;

public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		logger.info("Application is starting...");

		try {
			// ---------- Part 1: generic inventory holding books AND devices ----------
			Inventory<Item> inventory = new Inventory<>();

			inventory.addItem(new Book("1", "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565"));
			inventory.addItem(new Book("2", "To Kill a Mockingbird", "Harper Lee", "9780061120084"));
			inventory.addItem(new Book("3", "1984", "George Orwell", "9780451524935"));
			inventory.addItem(new Book("4", "Animal Farm", "George Orwell", "9780451526342"));
			inventory.addItem(new Book("5", "Concurrency in Java", "Brian Goetz", "9780321349606"));
			inventory.addItem(new Book("6", "The Hobbit", "J.R.R. Tolkien", "9780547928227"));

			inventory.addItem(new ElectronicDevice("7", "MacBook Pro", ECategory.LAPTOP));
			inventory.addItem(new ElectronicDevice("8", "ThinkPad X1", ECategory.LAPTOP));
			inventory.addItem(new ElectronicDevice("9", "Galaxy S25", ECategory.PHONE));
			inventory.addItem(new ElectronicDevice("10", "AirPods Pro", ECategory.AUDIO));
			logger.info("Inventory loaded with {} items", inventory.size());

			// ---------- Part 3: display any inventory ----------
			displayInventory(inventory);

			// remove an item
			Item hobbit = inventory.findItems(new SearchById("6")).get(0);
			inventory.removeItem(hobbit);
			System.out.println("\nRemoved: " + hobbit.getName());
			logger.info("Removed item {}", hobbit.getId());

			// ---------- Part 2: searching with different strategies ----------
			printResults("Search by name \"Concurrency in Java\"",
					inventory.findItems(new SearchByName("Concurrency in Java")));
			printResults("Search by name \"Galaxy S25\"",
					inventory.findItems(new SearchByName("Galaxy S25")));
			printResults("Search by id \"3\"",
					inventory.findItems(new SearchById("3")));
			printResults("Search books by author \"Orwell\"",
					inventory.findItems(new SearchByAuthor("Orwell")));
			printResults("Search devices by category LAPTOP",
					inventory.findItems(new SearchByCategory(ECategory.LAPTOP)));
			printResults("Search by name \"The Hobbit\" (was removed)",
					inventory.findItems(new SearchByName("The Hobbit")));

			// A strategy can also be written as a lambda (SearchStrategy has one method)
			printResults("Lambda strategy: names starting with \"The\"",
					inventory.findItems(item -> item.getName().startsWith("The")));

			// ---------- Generics: a books-only inventory reuses the same strategies ----------
			Inventory<Book> bookShelf = new Inventory<>();
			bookShelf.addItem(new Book("B1", "Clean Code", "Robert C. Martin", "9780132350884"));
			bookShelf.addItem(new Book("B2", "Clean Architecture", "Robert C. Martin", "9780134494166"));
			displayInventory(bookShelf);   // works for Inventory<Book> too (Inventory<?>)
			printResults("Book shelf: search by author \"Martin\"",
					bookShelf.findItems(new SearchByAuthor("Martin")));

			// ---------- Exception handling: invalid input is caught and logged ----------
			try {
				inventory.addItem(new ElectronicDevice("11", "Unknown gadget", null));
			} catch (IllegalArgumentException e) {
				System.out.println("\nCould not add item: " + e.getMessage());
				logger.error("Failed to add item: {}", e.getMessage(), e);
			}

		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
			logger.error("Unexpected exception", e);
		} finally {
			logger.info("Application is closing...");
		}
	}

	/** Part 3: prints the details of all items in ANY inventory, whatever its type. */
	public static void displayInventory(Inventory<?> inventory) {
		System.out.printf("%n===== Inventory (%d items) =====%n", inventory.size());
		for (Object item : inventory.getItems()) {
			System.out.println(item);   // calls the overridden toString() of each item
		}
	}

	private static void printResults(String title, List<? extends Item> results) {
		System.out.printf("%n%s -> %d result(s)%n", title, results.size());
		for (Item item : results) {
			System.out.println("   " + item);
		}
		logger.info("{} -> {} result(s)", title, results.size());
	}
}
