package com.example.pojo;

import java.sql.Date;

public class RuleGroupStatus {

	String bundleName;
	String cqName;
	String ruleGroup;
	String version;
	java.util.Date targetRuleCreateDt;
	java.util.Date targetRuleTestDt;
	java.util.Date targetRuleReviewDt;
	java.util.Date targetDate;
	
	int targetPercentRuleCreate;
	int targetPercentRuleTest;
	int targetPercentRuleReview;
	
	int completedPercentRuleCreate;
	int completedPercentRuleTest;
	int completedPercentRuleReview;
	
	int expectedPercent;
	int completedPercent;
	
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
	public String getRuleGroup() {
		return ruleGroup;
	}
	public void setRuleGroup(String ruleGroup) {
		this.ruleGroup = ruleGroup;
	}
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public java.util.Date getTargetRuleCreateDt() {
		return targetRuleCreateDt;
	}
	public void setTargetRuleCreateDt(java.util.Date targetRuleCreateDt) {
		this.targetRuleCreateDt = targetRuleCreateDt;
	}
	public java.util.Date getTargetRuleTestDt() {
		return targetRuleTestDt;
	}
	public void setTargetRuleTestDt(java.util.Date targetRuleTestDt) {
		this.targetRuleTestDt = targetRuleTestDt;
	}
	public int getTargetPercentRuleCreate() {
		return targetPercentRuleCreate;
	}
	public void setTargetPercentRuleCreate(int targetPercentRuleCreate) {
		this.targetPercentRuleCreate = targetPercentRuleCreate;
	}
	public int getCompletedPercentRuleCreate() {
		return completedPercentRuleCreate;
	}
	public void setCompletedPercentRuleCreate(int completedPercentRuleCreate) {
		this.completedPercentRuleCreate = completedPercentRuleCreate;
	}
	public int getTargetPercentRuleTest() {
		return targetPercentRuleTest;
	}
	public void setTargetPercentRuleTest(int targetPercentRuleTest) {
		this.targetPercentRuleTest = targetPercentRuleTest;
	}
	public int getCompletedPercentRuleTest() {
		return completedPercentRuleTest;
	}
	public void setCompletedPercentRuleTest(int completedPercentRuleTest) {
		this.completedPercentRuleTest = completedPercentRuleTest;
	}
	public java.util.Date getTargetRuleReviewDt() {
		return targetRuleReviewDt;
	}
	public void setTargetRuleReviewDt(java.util.Date targetRuleReviewDt) {
		this.targetRuleReviewDt = targetRuleReviewDt;
	}
	public int getTargetPercentRuleReview() {
		return targetPercentRuleReview;
	}
	public void setTargetPercentRuleReview(int targetPercentRuleReview) {
		this.targetPercentRuleReview = targetPercentRuleReview;
	}
	public int getCompletedPercentRuleReview() {
		return completedPercentRuleReview;
	}
	public void setCompletedPercentRuleReview(int completedPercentRuleReview) {
		this.completedPercentRuleReview = completedPercentRuleReview;
	}
	public int getExpectedPercent() {
		return expectedPercent;
	}
	public void setExpectedPercent(int expectedPercent) {
		this.expectedPercent = expectedPercent;
	}
	public int getCompletedPercent() {
		return completedPercent;
	}
	public void setCompletedPercent(int completedPercent) {
		this.completedPercent = completedPercent;
	}
	public java.util.Date getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(java.util.Date targetDate) {
		this.targetDate = targetDate;
	}
	
	
	
	
	
	
}
