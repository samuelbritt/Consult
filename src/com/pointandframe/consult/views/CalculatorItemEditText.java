package com.pointandframe.consult.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.pointandframe.consult.R;

public class CalculatorItemEditText extends RelativeLayout implements
		CalculatorItem, OnFocusChangeListener, OnEditorActionListener {
	private static final String TAG = "CalculatorItemEditText";
	protected EditText value;
	protected OnCalculatorItemChangeListener onCalculatorItemChangeListener;
	protected TextView label;
	protected TextView unit;

	public CalculatorItemEditText(Context context) {
		super(context);
		if (!isInEditMode()) {
			setupView(context, null);
		}
	}

	public CalculatorItemEditText(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		if (!isInEditMode()) {
			setupView(context, attrs);
		}
	}

	public CalculatorItemEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {
			setupView(context, attrs);
		}
	}

	protected void inflate(Context context, int layoutRes) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(layoutRes, this, true);
	}

	protected void setAttr(Context context, AttributeSet attrs) {
		String titleText;
		String unitText;
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.CalculatorItem, 0, 0);
			titleText = a.getString(R.styleable.CalculatorItem_labelText);
			unitText = a.getString(R.styleable.CalculatorItem_unitText);
			a.recycle();
		} else {
			titleText = context.getString(R.string.dummy_label);
			unitText = context.getString(R.string.dummy_unit);
		}
		if (label != null)
			label.setText(titleText);
		if (unit != null)
			unit.setText(unitText);
	}

	protected void setupView(Context context, AttributeSet attrs) {
		inflate(context, R.layout.calculator_item_edit_text);

		label = (TextView) findViewById(R.id.label);
		unit = (TextView) findViewById(R.id.unit);
		value = (EditText) findViewById(R.id.value);

		setAttr(context, attrs);

		value.setOnFocusChangeListener(this);
		value.setOnEditorActionListener(this);
	}

	@Override
	public String getText() {
		return value.getText().toString();
	}

	@Override
	public void setText(String s) {
		value.setText(s);
	}

	public float getValue() {
		return getValue(0.0f);
	}

	public float getValue(float defaultVal) {
		String valStr = getText();
		float val;
		try {
			val = Float.parseFloat(valStr);
		} catch (NumberFormatException e) {
			val = defaultVal;
		}
		return val;
	}

	public void setOnCalculatorItemChangeListener(
			OnCalculatorItemChangeListener listener) {
		onCalculatorItemChangeListener = listener;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.value:
			if (!hasFocus) {
				onCalculatorItemChangeListener.onCalculatorItemChange(this);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		switch (v.getId()) {
		case R.id.value:
			if (actionId == EditorInfo.IME_ACTION_DONE
					|| actionId == EditorInfo.IME_ACTION_NEXT) {
				onCalculatorItemChangeListener.onCalculatorItemChange(this);
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					return true;
				}
			}
			return false;
		default:
			break;
		}
		return false;
	}

}
