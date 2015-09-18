package com.example.pojo;

import java.sql.Date;

public class CQstatusPojo {

	private String bundleName;
	private String cqName;
	private String currentStage;
	private String expectedStage;
	private Date targetDt;
	private int targetPercent;
	private int currentPercent;
	private int cqCompletedPercent;
	
	private java.sql.Date designUptarget, designreviewtarget, rulecreateTarget, ruletesttarget, rulereviewtarget, scenariotesttarget,
	testreviewtarget, overallreviewtarget, regressiontarget;
	
	private int designReviewPer, designUpPer, ruleCreatePer, ruleTestPer, ruleReviewPer, scenarioDataLoad, scenarioTestPer, testReviewPer, overallReviewPer, totalRuleComplete;
	
	public String getBundleName() {
		return bundleName;
	}
	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}
	
	
	public String getCqName() {
		return cqName;
	}
	public void setCqName(String cqName) {
		this.cqName = cqName;
	}
	public String getCurrentStage() {
		return currentStage;
	}
	public void setCurrentStage(String currentStage) {
		this.currentStage = currentStage;
	}
	public String getExpectedStage() {
		return expectedStage;
	}
	public void setExpectedStage(String expectedStage) {
		this.expectedStage = expectedStage;
	}
	public Date getTargetDt() {
		return targetDt;
	}
	public void setTargetDt(Date targetDt) {
		this.targetDt = targetDt;
	}
	public int getTargetPercent() {
		return targetPercent;
	}
	public void setTargetPercent(int targetPercent) {
		this.targetPercent = targetPercent;
	}
	public int getCurrentPercent() {
		return currentPercent;
	}
	public void setCurrentPercent(int currentPercent) {
		this.currentPercent = currentPercent;
	}
	public java.sql.Date getDesignUptarget() {
		return designUptarget;
	}
	public void setDesignUptarget(java.sql.Date designUptarget) {
		this.designUptarget = designUptarget;
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
	public int getTotalRuleComplete() {
		return totalRuleComplete;
	}
	public void setTotalRuleComplete(int totalRuleComplete) {
		this.totalRuleComplete = totalRuleComplete;
	}
	public int getScenarioDataLoad() {
		return scenarioDataLoad;
	}
	public void setScenarioDataLoad(int scenarioDataLoad) {
		this.scenarioDataLoad = scenarioDataLoad;
	}
	public int getCqCompletedPercent() {
		return cqCompletedPercent;
	}
	public void setCqCompletedPercent(int cqCompletedPercent) {
		this.cqCompletedPercent = cqCompletedPercent;
	}

	
	
	
}
