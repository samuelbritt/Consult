package com.pointandframe.consult;

public class Vancomycin implements Drug {

		private static float NORMAL_VD = 0.75f;
		private static float OBESE_VD = 0.56f;
		private static float HYPOALBUMENIC_VD = 0.80f;
		private static float K_ELIMANATION_SLOPE = 0.00083f;
		private static float K_ELIMANATION_INTERCEPT = 0.0044f;
		
	public Vancomycin() {
	}
	
	@Override
	public float getKElimination(Patient patient) {
		return patient.getCrCl() * K_ELIMANATION_SLOPE + K_ELIMANATION_INTERCEPT;
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

}
