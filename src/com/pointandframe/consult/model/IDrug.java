package com.pointandframe.consult.model;

import android.os.Parcelable;


public interface IDrug {

	public float getKElimination(Patient patient);
	public float getHalfLife(Patient patient);
	public float getNormalVd(Patient patient);
	public float getHypoAlbumenicVd(Patient patient);
	int[] getValidDoses();
	int[] getValidDosingIntervals();
	float getInfusionTime_hr(float dose_mg);
}