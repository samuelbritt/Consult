package com.pointandframe.consult.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.pointandframe.consult.R;

public class CalculatorItemEditTextUnitSpinner extends CalculatorItemEditText
		implements OnItemSelectedListener {

	@SuppressWarnings("unused")
	private static final String TAG = "CalculatorItemEditTextUnitSpinner";
	private static final int LAYOUT_ID = R.layout.calculator_item_edit_text_unit_spinner;
	private Spinner unitSpinner;
	private boolean atStartup;

	public CalculatorItemEditTextUnitSpinner(Context context) {
		super(context);

	}

	public CalculatorItemEditTextUnitSpinner(Context context,
			AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public CalculatorItemEditTextUnitSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	protected void setupView(Context context, AttributeSet attrs) {
		CalculatorItemInitializer init = new CalculatorItemInitializer(this);
		init.inflate(context);

		label = (TextView) findViewById(R.id.label);
		value = (KeyChangeListenerEditText) findViewById(R.id.value);
		unitSpinner = (Spinner) findViewById(R.id.unit_options);

		init.setAttr(context, attrs);

		value.setOnFocusChangeListener(this);
		value.setOnEditorActionListener(this);
		value.setOnKeyChangeListener(this);
		unitSpinner.setOnItemSelectedListener(this);
		atStartup = true;
	}

	public Object getSelectedUnit() {
		return unitSpinner.getSelectedItem();
	}

	public <T> void setUnitOptions(T[] spinnerOptions) {
		ArrayAdapter<T> adapter = new ArrayAdapter<T>(getContext(),
				R.layout.calculator_spinner_item_unit, spinnerOptions);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		unitSpinner.setAdapter(adapter);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
		switch (parent.getId()) {
		case R.id.unit_options:
			onCalculatorItemChangeListener.onCalculatorItemChange(this);
			if (atStartup) {
				atStartup = false;
			} else {
				value.requestFocus();
			}
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

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
