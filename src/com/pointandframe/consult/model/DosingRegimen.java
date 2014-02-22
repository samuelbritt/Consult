package com.pointandframe.consult.model;

import com.pointandframe.consult.util.IObservable;
import com.pointandframe.consult.util.IObserver;
import com.pointandframe.consult.util.SimpleObservable;

public class DosingRegimen implements IObservable {
	private float dose_mg;
	private float dosingInterval_hr;
	private IObservable oberservable;

	public DosingRegimen() {
		oberservable = new SimpleObservable();
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

	@Override
	public void registerObserver(IObserver o) {
		oberservable.registerObserver(o);
	}

	@Override
	public void removeObserver(IObserver o) {
		oberservable.removeObserver(o);
	}

	@Override
	public void notifyObservers() {
		oberservable.notifyObservers();
	}
}