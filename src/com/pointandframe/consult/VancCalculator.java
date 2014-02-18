package com.pointandframe.consult;

public class VancCalculator {

	private DosingRegimin dosingRegimin = new DosingRegimin();
	private Patient patient;
	private Drug drug;
	
	public VancCalculator() {
		this.drug = new Vancomycin();
	}

	public DosingRegimin getDosingRegimin() {
		return dosingRegimin;
	}

	public void setDosingRegimin(DosingRegimin dosingRegimin) {
		this.dosingRegimin = dosingRegimin;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public float getKElimination() {
		return drug.getKElimination(patient);
	}
	
	public float getHalflife() {
		return drug.getHalfLife(patient);
	}
	
	public float getNormalVd() {
		return drug.getNormalVd(patient);
	}
	
	public float getHypoAlbumenicVd() {
		return drug.getHypoAlbumenicVd(patient);
	}
	
	public double getCmin(float volumeOfDistribution) {
		float t_inf = dosingRegimin.getInfusionTime_hr();
		float tau = dosingRegimin.getDosingInterval_hr();
		float k_el = drug.getKElimination(patient);

		double Cmax = getCmax(volumeOfDistribution);
		return Cmax * Math.exp(- k_el * (tau - t_inf));
	}
	
	public double getCmax(float volumeOfDistribution) {
		float t_inf = dosingRegimin.getInfusionTime_hr();
		float tau = dosingRegimin.getDosingInterval_hr();
		float dose_mg = dosingRegimin.getDose_mg();
		float k_el = drug.getKElimination(patient);
		float abw = patient.getActualBodyWeight();
		float Vd = volumeOfDistribution;

		double num = (1 - Math.exp(-k_el * t_inf)) * dose_mg;
		double denom = (1 - Math.exp(-k_el * tau)) * t_inf * k_el * Vd * abw;
		return num / denom; 
	}
}
