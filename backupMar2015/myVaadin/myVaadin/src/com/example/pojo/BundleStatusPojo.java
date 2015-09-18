package com.example.pojo;

import java.sql.Date;

public class BundleStatusPojo {

	private String bundleName;
	private String currentStage;
	private String expectedStage;
	private Date targetDt;
	private int targetPercent;
	private int currentPercent;

	private String cqName;
	private int cqComplete;
	private int regressionComplete;
	private int totalComplete;
	
	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
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

	public int getCurrentPrecent() {
		return currentPercent;
	}

	public void setCurrentPrecent(int currentPrecent) {
		this.currentPercent = currentPrecent;
	}

	public int getCurrentPercent() {
		return currentPercent;
	}

	public void setCurrentPercent(int currentPercent) {
		this.currentPercent = currentPercent;
	}

	public String getCqName() {
		return cqName;
	}

	public void setCqName(String cqName) {
		this.cqName = cqName;
	}

	public int getCqComplete() {
		return cqComplete;
	}

	public void setCqComplete(int cqComplete) {
		this.cqComplete = cqComplete;
	}

	public int getRegressionComplete() {
		return regressionComplete;
	}

	public void setRegressionComplete(int regressionComplete) {
		this.regressionComplete = regressionComplete;
	}

	public int getTotalComplete() {
		return totalComplete;
	}

	public void setTotalComplete(int totalComplete) {
		this.totalComplete = totalComplete;
	}

	
	
}
