package com.pointandframe.consult;

public class DosingRegimen extends SimpleObservable {
	private float dose_mg;
	private float dosingInterval_hr;

	public DosingRegimen() {
	}

	public float getDose_mg() {
		return dose_mg;
	}

	public void setDose_mg(float dose_mg) {
		this.dose_mg = dose_mg;
		onUpdate();
	}

	public float getDosingInterval_hr() {
		return dosingInterval_hr;
	}

	public void setDosingInterval_hr(float dosingInterval_hr) {
		this.dosingInterval_hr = dosingInterval_hr;
		onUpdate();
	}
	
	public void onUpdate() {
		notifyObservers();
	}
}