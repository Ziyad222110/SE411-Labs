package edu.psu.se411.lab09_inventory.model;

/** Item strategy: matches the item with the given id. */
public class SearchById implements SearchStrategy<Item> {

	private final String id;

	public SearchById(String id) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException("Id cannot be null or empty");
		}
		this.id = id;
	}

	@Override
	public boolean matches(Item item) {
		return item != null && item.getId().equals(id);
	}
}
