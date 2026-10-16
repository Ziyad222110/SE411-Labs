package edu.psu.se411.lab09_inventory.model;

/** Item strategy: matches any item whose name equals the given name (case-insensitive). */
public class SearchByName implements SearchStrategy<Item> {

	private final String name;

	public SearchByName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		this.name = name;
	}

	@Override
	public boolean matches(Item item) {
		return item != null && item.getName().equalsIgnoreCase(name);
	}
}
