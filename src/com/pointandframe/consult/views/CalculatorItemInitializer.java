package com.pointandframe.consult.views;

import com.pointandframe.consult.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class CalculatorItemInitializer {

	private CalculatorItem item;

	public CalculatorItemInitializer(CalculatorItem item) {
		this.item = item;
	}
	
	public void inflate(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(item.getLayoutId(), (ViewGroup) item, true);
	}

	public void setAttr(Context context, AttributeSet attrs) {
		String titleText;
		boolean titleAllCaps;
		String unitText;
		
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.CalculatorItem, 0, 0);
			titleText = a.getString(R.styleable.CalculatorItem_labelText);
			unitText = a.getString(R.styleable.CalculatorItem_unitText);
			titleAllCaps = a.getBoolean(
					R.styleable.CalculatorItem_labelAllCaps, true);
			a.recycle();
		} else {
			titleText = context.getString(R.string.dummy_label);
			titleAllCaps = true;
			unitText = context.getString(R.string.dummy_unit);
		}
		
		if (item.getLabel() != null) {
			item.getLabel().setText(titleText);
			item.getLabel().setAllCaps(titleAllCaps);
		}
		
		if (item.getUnit() != null) {
			item.getUnit().setText(unitText);
		}
	}
}
