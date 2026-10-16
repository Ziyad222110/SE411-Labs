package edu.psu.se411.lab09_inventory.model;

/**
 * Strategy interface: defines how to check whether an item matches a criteria.
 */
public interface SearchStrategy<T> {
	boolean matches(T item);
}
