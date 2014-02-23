package com.pointandframe.consult.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pointandframe.consult.R;

public class CalculatorItemTextView extends RelativeLayout implements
		CalculatorItemNumeric {

	protected TextView value;
	private TextView label;
	private TextView unit;
	private static final int LAYOUT_ID = R.layout.calculator_item_output;

	public CalculatorItemTextView(Context context) {
		super(context);
		if (!isInEditMode()) {
			setupView(context, null);
		}
	}

	public CalculatorItemTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {
			setupView(context, attrs);
		}
	}

	public CalculatorItemTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		if (!isInEditMode()) {
			setupView(context, attrs);
		}
	}

	protected void setupView(Context context, AttributeSet attrs) {
		CalculatorItemInitializer init = new CalculatorItemInitializer(this);
		init.inflate(context);

		label = (TextView) findViewById(R.id.label);
		unit = (TextView) findViewById(R.id.unit);
		value = (TextView) findViewById(R.id.value);

		init.setAttr(context, attrs);
	}

	@Override
	public String getValueText() {
		return value.getText().toString();
	}

	public float getValue() {
		return getValue(0.0f);
	}

	@Override
	public float getValue(float defaultVal) {
		try {
			return Float.parseFloat(getValueText());
		} catch (NumberFormatException e) {
			return defaultVal;
		}
	}

	@Override
	public void setValueText(String s) {
		value.setText(s);
	}

	@Override
	public void setValue(float f) {
		setValue("%.2f", f);
	}

	@Override
	public void setValue(String formatString, float f) {
		value.setText(String.format(formatString, f));
	}

	@Override
	public void clearValue() {
		value.setText("");
	}

	@Override
	public int getLayoutId() {
		return LAYOUT_ID;
	}

	@Override
	public TextView getLabel() {
		return label;
	}

	@Override
	public TextView getUnit() {
		return unit;
	}
}
