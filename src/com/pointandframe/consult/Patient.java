package com.pointandframe.consult;

public class Patient extends SimpleObservable {
	private static double CM_PER_INCH = 2.54;

	private float height_cm;
	private float actualBodyWeight;
	private int age;
	private boolean isMale;
	private float SCr;
	private boolean isDiabetic;
	private boolean hasAids;
	private boolean inIcu;
	private boolean isPregnant;
	private boolean hasAcuteRenalFailure;
	private boolean hasEndStageRenalDisease;
	private boolean hasCancer;
	private boolean isBedridden;
	private boolean isParaplegic;
	private boolean isQuadriplegic;

	public Patient() {
		height_cm = 0f;
		actualBodyWeight = 0f;
		age = 0;
		isMale = true;
		SCr = 0f;
		isDiabetic = false;
		hasAids = false;
		inIcu = false;
		isPregnant = false;
		hasAcuteRenalFailure = false;
		hasEndStageRenalDisease = false;
		hasCancer = false;
		isBedridden = false;
		isParaplegic = false;
		isQuadriplegic = false;
	}

	public float getHeight_cm() {
		return height_cm;
	}

	public void setHeight_cm(float ht_cm) {
		this.height_cm = ht_cm;
		onUpdate();
	}
	
	public float getHeight_in() {
		return (float) (getHeight_cm() / CM_PER_INCH);
	}

	public void setHeight_in(float ht_in) {
		this.height_cm = (float) (ht_in * CM_PER_INCH);
		onUpdate();
	}
	
	private float getHeight_m() {
		return getHeight_cm() / 100f;
	}


	public float getActualBodyWeight() {
		return actualBodyWeight;
	}

	public void setActualBodyWeight(float actualBodyWeight) {
		this.actualBodyWeight = actualBodyWeight;
		onUpdate();
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
		onUpdate();
	}

	public boolean isMale() {
		return isMale;
	}

	public void setSex(Sex sex) {
		if (sex == Sex.MALE) {
			isMale = true;
		} else {
			isMale = false;
		}
		onUpdate();
	}

	public float getSCr() {
		return SCr;
	}

	public void setSCr(float sCr) {
		SCr = sCr;
		onUpdate();
	}

	public boolean isDiabetic() {
		return isDiabetic;
	}

	public void setDiabetic(boolean isDiabetic) {
		this.isDiabetic = isDiabetic;
		onUpdate();
	}

	public boolean hasAids() {
		return hasAids;
	}

	public void setAids(boolean hasAids) {
		this.hasAids = hasAids;
		onUpdate();
	}

	public boolean inIcu() {
		return inIcu;
	}

	public void setIcu(boolean inIcu) {
		this.inIcu = inIcu;
		onUpdate();
	}

	public boolean isPregnant() {
		return isPregnant;
	}

	public void setPregnant(boolean isPregnant) {
		this.isPregnant = isPregnant;
		onUpdate();
	}

	public boolean hasAcuteRenalFailure() {
		return hasAcuteRenalFailure;
	}

	public void setAcuteRenalFailure(boolean hasAcuteRenalFailure) {
		this.hasAcuteRenalFailure = hasAcuteRenalFailure;
		onUpdate();
	}

	public boolean hasEndStageRenalDisease() {
		return hasEndStageRenalDisease;
	}

	public void setEndStageRenalDisease(boolean hasEndStageRenalDisease) {
		this.hasEndStageRenalDisease = hasEndStageRenalDisease;
		onUpdate();
	}

	public boolean hasCancer() {
		return hasCancer;
	}

	public void setCancer(boolean hasCancer) {
		this.hasCancer = hasCancer;
		onUpdate();
	}

	public boolean isBedridden() {
		return isBedridden;
	}

	public void setBedridden(boolean isBedridden) {
		this.isBedridden = isBedridden;
		onUpdate();
	}

	public boolean isParaplegic() {
		return isParaplegic;
	}

	public void setParaplegic(boolean isParaplegic) {
		this.isParaplegic = isParaplegic;
		onUpdate();
	}

	public boolean isQuadriplegic() {
		return isQuadriplegic;
	}

	public void setQuadriplegic(boolean isQuadriplegic) {
		this.isQuadriplegic = isQuadriplegic;
		onUpdate();
	}

	public boolean inHypoAlbumenicState() {
		return (
			getAge() >= 65
			|| isDiabetic()
			|| hasAids()
			|| inIcu()
			|| isPregnant()
			|| hasAcuteRenalFailure()
			|| hasEndStageRenalDisease()
			|| hasCancer()
			|| isUnderweight()
			|| isBedridden()
			|| isParaplegic()
			|| isQuadriplegic()
		);
	}
	
	private boolean isUnderweight() {
		return getBMI() <= 16;
	}
	
	public boolean isObese() {
		return getActualBodyWeight() >= 1.2 * getIdealBodyWeight();
	}

	private float getBMI() {
		return getActualBodyWeight() / (getHeight_m() * getHeight_m());
	}
	
	public float getIdealBodyWeight() {
		float ibw = 2.3f * Math.max(getHeight_in() - 60, 0);
		if (isMale()) {
			ibw += 50;
		} else {
			ibw += 45.5;
		}
		return ibw;
	}
	
	private float getAdjustedBodyWeight() {
		return 0.4f * (getActualBodyWeight() - getIdealBodyWeight()) + getIdealBodyWeight();
	}

	private float getDosingBodyWeight() {
		float bodyWeight = getIdealBodyWeight();
		if (isObese()) {
			bodyWeight = getAdjustedBodyWeight();
		}
		return bodyWeight;
	}
	
	public float getCrCl() {
		float SCr = getSCrForCrCl();
		float bodyWeight = getDosingBodyWeight();
		float CrCl = (140 - getAge()) * bodyWeight / (72 * SCr);
		if (!isMale()) {
			CrCl *= 0.85;
		}
		return Math.min(CrCl, 120.0f);
	}
	
	private float getSCrForCrCl() {
		float SCr = getSCr();
		if (getAge() >= 65) {
			SCr = 1.0f;
		} else if (isParaplegic() || isQuadriplegic()) {
			SCr = 1.0f;
		} else if (isUnderweight() || isBedridden()) {
			SCr = 0.8f;
		}
		return SCr;
	}
	
	private void onUpdate() {
		notifyObservers();
	}


}