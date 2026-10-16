package edu.psu.se411.lab09_inventory.model;

/**
 * ElectronicDevice strategy: matches devices of the given category.
 * Typed on Item so it can be used on a mixed Inventory<Item>; non-devices never match.
 */
public class SearchByCategory implements SearchStrategy<Item> {

	private final ECategory category;

	public SearchByCategory(ECategory category) {
		if (category == null) {
			throw new IllegalArgumentException("Category cannot be null");
		}
		this.category = category;
	}

	@Override
	public boolean matches(Item item) {
		return item instanceof ElectronicDevice
				&& ((ElectronicDevice) item).getCategory() == category;
	}
}
