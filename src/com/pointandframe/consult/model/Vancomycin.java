package com.pointandframe.consult.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Vancomycin implements IDrug {

	private static float NORMAL_VD = 0.75f;
	private static float OBESE_VD = 0.56f;
	private static float HYPOALBUMENIC_VD = 0.80f;
	private static float K_ELIMINATION_SLOPE = 0.00083f;
	private static float K_ELIMINATION_INTERCEPT = 0.0044f;

	private static int[] validDoses = { 250, 500, 750, 1000, 1250, 1500, 1750,
			2000, 2250, 2500, 2750, 3000 };
	private static int[] validDosingIntervals = { 8, 12, 16, 18, 24, 36, 72, 96 };

	public Vancomycin() {
	}

	@Override
	public float getKElimination(Patient patient) {
		return patient.getCrCl() * K_ELIMINATION_SLOPE
				+ K_ELIMINATION_INTERCEPT;
	}

	@Override
	public float getHalfLife(Patient patient) {
		return 0.69314718056f / getKElimination(patient);
	}

	@Override
	public float getNormalVd(Patient patient) {
		if (patient.getActualBodyWeight() > 1.3 * patient.getIdealBodyWeight()) {
			return OBESE_VD;
		} else {
			return NORMAL_VD;
		}
	}

	@Override
	public float getHypoAlbumenicVd(Patient patient) {
		return HYPOALBUMENIC_VD;
	}

	@Override
	public int[] getValidDoses() {
		return validDoses;
	}

	@Override
	public int[] getValidDosingIntervals() {
		return validDosingIntervals;
	}

	@Override
	public float getInfusionTime_hr(float dose_mg) {
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

}
