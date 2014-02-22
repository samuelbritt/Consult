package com.pointandframe.consult.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pointandframe.consult.R;

public class CalculatorItemOutput extends RelativeLayout implements
		CalculatorItem {

	protected TextView value;
	private TextView label;
	private TextView unit;
	private static final int LAYOUT_ID = R.layout.calculator_item_output;

	public CalculatorItemOutput(Context context) {
		super(context);
		if (!isInEditMode()) {
			setupView(context, null);
		}
	}

	public CalculatorItemOutput(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {
			setupView(context, attrs);
		}
	}

	public CalculatorItemOutput(Context context, AttributeSet attrs,
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
	public String getText() {
		return value.getText().toString();
	}

	@Override
	public void setText(String s) {
		value.setText(s);
	}

	public void setValue(float f) {
		setValue("%.2f", f);
	}

	public void setValue(String formatString, float f) {
		value.setText(String.format("%.2f", f));
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
