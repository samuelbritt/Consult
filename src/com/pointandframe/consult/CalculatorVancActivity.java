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
import com.pointandframe.consult.views.CalculatorItem;
import com.pointandframe.consult.views.CalculatorItemEditText;
import com.pointandframe.consult.views.CalculatorItemEditTextUnitSpinner;
import com.pointandframe.consult.views.CalculatorItemOutput;
import com.pointandframe.consult.views.CalculatorItemSpinner;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class CalculatorVancActivity extends Activity implements IObserver,
		OnSeekBarChangeListener, OnClickListener,
		CalculatorItem.OnCalculatorItemChangeListener {

	// Model
	private Patient patient;
	private DosingRegimen regimen;
	private IDrug drug;
	private PKCalculator calculator;

	// Patient Detail Input
	private CalculatorItemEditText inputAge;
	private CalculatorItemSpinner inputSex;
	private CalculatorItemEditTextUnitSpinner inputHt;
	private CalculatorItemEditText inputWt;
	private CalculatorItemEditText inputSCr;

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

	// Dosing Regimen Input
	private SeekBar inputDose;
	private SeekBar inputDoseInterval;
	private TextView inputDoseValue;
	private TextView inputDoseIntervalValue;
	HashMap<SeekBar, int[]> seekBarProgressArrays;

	// Outputs
	private CalculatorItemOutput outputHtInches;
	private TextView outputKel;
	private TextView outputCrCl;
	private TextView outputHalflife;
	private TextView outputVdNormal;
	private TextView outputVdHypoalbumenic;
	private TextView outputCminNormal;
	private TextView outputCminHypoalbumenic;
	private TextView outputCmaxNormal;
	private TextView outputCmaxHypoalbumenic;

	// Other fields
	private final static String TAG = "CalculatorVancActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator_vanc);

		// Model
		patient = new Patient();
		regimen = new DosingRegimen();
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

		// Input Dosing Regimen
		inputDose = (SeekBar) findViewById(R.id.input_dose_seek);
		inputDoseValue = (TextView) findViewById(R.id.input_dose_value);
		inputDoseInterval = (SeekBar) findViewById(R.id.input_dose_interval_seek);
		inputDoseIntervalValue = (TextView) findViewById(R.id.input_dose_interval_value);

		// Output
		outputHtInches = ((CalculatorItemOutput) findViewById(R.id.output_ht_inches));
		outputKel = ((TextView) findViewById(R.id.output_kel_value));
		outputHalflife = ((TextView) findViewById(R.id.output_halflife_value));
		outputCrCl = ((TextView) findViewById(R.id.output_CrCl_value));
		outputVdNormal = ((TextView) findViewById(R.id.output_Vd_value));
		outputVdHypoalbumenic = ((TextView) findViewById(R.id.output_Vd_hypoalbumenic_value));
		outputCminNormal = ((TextView) findViewById(R.id.output_Cmin_value));
		outputCminHypoalbumenic = ((TextView) findViewById(R.id.output_Cmin_hypoalbumenic_value));
		outputCmaxNormal = ((TextView) findViewById(R.id.output_Cmax_value));
		outputCmaxHypoalbumenic = ((TextView) findViewById(R.id.output_Cmax_hypoalbumenic_value));

		// Add Listeners
		// Patient Details
		inputAge.setOnCalculatorItemChangeListener(this);
		inputSex.setOnCalculatorItemChangeListener(this);
		inputHt.setOnCalculatorItemChangeListener(this);
		inputWt.setOnCalculatorItemChangeListener(this);
		inputSCr.setOnCalculatorItemChangeListener(this);

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

		// Dosing Regimen
		inputDose.setOnSeekBarChangeListener(this);
		inputDoseInterval.setOnSeekBarChangeListener(this);

		// Init Views
		inputHt.setUnitOptions(LengthUnit.values());
		inputSex.setOptions(Sex.values());
		initSeekBar(inputDose, drug.getValidDoses());
		initSeekBar(inputDoseInterval, drug.getValidDosingIntervals());
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

	private void updateModel(View v) {
		switch (v.getId()) {
		case R.id.input_age:
			patient.setAge((int) ((CalculatorItemEditText) v).getValue());
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
			patient.setActualBodyWeight(((CalculatorItemEditText) v).getValue());
			break;
		case R.id.input_ht:
			setPatientHeight();
			break;
		case R.id.input_SCr:
			patient.setSCr(((CalculatorItemEditText) v).getValue());
			break;
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
		outputHtInches.setValue(patient.getHeight_in());
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
