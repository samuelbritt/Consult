package com.pointandframe.consult.views;

import com.pointandframe.consult.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
		CharSequence titleText;
		int titleVisibility;
		boolean titleAllCaps;
		CharSequence unitText;

		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.CalculatorItem, 0, 0);
			unitText = a.getText(R.styleable.CalculatorItem_unitText);
			titleText = a.getText(R.styleable.CalculatorItem_labelText);
			titleVisibility = a.getInt(
					R.styleable.CalculatorItem_labelVisibility, 1);
			titleAllCaps = a.getBoolean(
					R.styleable.CalculatorItem_labelAllCaps, true);
			a.recycle();
		} else {
			titleText = context.getString(R.string.dummy_label);
			titleAllCaps = true;

			titleVisibility = 1;
			unitText = context.getString(R.string.dummy_unit);
		}

		if (item.getLabel() != null) {
			item.getLabel().setText(titleText);
			item.getLabel().setAllCaps(titleAllCaps);
			item.getLabel().setVisibility(getViewVisibility(titleVisibility));
		}

		if (item.getUnit() != null) {
			item.getUnit().setText(unitText);
		}
	}

	private int getViewVisibility(int v) {
		switch (v) {
		case 2:
			return View.INVISIBLE;
		case 3:
			return View.GONE;
		case 1:
		default:
			return View.VISIBLE;
		}
	}
}
