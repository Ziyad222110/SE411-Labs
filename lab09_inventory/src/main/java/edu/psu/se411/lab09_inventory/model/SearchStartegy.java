package edu.psu.se411.lab09_inventory.model;

public interface SearchStartegy<T> {
	public boolean matches(T item);

}
