package com.pointandframe.consult.views;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.pointandframe.consult.R;
import com.pointandframe.consult.views.KeyChangeListenerEditText.OnKeyChangeListener;

public class CalculatorItemEditText extends RelativeLayout implements
		CalculatorItemNumeric, OnFocusChangeListener, OnEditorActionListener,
		OnKeyChangeListener {
	@SuppressWarnings("unused")
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
		value.getText().clear();
	}

	@Override
	public boolean isEmpty() {
		return TextUtils.isEmpty(value.getText().toString());
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
		if (!hasFocus && v.getId() == R.id.value) {
			notifyListener();
		}
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		switch (v.getId()) {
		case R.id.value:
			if (actionId == EditorInfo.IME_ACTION_DONE
					|| actionId == EditorInfo.IME_ACTION_NEXT) {
				notifyListener();
				if (actionId == EditorInfo.IME_ACTION_DONE
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					InputMethodManager in = (InputMethodManager) getContext()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					in.hideSoftInputFromWindow(v.getApplicationWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
					return true;
				}
			}
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
	public Parcelable onSaveInstanceState() {

		Bundle bundle = new Bundle();
		bundle.putParcelable("instanceState", super.onSaveInstanceState());
		if (value != null)
			bundle.putString("value", getValueText());
		return bundle;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {

		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			setValueText(bundle.getString("value"));
			state = bundle.getParcelable("instanceState");
		}
		super.onRestoreInstanceState(state);
	}
}
