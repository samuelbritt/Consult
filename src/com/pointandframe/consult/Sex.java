package com.pointandframe.consult;

public enum Sex {
	MALE("Male"), FEMALE("Female");

	private String label;

	private Sex(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return this.label;
	}
}
