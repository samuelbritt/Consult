package com.pointandframe.consult.views;

import com.pointandframe.consult.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class CalculatorItemEditText extends RelativeLayout {
	EditText value;
	OnEditorActionListener listener;

	public CalculatorItemEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CalculatorItemEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {
			setupView(context, attrs);
		}
	}
	
	public void setOnEditorActionListenter(OnEditorActionListener listener) {
		this.listener = listener;
		value.setOnEditorActionListener(listener);
	}

	private void setupView(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,
		        R.styleable.CalculatorItem, 0, 0);
	    String titleText = a.getString(R.styleable.CalculatorItem_labelText);
	    String unitText = a.getString(R.styleable.CalculatorItem_unitText);
	    a.recycle();
	    
	    LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        inflater.inflate(R.layout.calculator_item_edit_text, this, true);
	    
	    TextView label = (TextView) findViewById(R.id.label);
	    label.setText(titleText);
	    TextView unit = (TextView) findViewById(R.id.unit);
	    unit.setText(unitText);
	    
	    value = (EditText) findViewById(R.id.value);
	}

	public CalculatorItemEditText(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

}
