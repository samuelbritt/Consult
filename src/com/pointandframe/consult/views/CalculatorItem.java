package com.pointandframe.consult.views;

import android.widget.TextView;

public interface CalculatorItem {
	public interface OnCalculatorItemChangeListener {
		public void onCalculatorItemChange(CalculatorItem v);
	}

	public String getValueText();

	public void setValueText(String s);

	public void clearValue();

	public boolean isEmpty();

	public int getLayoutId();

	public TextView getLabel();

	public TextView getUnit();

}
