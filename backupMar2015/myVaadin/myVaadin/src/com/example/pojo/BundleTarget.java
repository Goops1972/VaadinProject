package com.example.pojo;

import java.io.Serializable;
import java.sql.Date;

public class BundleTarget implements Serializable{

	private Date targetDt;
	private String stage;
	private int expPercent;
	
	
	
	
	
	public Date getTargetDt() {
		return targetDt;
	}
	public void setTargetDt(Date targetDt) {
		this.targetDt = targetDt;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public int getExpPercent() {
		return expPercent;
	}
	public void setExpPercent(int expPercent) {
		this.expPercent = expPercent;
	}
	
	
	
}
