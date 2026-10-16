package edu.psu.se411.lab09_inventory.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic, type-safe inventory holding objects of type T.
 */
public class Inventory<T> {

	private final List<T> items = new ArrayList<>();

	public void addItem(T item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}
		items.add(item);
	}

	/** @return true if the item was in the inventory and has been removed */
	public boolean removeItem(T item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}
		return items.remove(item);
	}

	/** Returns a copy so callers cannot modify the inventory directly. */
	public List<T> getItems() {
		return new ArrayList<>(items);
	}

	public int size() {
		return items.size();
	}

	/**
	 * Strategy pattern: returns every item that matches the given search strategy.
	 * "? super T" lets a strategy written for a supertype be reused,
	 * e.g. a SearchStrategy<Item> also works on an Inventory<Book>.
	 */
	public List<T> findItems(SearchStrategy<? super T> strategy) {
		if (strategy == null) {
			throw new IllegalArgumentException("Search strategy cannot be null");
		}
		List<T> result = new ArrayList<>();
		for (T item : items) {
			if (strategy.matches(item)) {
				result.add(item);
			}
		}
		return result;
	}
}
