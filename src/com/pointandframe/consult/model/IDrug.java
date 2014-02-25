package com.pointandframe.consult.model;

public interface IDrug {

	public float getKElimination(Patient patient);

	public float getHalfLife(Patient patient);

	public float getNormalVdPerABW(Patient patient);

	public float getHypoAlbumenicVdPerABW(Patient patient);

	public float getNormalVd(Patient patient);

	public float getHypoAlbumenicVd(Patient patient);
	
	public int[] getValidDoses();

	public int[] getValidDosingIntervals();

	public 	float getInfusionTime_hr(float dose_mg);

}