package com.example.myvaadin;

import java.io.Serializable;
import java.util.Date;

public class BundlePojo implements Serializable{

	private String bname;
	private String bdesc;
	private String bundleLead;

	private java.sql.Date designUptarget, designreviewtarget, rulecreateTarget, ruletesttarget, rulereviewtarget, scenarioDataTarget, scenariotesttarget,
	testreviewtarget, overallreviewtarget, regressiontarget;
	
	private int designReviewPer, designUpPer, ruleCreatePer, ruleTestPer, ruleReviewPer, scenarioDataPer, scenarioTestPer, testReviewPer, overallReviewPer, regressionPer;
	
	public BundlePojo(){
		
	}

	public BundlePojo(String bname2, String bdesc2) {
		this.bname = bname2;
		this.bdesc = bdesc2;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	
	public String getBundleLead() {
		return bundleLead;
	}

	public void setBundleLead(String bundleLead) {
		this.bundleLead = bundleLead;
	}

	public String getBdesc() {
		return bdesc;
	}

	public void setBdesc(String bdesc) {
		this.bdesc = bdesc;
	}

	public java.sql.Date getDesignUptarget() {
		return designUptarget;
	}

	public java.sql.Date getDesignreviewtarget() {
		return designreviewtarget;
	}

	public void setDesignreviewtarget(java.sql.Date designreviewtarget) {
		this.designreviewtarget = designreviewtarget;
	}

	public java.sql.Date getRulecreateTarget() {
		return rulecreateTarget;
	}

	public void setRulecreateTarget(java.sql.Date rulecreateTarget) {
		this.rulecreateTarget = rulecreateTarget;
	}

	public java.sql.Date getRuletesttarget() {
		return ruletesttarget;
	}

	public void setRuletesttarget(java.sql.Date ruletesttarget) {
		this.ruletesttarget = ruletesttarget;
	}

	public java.sql.Date getRulereviewtarget() {
		return rulereviewtarget;
	}

	public void setRulereviewtarget(java.sql.Date rulereviewtarget) {
		this.rulereviewtarget = rulereviewtarget;
	}

	public java.sql.Date getScenariotesttarget() {
		return scenariotesttarget;
	}

	public void setScenariotesttarget(java.sql.Date scenariotesttarget) {
		this.scenariotesttarget = scenariotesttarget;
	}

	public java.sql.Date getTestreviewtarget() {
		return testreviewtarget;
	}

	public void setTestreviewtarget(java.sql.Date testreviewtarget) {
		this.testreviewtarget = testreviewtarget;
	}

	public java.sql.Date getOverallreviewtarget() {
		return overallreviewtarget;
	}

	public void setOverallreviewtarget(java.sql.Date overallreviewtarget) {
		this.overallreviewtarget = overallreviewtarget;
	}

	public java.sql.Date getRegressiontarget() {
		return regressiontarget;
	}

	public void setRegressiontarget(java.sql.Date regressiontarget) {
		this.regressiontarget = regressiontarget;
	}

	public void setDesignUptarget(java.sql.Date designUptarget) {
		this.designUptarget = designUptarget;
	}

	public int getDesignReviewPer() {
		return designReviewPer;
	}

	public void setDesignReviewPer(int designReviewPer) {
		this.designReviewPer = designReviewPer;
	}

	public int getDesignUpPer() {
		return designUpPer;
	}

	public void setDesignUpPer(int designUpPer) {
		this.designUpPer = designUpPer;
	}

	public int getRuleCreatePer() {
		return ruleCreatePer;
	}

	public void setRuleCreatePer(int ruleCreatePer) {
		this.ruleCreatePer = ruleCreatePer;
	}

	public int getRuleTestPer() {
		return ruleTestPer;
	}

	public void setRuleTestPer(int ruleTestPer) {
		this.ruleTestPer = ruleTestPer;
	}

	public int getRuleReviewPer() {
		return ruleReviewPer;
	}

	public void setRuleReviewPer(int ruleReviewPer) {
		this.ruleReviewPer = ruleReviewPer;
	}

	public int getScenarioTestPer() {
		return scenarioTestPer;
	}

	public void setScenarioTestPer(int scenarioTestPer) {
		this.scenarioTestPer = scenarioTestPer;
	}

	public int getTestReviewPer() {
		return testReviewPer;
	}

	public void setTestReviewPer(int testReviewPer) {
		this.testReviewPer = testReviewPer;
	}

	public int getOverallReviewPer() {
		return overallReviewPer;
	}

	public void setOverallReviewPer(int overallReviewPer) {
		this.overallReviewPer = overallReviewPer;
	}

	public java.sql.Date getScenarioDataTarget() {
		return scenarioDataTarget;
	}

	public void setScenarioDataTarget(java.sql.Date scenarioDataTarget) {
		this.scenarioDataTarget = scenarioDataTarget;
	}

	public int getScenarioDataPer() {
		return scenarioDataPer;
	}

	public void setScenarioDataPer(int scenarioDataPer) {
		this.scenarioDataPer = scenarioDataPer;
	}

	public int getRegressionPer() {
		return regressionPer;
	}

	public void setRegressionPer(int regressionPer) {
		this.regressionPer = regressionPer;
	}


	
}
