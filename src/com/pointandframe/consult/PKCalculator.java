package com.pointandframe.consult;


public class PKCalculator {

	private DosingRegimen dosingRegimen;
	private Patient patient;
	private Drug drug;
	
	public PKCalculator(Patient patient, Drug drug, DosingRegimen regimen) {
		this.drug = drug;
		this.patient = patient;
		this.dosingRegimen = regimen;
	}

	public DosingRegimen getDosingRegimin() {
		return dosingRegimen;
	}

	public void setDosingRegimin(DosingRegimen dosingRegimin) {
		this.dosingRegimen = dosingRegimin;
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
		float t_inf = drug.getInfusionTime_hr(dosingRegimen.getDose_mg());
		float tau = dosingRegimen.getDosingInterval_hr();
		float k_el = drug.getKElimination(patient);

		double Cmax = getCmax(volumeOfDistribution);
		return Cmax * Math.exp(- k_el * (tau - t_inf));
	}
	
	public double getCmax(float volumeOfDistribution) {
		float t_inf = drug.getInfusionTime_hr(dosingRegimen.getDose_mg());
		float tau = dosingRegimen.getDosingInterval_hr();
		float dose_mg = dosingRegimen.getDose_mg();
		float k_el = drug.getKElimination(patient);
		float abw = patient.getActualBodyWeight();
		float Vd = volumeOfDistribution;

		double num = (1 - Math.exp(-k_el * t_inf)) * dose_mg;
		double denom = (1 - Math.exp(-k_el * tau)) * t_inf * k_el * Vd * abw;
		return num / denom; 
	}
}
