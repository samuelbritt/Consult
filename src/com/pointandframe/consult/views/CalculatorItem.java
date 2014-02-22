package com.pointandframe.consult.views;

public interface CalculatorItem {
	public interface OnCalculatorItemChangeListener {
		public void onCalculatorItemChange(CalculatorItem v);
	}

	public String getText();

	public void setText(String s);
}
