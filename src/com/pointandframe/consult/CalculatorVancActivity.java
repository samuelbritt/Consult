package com.pointandframe.consult;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

//import android.view.View;

public class CalculatorVancActivity extends Activity implements Observer,
		TextView.OnEditorActionListener, Spinner.OnItemSelectedListener,
		OnCheckedChangeListener, OnFocusChangeListener, OnSeekBarChangeListener {

	private Patient patient;
	private DosingRegimin regimin;
	private VancCalculator calculator;
	private EditText inputHtValue;
	private Spinner inputHtUnit;
	private EditText inputAgeValue;
	private EditText inputWtValue;
	private RadioButton inputSexMale;
	private RadioButton inputSexFemale;
	private TextView outputHtInches;
	private TextView outputKel;
	private TextView outputHalflife;
	private TextView outputVdNormal;
	private TextView outputVdHypoalbumenic;
	private TextView outputCminNormal;
	private TextView outputCminHypoalbumenic;
	private TextView outputCmaxNormal;
	private TextView outputCmaxHypoalbumenic;
	private SeekBar inputDose;
	private SeekBar inputDoseInterval;
	private TextView inputDoseValue;
	private TextView inputDoseIntervalValue;

	private static int[] validDoses = { 250, 500, 750, 1000, 1250, 1750, 2000,
			2250, 2500, 2750, 3000 };
	private static int[] validDosingIntervals = { 8, 12, 16, 18, 24, 36, 72, 96 };

	private boolean atStartup = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator_vanc);

		patient = new Patient();
		regimin = new DosingRegimin();
		calculator = new VancCalculator();
		calculator.setPatient(patient);
		calculator.setDosingRegimin(regimin);
		patient.registerObserver(this);
		regimin.registerObserver(this);

		View e = findViewById(R.id.input_ht_value);
		inputHtValue = (EditText) e;
		inputHtUnit = (Spinner) findViewById(R.id.ht_unit_options);
		inputAgeValue = (EditText) findViewById(R.id.input_age_value);
		inputWtValue = (EditText) findViewById(R.id.input_wt_value);
		inputSexMale = (RadioButton) findViewById(R.id.radioMale);
		inputSexFemale = (RadioButton) findViewById(R.id.radioFemale);
		inputDose = (SeekBar) findViewById(R.id.input_dose_seek);
		inputDoseValue = (TextView) findViewById(R.id.input_dose_value);
		inputDoseInterval = (SeekBar) findViewById(R.id.input_dose_interval_seek);
		inputDoseIntervalValue = (TextView) findViewById(R.id.input_dose_interval_value);

		outputHtInches = ((TextView) findViewById(R.id.output_ht_inches_value));
		outputKel = ((TextView) findViewById(R.id.output_kel_value));
		outputHalflife = ((TextView) findViewById(R.id.output_halflife_value));
		outputVdNormal = ((TextView) findViewById(R.id.output_Vd_value));
		outputVdHypoalbumenic = ((TextView) findViewById(R.id.output_Vd_hypoalbumenic_value));
		outputCminNormal = ((TextView) findViewById(R.id.output_Cmin_value));
		outputCminHypoalbumenic = ((TextView) findViewById(R.id.output_Cmin_hypoalbumenic_value));
		outputCmaxNormal = ((TextView) findViewById(R.id.output_Cmax_value));
		outputCmaxHypoalbumenic = ((TextView) findViewById(R.id.output_Cmax_hypoalbumenic_value));

		outputVdHypoalbumenic.setVisibility(View.GONE);
		outputCminHypoalbumenic.setVisibility(View.GONE);
		outputCmaxHypoalbumenic.setVisibility(View.GONE);

		inputHtValue.setOnEditorActionListener(this);
		inputHtValue.setOnFocusChangeListener(this);
		inputAgeValue.setOnEditorActionListener(this);
		inputAgeValue.setOnFocusChangeListener(this);
		inputWtValue.setOnEditorActionListener(this);
		inputWtValue.setOnFocusChangeListener(this);
		inputSexMale.setOnCheckedChangeListener(this);
		inputSexFemale.setOnCheckedChangeListener(this);

		inputHtUnit.setOnItemSelectedListener(this);
		ArrayAdapter<LengthUnit> adapter = new ArrayAdapter<LengthUnit>(this,
				R.layout.calculator_spinner_item, LengthUnit.values());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inputHtUnit.setAdapter(adapter);

		inputDose.setOnSeekBarChangeListener(this);
		inputDose.setMax(validDoses.length - 1);
		inputDose.setProgress((int) (validDoses.length / 2));

		inputDoseInterval.setOnSeekBarChangeListener(this);
		inputDoseInterval.setMax(validDosingIntervals.length - 1);
		inputDoseInterval.setProgress((int) (validDosingIntervals.length / 2));

		atStartup = true;
		inputAgeValue.requestFocus();
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus) {
			updateModel((TextView) v);
		}
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_DONE
				|| actionId == EditorInfo.IME_ACTION_NEXT) {
			updateModel(v);
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
		switch (parent.getId()) {
		case R.id.ht_unit_options:
			setPatientHeight();
			if (atStartup) {
				atStartup = false;
			} else {
				inputHtValue.requestFocus();
			}
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.input_dose_seek:
			inputDoseValue.setText(String.valueOf(validDoses[progress]) + " " + getResources().getString(R.string.input_dose_unit));
			regimin.setDose_mg(validDoses[progress]);
			break;
		case R.id.input_dose_interval_seek:
			inputDoseIntervalValue.setText(String
					.valueOf(validDosingIntervals[progress])  + " " + getResources().getString(R.string.input_dose_interval_unit));
			regimin.setDosingInterval_hr(validDosingIntervals[progress]);
			break;
		default:
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCheckedChanged(CompoundButton view, boolean isChecked) {
		if (isChecked) {
			switch (view.getId()) {
			case R.id.radioMale:
				patient.setMale(true);
				break;
			case R.id.radioFemale:
				patient.setMale(false);
				break;
			}
		}
	}

	private void updateModel(TextView v) {
		switch (v.getId()) {
		case R.id.input_age_value:
			patient.setAge(viewToInt(v));
			break;
		case R.id.input_wt_value:
			patient.setActualBodyWeight(viewToFloat(v));
			break;
		case R.id.input_ht_value:
			setPatientHeight();
			break;
		case R.id.input_SCr_value:
			patient.setSCr(viewToFloat(v));
			break;
		}
	}

	@Override
	public void notify(Observable o) {
		outputHtInches.setText(String.format("%.2f", patient.getHeight_in()));
		outputKel.setText(String.format("%.3f", calculator.getKElimination()));
		outputHalflife.setText(String.format("%.2f", calculator.getHalflife()));

		float vd = calculator.getNormalVd();
		outputVdNormal.setText(String.format("%.2f", vd));
		outputCminNormal.setText(String.format("%.2f", calculator.getCmin(vd)));
		outputCmaxNormal.setText(String.format("%.2f", calculator.getCmax(vd)));

		if (patient.inHypoAlbumenicState()) {
			vd = calculator.getHypoAlbumenicVd();
			outputVdHypoalbumenic.setText(String.format("%.2f", vd));
			outputCminHypoalbumenic.setText(String.format("%.2f",
					calculator.getCmin(vd)));
			outputCmaxHypoalbumenic.setText(String.format("%.2f",
					calculator.getCmax(vd)));
			outputVdHypoalbumenic.setVisibility(View.VISIBLE);
			outputCminHypoalbumenic.setVisibility(View.VISIBLE);
			outputCmaxHypoalbumenic.setVisibility(View.VISIBLE);
		} else {
			outputVdHypoalbumenic.setVisibility(View.GONE);
			outputCminHypoalbumenic.setVisibility(View.GONE);
			outputCmaxHypoalbumenic.setVisibility(View.GONE);
		}
	}

	private void setPatientHeight() {
		float ht_val = viewToFloat(inputHtValue);
		LengthUnit units = (LengthUnit) inputHtUnit.getSelectedItem();
		switch (units) {
		case CENTIMETER:
			patient.setHeight_cm(ht_val);
			break;
		case INCH:
			patient.setHeight_in(ht_val);
			break;
		}
	}

	private float viewToFloat(TextView v) {
		String valStr = v.getText().toString();
		float defaultVal = 0.0f;
		float val;
		try {
			val = Float.parseFloat(valStr);
		} catch (NumberFormatException e) {
			val = defaultVal;
		}
		return val;
	}

	private int viewToInt(TextView v) {
		String valStr = v.getText().toString();
		int defaultVal = 0;
		int val;
		try {
			val = Integer.parseInt(valStr);
		} catch (NumberFormatException e) {
			val = defaultVal;
		}
		return val;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator_vanc, menu);
		return true;
	}

}
