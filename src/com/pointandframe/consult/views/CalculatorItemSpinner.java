package com.pointandframe.consult.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pointandframe.consult.R;

public class CalculatorItemSpinner extends RelativeLayout implements
		CalculatorItem, OnItemSelectedListener {
	@SuppressWarnings("unused")
	private static final String TAG = "CalculatorItemEditText";
	private static final int LAYOUT_ID = R.layout.calculator_item_spinner;
	protected Spinner value;
	protected OnCalculatorItemChangeListener onCalculatorItemChangeListener;
	protected TextView label;

	public CalculatorItemSpinner(Context context) {
		super(context);
		if (!isInEditMode()) {
			setupView(context, null);
		}
	}

	public CalculatorItemSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {
			setupView(context, attrs);
		}
	}

	public CalculatorItemSpinner(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		if (!isInEditMode()) {
			setupView(context, attrs);
		}
	}

	private void setupView(Context context, AttributeSet attrs) {
		CalculatorItemInitializer init = new CalculatorItemInitializer(this);
		init.inflate(context);

		label = (TextView) findViewById(R.id.label);
		value = (Spinner) findViewById(R.id.value_options);

		init.setAttr(context, attrs);

		value.setOnItemSelectedListener(this);
	}

	@Override
	public String getValueText() {
		return value.getSelectedItem().toString();
	}

	public Object getSelectedItem() {
		return value.getSelectedItem();
	}

	@Override
	public void setValueText(String s) {
	}
	
	@Override
	public void clearValue() {
		value.setSelection(0);
	}

	public <T> void setOptions(T[] spinnerOptions) {
		ArrayAdapter<T> adapter = new ArrayAdapter<T>(getContext(),
				R.layout.calculator_spinner_item_value, spinnerOptions);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		value.setAdapter(adapter);
	}

	public void setOnCalculatorItemChangeListener(
			OnCalculatorItemChangeListener listener) {
		onCalculatorItemChangeListener = listener;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
		switch (parent.getId()) {
		case R.id.value_options:
			onCalculatorItemChangeListener.onCalculatorItemChange(this);
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
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
		return null;
	}
}
