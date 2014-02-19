package com.pointandframe.consult;

import junit.framework.TestCase;

public class PKCalculatorTest extends TestCase {
	private Drug drug;
	private Patient patient1;
	private DosingRegimen regimen1;
	private static double TOL = 0.01;
	
	protected void setUp() throws Exception {
		super.setUp();
		drug=new Vancomycin();
		patient1 = new Patient();
		patient1.setHeight_cm(160);
		patient1.setActualBodyWeight(79);
		patient1.setSCr(.89f);
		patient1.setParaplegic(true);
		
		regimen1 = new DosingRegimen();
		regimen1.setDose_mg(1250);
		regimen1.setDosingInterval_hr(18);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCmin() {
		PKCalculator c = new PKCalculator(patient1, drug, regimen1);
		double cmin = c.getCmin(drug.getNormalVd(patient1));
		assertTrue(Math.abs(cmin - 25.7) < TOL);
	}

	public void testGetCmax() {
		fail("Not yet implemented");
	}

}
