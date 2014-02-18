package com.pointandframe.consult;

public enum LengthUnit {
	INCH("in"), CENTIMETER("cm");

	private String label;

	private LengthUnit(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return this.label;
	}
}