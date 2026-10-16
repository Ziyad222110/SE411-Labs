package edu.psu.se411.lab09_inventory.model;

/**
 * Common base for everything stored in the inventory.
 */
public abstract class Item {

	private final String id;
	private String name;

	protected Item(String id, String name) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException("Id cannot be null or empty");
		}
		this.id = id;
		setName(name);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		this.name = name;
	}

	/** Each subclass adds its own details (polymorphism, used by displayInventory). */
	protected abstract String details();

	@Override
	public String toString() {
		return String.format("%-17s id=%-4s name=%-28s %s",
				getClass().getSimpleName(), id, name, details());
	}
}
