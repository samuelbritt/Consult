package com.pointandframe.consult.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class KeyChangeListenerEditText extends EditText {
	public interface OnKeyChangeListener {
		public void onKeyChange(int keyCode, KeyEvent event);
	}

	private OnKeyChangeListener onKeyChangeListener;
	@SuppressWarnings("unused")
	private static final String TAG = "KeyChangeListenerEditText";

	public KeyChangeListenerEditText(Context context) {
		super(context);
	}

	public KeyChangeListenerEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public KeyChangeListenerEditText(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOnKeyChangeListener(OnKeyChangeListener listener) {
		onKeyChangeListener = listener;
	}

	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
		boolean val = super.onKeyPreIme(keyCode, event);
		if (onKeyChangeListener != null) {
			onKeyChangeListener.onKeyChange(keyCode, event);
		}
		return val;
	}
}
