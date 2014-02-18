package com.pointandframe.consult;

public class DosingRegimin extends SimpleObservable {
	private float dose_mg;
	private float dosingInterval_hr;

	public DosingRegimin() {
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

	public float getInfusionTime_hr() {
		if (dose_mg <= 1000) {
			return 1f;
		} else if (dose_mg <= 1500) {
			return 1.5f;
		} else if (dose_mg <= 2000) {
			return 2f;
		} else {
			return 2.5f;
		}
	}
	
	public void onUpdate() {
		notifyObservers();
	}
}