package com.pointandframe.consult;

public interface Drug {

	public float getKElimination(Patient patient);
	public float getHalfLife(Patient patient);
	public float getNormalVd(Patient patient);
	public float getHypoAlbumenicVd(Patient patient);

}
