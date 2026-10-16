package edu.psu.se411.lab09_inventory.model;

public class ElectronicDevices extends Item {
	
	private ECategory category;
	
	public ElectronicDevices(String id, String name, ECategory category) {
		super(id, name);
		setCategory(category);
	}
	
	public ECategory getCategory() {
		return category;
	}
	
	public void setCategory(ECategory category) {
		if (category == null) {
			throw new IllegalArgumentException("Category cannot be null");
		}
		this.category = category;
	}

	@Override
	protected String details() {
		// TODO Auto-generated method stub
		return null;
	}
}
	

