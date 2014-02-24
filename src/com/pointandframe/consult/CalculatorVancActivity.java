package com.pointandframe.consult;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.pointandframe.consult.model.DosingRegimen;
import com.pointandframe.consult.model.IDrug;
import com.pointandframe.consult.model.LengthUnit;
import com.pointandframe.consult.model.PKCalculator;
import com.pointandframe.consult.model.Patient;
import com.pointandframe.consult.model.Sex;
import com.pointandframe.consult.model.Vancomycin;
import com.pointandframe.consult.util.IObservable;
import com.pointandframe.consult.util.IObserver;
import com.pointandframe.consult.views.CalculatorItem;
import com.pointandframe.consult.views.CalculatorItemEditText;
import com.pointandframe.consult.views.CalculatorItemEditTextUnitSpinner;
import com.pointandframe.consult.views.CalculatorItemSpinner;
import com.pointandframe.consult.views.CalculatorItemTextView;

public class CalculatorVancActivity extends Activity implements IObserver,
		OnSeekBarChangeListener, OnClickListener,
		CalculatorItem.OnCalculatorItemChangeListener {

	// Model
	private Patient patient;
	private DosingRegimen regimen;
	private IDrug drug;
	private PKCalculator calculator;

	// All inputs
	View[] inputs;

	// Patient Detail Input
	private CalculatorItemEditText inputAge;
	private CalculatorItemSpinner inputSex;
	private CalculatorItemEditTextUnitSpinner inputHt;
	private CalculatorItemEditText inputWt;
	private CalculatorItemEditText inputSCr;

	private CheckBox inputIsBedridden;
	private CheckBox inputInICU;
	private CheckBox inputIsPregnant;
	private CheckBox inputHasAcuteRenalFailure;
	private CheckBox inputHasEndStageRenalDisease;
	private CheckBox inputHasCancer;
	private CheckBox inputHasAids;
	private CheckBox inputIsParaplegic;
	private CheckBox inputIsQuadriplegic;

	// Dosing Regimen Input
	private SeekBar inputDose;
	private SeekBar inputDoseInterval;
	private TextView inputDoseValue;
	private TextView inputDoseIntervalValue;
	HashMap<SeekBar, int[]> seekBarProgressArrays;

	// Outputs
	private CalculatorItemTextView outputHtInches;
	private CalculatorItemTextView outputKel;
	private CalculatorItemTextView outputCrCl;
	private CalculatorItemTextView outputHalflife;
	private CalculatorItemTextView outputVdNormal;
	private CalculatorItemTextView outputVdHypoalbumenic;
	private CalculatorItemTextView outputCminNormal;
	private CalculatorItemTextView outputCminHypoalbumenic;
	private CalculatorItemTextView outputCmaxNormal;
	private CalculatorItemTextView outputCmaxHypoalbumenic;

	// Other fields
	@SuppressWarnings("unused")
	private final static String TAG = "CalculatorVancActivity";
	private final static String KEY_PATIENT = "PATIENT";
	private final static String KEY_DOSING_REGIMIN = "DOSING_REGIMIN";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator_vanc);

		// Model
		if (savedInstanceState != null) {
			patient = (Patient) savedInstanceState.getParcelable(KEY_PATIENT);
			regimen = (DosingRegimen) savedInstanceState
					.getParcelable(KEY_DOSING_REGIMIN);
		} else {
			patient = new Patient();
			regimen = new DosingRegimen();
		}

		drug = new Vancomycin();
		calculator = new PKCalculator(patient, drug, regimen);
		patient.registerObserver(this);
		regimen.registerObserver(this);

		// Get Fields
		// Input Patient Details
		inputAge = (CalculatorItemEditText) findViewById(R.id.input_age);
		inputSex = (CalculatorItemSpinner) findViewById(R.id.input_sex);
		inputHt = (CalculatorItemEditTextUnitSpinner) findViewById(R.id.input_ht);
		inputWt = (CalculatorItemEditText) findViewById(R.id.input_wt);
		inputSCr = (CalculatorItemEditText) findViewById(R.id.input_SCr);

		inputIsBedridden = ((CheckBox) findViewById(R.id.input_isBedridden));
		inputInICU = ((CheckBox) findViewById(R.id.input_inICU));
		inputIsPregnant = ((CheckBox) findViewById(R.id.input_isPregnant));
		inputHasAcuteRenalFailure = ((CheckBox) findViewById(R.id.input_hasAcuteRenalFailure));
		inputHasEndStageRenalDisease = ((CheckBox) findViewById(R.id.input_hasEndStageRenalDisease));
		inputHasCancer = ((CheckBox) findViewById(R.id.input_hasCancer));
		inputHasAids = ((CheckBox) findViewById(R.id.input_hasAids));
		inputIsParaplegic = ((CheckBox) findViewById(R.id.input_isParaplegic));
		inputIsQuadriplegic = ((CheckBox) findViewById(R.id.input_isQuadriplegic));

		// Input Dosing Regimen
		inputDose = (SeekBar) findViewById(R.id.input_dose_seek);
		inputDoseValue = (TextView) findViewById(R.id.input_dose_value);
		inputDoseInterval = (SeekBar) findViewById(R.id.input_dose_interval_seek);
		inputDoseIntervalValue = (TextView) findViewById(R.id.input_dose_interval_value);

		inputs = new View[] { inputAge, inputSex, inputHt, inputWt, inputSCr,
				inputIsBedridden, inputInICU, inputIsPregnant,
				inputHasAcuteRenalFailure, inputHasEndStageRenalDisease,
				inputHasCancer, inputHasAids, inputIsParaplegic,
				inputIsQuadriplegic, inputDose, inputDoseInterval };

		// Output
		outputHtInches = ((CalculatorItemTextView) findViewById(R.id.output_ht_inches));
		outputKel = ((CalculatorItemTextView) findViewById(R.id.output_kel));
		outputHalflife = ((CalculatorItemTextView) findViewById(R.id.output_halflife));
		outputCrCl = ((CalculatorItemTextView) findViewById(R.id.output_CrCl));
		outputVdNormal = ((CalculatorItemTextView) findViewById(R.id.output_Vd_normal));
		outputVdHypoalbumenic = ((CalculatorItemTextView) findViewById(R.id.output_Vd_hypoalbumenic));
		outputCminNormal = ((CalculatorItemTextView) findViewById(R.id.output_Cmin_normal));
		outputCminHypoalbumenic = ((CalculatorItemTextView) findViewById(R.id.output_Cmin_hypoalbumenic));
		outputCmaxNormal = ((CalculatorItemTextView) findViewById(R.id.output_Cmax_normal));
		outputCmaxHypoalbumenic = ((CalculatorItemTextView) findViewById(R.id.output_Cmax_hypoalbumenic));

		// Add Listeners
		// Patient Details
		inputAge.setOnCalculatorItemChangeListener(this);
		inputSex.setOnCalculatorItemChangeListener(this);
		inputHt.setOnCalculatorItemChangeListener(this);
		inputWt.setOnCalculatorItemChangeListener(this);
		inputSCr.setOnCalculatorItemChangeListener(this);

		inputIsBedridden.setOnClickListener(this);
		inputInICU.setOnClickListener(this);
		inputIsPregnant.setOnClickListener(this);
		inputHasAcuteRenalFailure.setOnClickListener(this);
		inputHasEndStageRenalDisease.setOnClickListener(this);
		inputHasCancer.setOnClickListener(this);
		inputHasAids.setOnClickListener(this);
		inputIsParaplegic.setOnClickListener(this);
		inputIsQuadriplegic.setOnClickListener(this);

		// Dosing Regimen
		inputDose.setOnSeekBarChangeListener(this);
		inputDoseInterval.setOnSeekBarChangeListener(this);

		// Init Views
		inputHt.setUnitOptions(LengthUnit.values());
		inputSex.setOptions(Sex.values());
		initSeekBar(inputDose, drug.getValidDoses());
		initSeekBar(inputDoseInterval, drug.getValidDosingIntervals());

		updateOutputViews();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(KEY_PATIENT, patient);
		outState.putParcelable(KEY_DOSING_REGIMIN, regimen);

		super.onSaveInstanceState(outState);
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
	public void onCalculatorItemChange(CalculatorItem v) {
		updateModel((View) v);
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

	private void updateModel() {
		for (int i = 0, count = inputs.length; i < count; ++i) {
			updateModel(inputs[i]);
		}
	}

	private void updateModel(View v) {
		switch (v.getId()) {
		case R.id.input_age:
			patient.setAge((int) inputAge.getValue());
			break;
		case R.id.input_sex:
			Sex sex = (Sex) inputSex.getSelectedItem();
			if (sex == Sex.MALE) {
				inputIsPregnant.setChecked(false);
				inputIsPregnant.setEnabled(false);
			} else {
				inputIsPregnant.setEnabled(true);
			}
			patient.setSex(sex);
			break;
		case R.id.input_wt:
			patient.setActualBodyWeight(inputWt.getValue());
			break;
		case R.id.input_ht:
			setPatientHeight();
			break;
		case R.id.input_SCr:
			patient.setSCr(inputSCr.getValue());
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
		outputHtInches.setValue(patient.getHeight_in());
		outputKel.setValue("%.3f", calculator.getKElimination());
		outputHalflife.setValue(calculator.getHalflife());
		outputCrCl.setValue(patient.getCrCl());

		float vd = calculator.getNormalVd();
		outputVdNormal.setValue(vd);
		outputCminNormal.setValue(((float) calculator.getCmin(vd)));
		outputCmaxNormal.setValue(((float) calculator.getCmax(vd)));

		if (patient.inHypoAlbumenicState()) {
			vd = calculator.getHypoAlbumenicVd();
			outputVdHypoalbumenic.setValue(vd);
			outputCminHypoalbumenic.setValue(((float) calculator.getCmin(vd)));
			outputCmaxHypoalbumenic.setValue(((float) calculator.getCmax(vd)));
			findViewById(R.id.output_hypoalbumenic).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.output_hypoalbumenic).setVisibility(View.GONE);
		}
	}

	private void setPatientHeight() {
		float ht_val = inputHt.getValue();
		LengthUnit units = (LengthUnit) inputHt.getSelectedUnit();
		switch (units) {
		case CENTIMETER:
			patient.setHeight_cm(ht_val);
			break;
		case INCH:
			patient.setHeight_in(ht_val);
			break;
		}
	}

	private void clearInputs() {
		for (int i = 0, count = inputs.length; i < count; ++i) {
			View view = inputs[i];
			if (view instanceof CalculatorItem) {
				((CalculatorItem) view).clearValue();
			} else if (view instanceof EditText) {
				((EditText) view).getText().clear();
			} else if (view instanceof CheckBox) {
				((CheckBox) view).setChecked(false);
			} else if (view instanceof SeekBar) {
				setSeekBarDefault((SeekBar) view);
			}
		}
		updateModel();
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
			clearInputs();
			((ScrollView) findViewById(R.id.ScrollView))
					.fullScroll(ScrollView.FOCUS_UP);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
