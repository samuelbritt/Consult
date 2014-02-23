package com.pointandframe.consult.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.pointandframe.consult.R;
import com.pointandframe.consult.views.KeyChangeListenerEditText.OnKeyChangeListener;

public class CalculatorItemEditText extends RelativeLayout implements
		CalculatorItem, OnFocusChangeListener, OnEditorActionListener,
		OnKeyChangeListener {
	private static final String TAG = "CalculatorItemEditText";
	private static final int LAYOUT_ID = R.layout.calculator_item_edit_text;
	protected OnCalculatorItemChangeListener onCalculatorItemChangeListener;
	protected KeyChangeListenerEditText value;
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

	protected void setupView(Context context, AttributeSet attrs) {
		CalculatorItemInitializer init = new CalculatorItemInitializer(this);
		init.inflate(context);

		label = (TextView) findViewById(R.id.label);
		unit = (TextView) findViewById(R.id.unit);
		value = (KeyChangeListenerEditText) findViewById(R.id.value);

		init.setAttr(context, attrs);

		value.setOnFocusChangeListener(this);
		value.setOnEditorActionListener(this);
		value.setOnKeyChangeListener(this);
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

	private void notifyListener() {
		if (onCalculatorItemChangeListener != null) {
			onCalculatorItemChangeListener.onCalculatorItemChange(this);
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.value:
			if (!hasFocus) {
				notifyListener();
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
				notifyListener();
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

	@Override
	public void onKeyChange(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_UP) {
			notifyListener();
		}
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
