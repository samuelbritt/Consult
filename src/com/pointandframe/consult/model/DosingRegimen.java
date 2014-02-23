package com.pointandframe.consult.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.pointandframe.consult.util.IObservable;
import com.pointandframe.consult.util.IObserver;
import com.pointandframe.consult.util.SimpleObservable;

public class DosingRegimen implements IObservable, Parcelable {
	private float dose_mg;
	private float dosingInterval_hr;
	private IObservable oberservable;

	private class ParcelWriter {
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeFloat(dose_mg);
			dest.writeFloat(dosingInterval_hr);
		}

		public void readFromParcel(Parcel in) {
			dose_mg = in.readFloat();
			dosingInterval_hr = in.readFloat();
		}
	}

	public static final Parcelable.Creator<DosingRegimen> CREATOR = new Parcelable.Creator<DosingRegimen>() {
		public DosingRegimen createFromParcel(Parcel in) {
			return new DosingRegimen(in);
		}

		public DosingRegimen[] newArray(int size) {
			return new DosingRegimen[size];
		}
	};

	public DosingRegimen() {
		oberservable = new SimpleObservable();
	}

	public DosingRegimen(Parcel in) {
		oberservable = new SimpleObservable();
		(new ParcelWriter()).readFromParcel(in);
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		(new ParcelWriter()).writeToParcel(dest, flags);
	}
}