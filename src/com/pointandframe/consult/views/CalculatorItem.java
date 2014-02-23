package com.pointandframe.consult.views;

import android.widget.TextView;

public interface CalculatorItem {
	public interface OnCalculatorItemChangeListener {
		public void onCalculatorItemChange(CalculatorItem v);
	}

	public String getText();

	public void setText(String s);

	public void clearValue();

	public int getLayoutId();

	public TextView getLabel();

	public TextView getUnit();

}
