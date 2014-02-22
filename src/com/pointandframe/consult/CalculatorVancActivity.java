package com.pointandframe.consult;

import java.util.HashMap;

import com.pointandframe.consult.model.DosingRegimen;
import com.pointandframe.consult.model.IDrug;
import com.pointandframe.consult.model.LengthUnit;
import com.pointandframe.consult.model.PKCalculator;
import com.pointandframe.consult.model.Patient;
import com.pointandframe.consult.model.Sex;
import com.pointandframe.consult.model.Vancomycin;
import com.pointandframe.consult.util.IObservable;
import com.pointandframe.consult.util.IObserver;
import com.pointandframe.consult.views.CalculatorItemEditText;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class CalculatorVancActivity extends Activity implements IObserver,
		TextView.OnEditorActionListener, Spinner.OnItemSelectedListener,
		OnFocusChangeListener, OnSeekBarChangeListener, OnClickListener {

	private Patient patient;
	private DosingRegimen regimen;
	private IDrug drug;
	private PKCalculator calculator;
	private EditText inputHtValue;
	private Spinner inputHtUnit;
	private EditText inputAgeValue;
	private EditText inputWtValue;
	private Spinner inputSex;
	HashMap<SeekBar, int[]> seekBarProgressArrays;

	private SeekBar inputDose;
	private SeekBar inputDoseInterval;
	private TextView inputDoseValue;
	private TextView inputDoseIntervalValue;

	private CheckBox inputIsDiabetic;
	private CheckBox inputIsBedridden;
	private CheckBox inputInICU;
	private CheckBox inputIsPregnant;
	private CheckBox inputHasAcuteRenalFailure;
	private CheckBox inputHasEndStageRenalDisease;
	private CheckBox inputHasCancer;
	private CheckBox inputHasAids;
	private CheckBox inputIsParaplegic;
	private CheckBox inputIsQuadriplegic;

	private TextView outputHtInches;
	private TextView outputKel;
	private TextView outputCrCl;
	private TextView outputHalflife;
	private TextView outputVdNormal;
	private TextView outputVdHypoalbumenic;
	private TextView outputCminNormal;
	private TextView outputCminHypoalbumenic;
	private TextView outputCmaxNormal;
	private TextView outputCmaxHypoalbumenic;

	private static String TAG = "CalculatorVancActivity";
	private boolean atStartup = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator_vanc);

		patient = new Patient();
		regimen = new DosingRegimen();
		drug = new Vancomycin();
		calculator = new PKCalculator(patient, drug, regimen);
		patient.registerObserver(this);
		regimen.registerObserver(this);

		inputHtValue = (EditText) findViewById(R.id.input_ht_value);
		inputHtUnit = (Spinner) findViewById(R.id.ht_unit_options);
		inputSex = (Spinner) findViewById(R.id.input_sex_spinner);
		inputAgeValue = (EditText) findViewById(R.id.input_age_value);
		inputWtValue = (EditText) findViewById(R.id.input_wt_value);
		inputDose = (SeekBar) findViewById(R.id.input_dose_seek);
		inputDoseValue = (TextView) findViewById(R.id.input_dose_value);
		inputDoseInterval = (SeekBar) findViewById(R.id.input_dose_interval_seek);
		inputDoseIntervalValue = (TextView) findViewById(R.id.input_dose_interval_value);

		inputIsDiabetic = ((CheckBox) findViewById(R.id.input_isDiabetic));
		inputIsBedridden = ((CheckBox) findViewById(R.id.input_isBedridden));
		inputInICU = ((CheckBox) findViewById(R.id.input_inICU));
		inputIsPregnant = ((CheckBox) findViewById(R.id.input_isPregnant));
		inputHasAcuteRenalFailure = ((CheckBox) findViewById(R.id.input_hasAcuteRenalFailure));
		inputHasEndStageRenalDisease = ((CheckBox) findViewById(R.id.input_hasEndStageRenalDisease));
		inputHasCancer = ((CheckBox) findViewById(R.id.input_hasCancer));
		inputHasAids = ((CheckBox) findViewById(R.id.input_hasAids));
		inputIsParaplegic = ((CheckBox) findViewById(R.id.input_isParaplegic));
		inputIsQuadriplegic = ((CheckBox) findViewById(R.id.input_isQuadriplegic));

		outputHtInches = ((TextView) findViewById(R.id.output_ht_inches_value));
		outputKel = ((TextView) findViewById(R.id.output_kel_value));
		outputHalflife = ((TextView) findViewById(R.id.output_halflife_value));
		outputCrCl = ((TextView) findViewById(R.id.output_CrCl_value));
		outputVdNormal = ((TextView) findViewById(R.id.output_Vd_value));
		outputVdHypoalbumenic = ((TextView) findViewById(R.id.output_Vd_hypoalbumenic_value));
		outputCminNormal = ((TextView) findViewById(R.id.output_Cmin_value));
		outputCminHypoalbumenic = ((TextView) findViewById(R.id.output_Cmin_hypoalbumenic_value));
		outputCmaxNormal = ((TextView) findViewById(R.id.output_Cmax_value));
		outputCmaxHypoalbumenic = ((TextView) findViewById(R.id.output_Cmax_hypoalbumenic_value));

		inputHtValue.setOnEditorActionListener(this);
		inputHtValue.setOnFocusChangeListener(this);
		inputAgeValue.setOnEditorActionListener(this);
		inputAgeValue.setOnFocusChangeListener(this);
		inputWtValue.setOnEditorActionListener(this);
		inputWtValue.setOnFocusChangeListener(this);
		inputIsDiabetic.setOnClickListener(this);
		inputIsBedridden.setOnClickListener(this);
		inputInICU.setOnClickListener(this);
		inputIsPregnant.setOnClickListener(this);
		inputHasAcuteRenalFailure.setOnClickListener(this);
		inputHasEndStageRenalDisease.setOnClickListener(this);
		inputHasCancer.setOnClickListener(this);
		inputHasAids.setOnClickListener(this);
		inputIsParaplegic.setOnClickListener(this);
		inputIsQuadriplegic.setOnClickListener(this);

		CalculatorItemEditText foo = (CalculatorItemEditText) findViewById(R.id.foo);
		foo.setOnEditorActionListenter(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				Log.d(TAG, "custom view clicked. Value: " + v.getText());
				return false;
			}
		});

		inputHtUnit.setOnItemSelectedListener(this);
		initSpinner(inputHtUnit, R.layout.calculator_spinner_item_unit,
				LengthUnit.values());

		inputSex.setOnItemSelectedListener(this);
		initSpinner(inputSex, R.layout.calculator_spinner_item_value,
				Sex.values());

		inputDose.setOnSeekBarChangeListener(this);
		initSeekBar(inputDose, drug.getValidDoses());

		inputDoseInterval.setOnSeekBarChangeListener(this);
		initSeekBar(inputDoseInterval, drug.getValidDosingIntervals());

		atStartup = true;
	}

	private <T> void initSpinner(Spinner spinner, int layoutId, T[] array) {
		ArrayAdapter<T> adapter = new ArrayAdapter<T>(this, layoutId, array);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	private void initSeekBar(SeekBar seekBar, int[] progressArray) {
		if (seekBarProgressArrays == null) {
			seekBarProgressArrays = new HashMap<SeekBar, int[]>();
		}
		seekBarProgressArrays.put(seekBar, progressArray);
		setSeekBarMax(seekBar);
		setSeekBarDefault(seekBar);
	}

	private void setSeekBarMax(SeekBar seekBar) {
		int[] progress = seekBarProgressArrays.get(seekBar);
		seekBar.setMax(progress.length - 1);
	}

	private void setSeekBarDefault(SeekBar seekBar) {
		int[] progress = seekBarProgressArrays.get(seekBar);
		seekBar.setProgress((int) (progress.length / 2.0));
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
			if (atStartup) {
				atStartup = false;
			} else {
				inputHtValue.requestFocus();
			}
			break;
		case R.id.input_sex_spinner:
			Sex sex = (Sex) parent.getSelectedItem();
			if (sex == Sex.MALE) {
				inputIsPregnant.setChecked(false);
				inputIsPregnant.setEnabled(false);
			} else {
				inputIsPregnant.setEnabled(true);
			}
			break;
		}
		updateModel(parent);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.input_dose_seek:
			inputDoseValue
					.setText(String.valueOf(drug.getValidDoses()[progress]));
			regimen.setDose_mg(drug.getValidDoses()[progress]);
			break;
		case R.id.input_dose_interval_seek:
			inputDoseIntervalValue.setText(String.valueOf(drug
					.getValidDosingIntervals()[progress]));
			regimen.setDosingInterval_hr(drug.getValidDosingIntervals()[progress]);
			break;
		default:
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onClick(View v) {
		updateModel(v);
	}

	private void updateModel(View v) {
		switch (v.getId()) {
		case R.id.input_age_value:
			patient.setAge(viewToInt((TextView) v));
			break;
		case R.id.input_wt_value:
			patient.setActualBodyWeight(viewToFloat((TextView) v));
			break;
		case R.id.input_ht_value:
			setPatientHeight();
			break;
		case R.id.input_SCr_value:
			patient.setSCr(viewToFloat((TextView) v));
			break;
		case R.id.input_sex_spinner:
			patient.setSex((Sex) inputSex.getSelectedItem());
		case R.id.input_isDiabetic:
			patient.setDiabetic(inputIsDiabetic.isChecked());
			break;
		case R.id.input_isBedridden:
			patient.setBedridden(inputIsBedridden.isChecked());
			break;
		case R.id.input_inICU:
			patient.setIcu(inputInICU.isChecked());
			break;
		case R.id.input_isPregnant:
			patient.setPregnant(inputIsPregnant.isChecked());
			break;
		case R.id.input_hasAcuteRenalFailure:
			patient.setAcuteRenalFailure(inputHasAcuteRenalFailure.isChecked());
			break;
		case R.id.input_hasEndStageRenalDisease:
			patient.setEndStageRenalDisease(inputHasEndStageRenalDisease
					.isChecked());
			break;
		case R.id.input_hasCancer:
			patient.setCancer(inputHasCancer.isChecked());
			break;
		case R.id.input_hasAids:
			patient.setAids(inputHasAids.isChecked());
			break;
		case R.id.input_isParaplegic:
			patient.setParaplegic(inputIsParaplegic.isChecked());
			break;
		case R.id.input_isQuadriplegic:
			patient.setQuadriplegic(inputIsQuadriplegic.isChecked());
			break;
		}
	}

	@Override
	public void notify(IObservable o) {
		Log.d("CalculatorVanc", "Being Notified");
		updateOutputViews();
	}

	private void updateOutputViews() {
		outputHtInches.setText(String.format("%.2f", patient.getHeight_in()));
		outputKel.setText(String.format("%.3f", calculator.getKElimination()));
		outputHalflife.setText(String.format("%.2f", calculator.getHalflife()));
		outputCrCl.setText(String.format("%.2f", patient.getCrCl()));

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
			findViewById(R.id.output_hypoalbumenic).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.output_hypoalbumenic).setVisibility(View.GONE);
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

	private void clearForm(ViewGroup group) {
		{
			for (int i = 0, count = group.getChildCount(); i < count; ++i) {
				View view = group.getChildAt(i);
				if (view instanceof EditText) {
					((EditText) view).getText().clear();
				} else if (view instanceof CheckBox) {
					((CheckBox) view).setChecked(false);
				} else if (view instanceof SeekBar) {
					setSeekBarDefault((SeekBar) view);
				} else if (view instanceof Spinner) {
					((Spinner) view).setSelection(0);
				}

				if (view instanceof ViewGroup)
					clearForm((ViewGroup) view);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator_vanc, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_clear:
			clearForm((ViewGroup) findViewById(R.id.MainView));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
