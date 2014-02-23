package com.pointandframe.consult.views;

public interface CalculatorItemNumeric extends CalculatorItem {
	
	public float getValue(float defaltValue);
	public void setValue(float f);
	public void setValue(String formatString, float f);

}
