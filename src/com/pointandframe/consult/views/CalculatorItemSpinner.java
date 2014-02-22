package com.pointandframe.consult.views;

import com.pointandframe.consult.R;
import com.pointandframe.consult.views.CalculatorItem.OnCalculatorItemChangeListener;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class CalculatorItemSpinner extends RelativeLayout implements
		CalculatorItem, OnItemSelectedListener {
	private static final String TAG = "CalculatorItemEditText";
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
		inflate(context, R.layout.calculator_item_spinner);

		label = (TextView) findViewById(R.id.label);
		value = (Spinner) findViewById(R.id.value);

		setAttr(context, attrs);

		value.setOnItemSelectedListener(this);
	}

	protected void inflate(Context context, int layoutRes) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(layoutRes, this, true);
	}

	private void setAttr(Context context, AttributeSet attrs) {
		String titleText;
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.CalculatorItem, 0, 0);
			titleText = a.getString(R.styleable.CalculatorItem_labelText);
			a.recycle();
		} else {
			titleText = context.getString(R.string.dummy_label);
		}
		if (label != null)
			label.setText(titleText);
	}

	@Override
	public String getText() {
		return value.getSelectedItem().toString();
	}
	
	public Object getSelectedItem() {
		return value.getSelectedItem();
	}

	@Override
	public void setText(String s) {
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
		case R.id.value:
			onCalculatorItemChangeListener.onCalculatorItemChange(this);
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}
}
