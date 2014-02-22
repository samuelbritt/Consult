package com.pointandframe.consult.views;

import com.pointandframe.consult.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalculatorItemOutput extends RelativeLayout implements
		CalculatorItem {

	protected TextView value;
	private TextView label;
	private TextView unit;

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
		inflate(context, R.layout.calculator_item_output);

		label = (TextView) findViewById(R.id.label);
		unit = (TextView) findViewById(R.id.unit);
		value = (TextView) findViewById(R.id.value);

		setAttr(context, attrs);

	}

	protected void inflate(Context context, int layoutRes) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(layoutRes, this, true);
	}

	protected void setAttr(Context context, AttributeSet attrs) {
		String titleText;
		boolean titleAllCaps;
		String unitText;
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.CalculatorItem, 0, 0);
			titleText = a.getString(R.styleable.CalculatorItem_labelText);
			titleAllCaps = a.getBoolean(
					R.styleable.CalculatorItem_labelAllCaps, true);
			unitText = a.getString(R.styleable.CalculatorItem_unitText);
			a.recycle();
		} else {
			titleText = context.getString(R.string.dummy_label);
			titleAllCaps = true;
			unitText = context.getString(R.string.dummy_unit);
		}
		if (label != null) {
			label.setText(titleText);
			label.setAllCaps(titleAllCaps);
		}
		if (unit != null) {
			unit.setText(unitText);
		}
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

}
