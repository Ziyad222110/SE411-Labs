package edu.psu.se411.lab09_inventory.model;

/**
 * Book strategy: matches books whose author contains the given text (case-insensitive).
 * Typed on Item so it can be used on a mixed Inventory<Item>; non-books never match.
 */
public class SearchByAuthor implements SearchStrategy<Item> {

	private final String author;

	public SearchByAuthor(String author) {
		if (author == null || author.isEmpty()) {
			throw new IllegalArgumentException("Author cannot be null or empty");
		}
		this.author = author.toLowerCase();
	}

	@Override
	public boolean matches(Item item) {
		return item instanceof Book
				&& ((Book) item).getAuthor().toLowerCase().contains(author);
	}
}
