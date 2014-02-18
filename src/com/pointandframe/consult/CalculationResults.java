package com.pointandframe.consult;

public class CalculationResults {
	private float height_in;
	private float halfLife;
	
	float vdNormal;
	double CminNormal;
	double CmaxNormal;
	
	float vdHypoAlbumentic;
	double CminHypoAlbumentic;
	double CmaxHypoAlbumentic;
	
	public CalculationResults() {
		;
	}

	public float getHeight_in() {
		return height_in;
	}

	public void setHeight_in(float height_in) {
		this.height_in = height_in;
	}

	public float getHalfLife() {
		return halfLife;
	}

	public void setHalfLife(float halfLife) {
		this.halfLife = halfLife;
	}

	public float getVdNormal() {
		return vdNormal;
	}

	public void setVdNormal(float vdNormal) {
		this.vdNormal = vdNormal;
	}

	public double getCminNormal() {
		return CminNormal;
	}

	public void setCminNormal(double cminNormal) {
		CminNormal = cminNormal;
	}

	public double getCmaxNormal() {
		return CmaxNormal;
	}

	public void setCmaxNormal(double cmaxNormal) {
		CmaxNormal = cmaxNormal;
	}

	public float getVdHypoAlbumentic() {
		return vdHypoAlbumentic;
	}

	public void setVdHypoAlbumentic(float vdHypoAlbumentic) {
		this.vdHypoAlbumentic = vdHypoAlbumentic;
	}

	public double getCminHypoAlbumentic() {
		return CminHypoAlbumentic;
	}

	public void setCminHypoAlbumentic(double cminHypoAlbumentic) {
		CminHypoAlbumentic = cminHypoAlbumentic;
	}

	public double getCmaxHypoAlbumentic() {
		return CmaxHypoAlbumentic;
	}

	public void setCmaxHypoAlbumentic(double cmaxHypoAlbumentic) {
		CmaxHypoAlbumentic = cmaxHypoAlbumentic;
	}


}
