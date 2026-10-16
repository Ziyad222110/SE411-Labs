package edu.psu.se411.lab09_inventory.model;

public class Book extends Item {

	private String author;
	private String isbn;

	public Book(String id, String name, String author, String isbn) {
		super(id, name);
		setAuthor(author);   // use the setters so validation also applies on creation
		setIsbn(isbn);
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		if (author == null || author.isEmpty()) {
			throw new IllegalArgumentException("Author cannot be null or empty");
		}
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		if (isbn == null || isbn.isEmpty()) {
			throw new IllegalArgumentException("ISBN cannot be null or empty");
		}
		this.isbn = isbn;
	}

	@Override
	protected String details() {
		return "author=" + author + ", isbn=" + isbn;
	}
}
