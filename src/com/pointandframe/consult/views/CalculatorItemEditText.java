package com.pointandframe.consult.views;

import com.pointandframe.consult.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class CalculatorItemEditText extends RelativeLayout implements CalculatorItem, OnEditorActionListener, OnFocusChangeListener {
	private static final String TAG = "CalculatorItemEditText";
	private EditText value;
	private com.pointandframe.consult.views.CalculatorItemEditText.OnCalculatorItemChangeListener onCalculatorItemChangeListener;
	
	public interface OnCalculatorItemChangeListener {
		public void onCalculatorItemChange(CalculatorItemEditText v);
	}

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

	public void setOnCalculatorItemChangeListener(OnCalculatorItemChangeListener listener) {
		onCalculatorItemChangeListener = listener;
	}

	private void setupView(Context context, AttributeSet attrs) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.calculator_item_edit_text, this, true);

		TextView label = (TextView) findViewById(R.id.label);
		TextView unit = (TextView) findViewById(R.id.unit);
		value = (EditText) findViewById(R.id.value);
		value.setOnFocusChangeListener(this);
		value.setOnEditorActionListener(this);
		
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.CalculatorItem, 0, 0);
			String titleText = a
					.getString(R.styleable.CalculatorItem_labelText);
			String unitText = a.getString(R.styleable.CalculatorItem_unitText);
			a.recycle();
			label.setText(titleText);
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

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.value:
			Log.d(TAG, "On Focus Change");
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
			Log.d(TAG, "On Editor Action");
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
