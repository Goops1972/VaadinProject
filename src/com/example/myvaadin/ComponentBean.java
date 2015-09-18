package com.example.myvaadin;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;

public class ComponentBean {
	TextField textfield = new TextField();
	CheckBox checkbox = new CheckBox();
	boolean value;
	String ruleNo;
	String RuleGroup;
	String ver;
	String cq;


	public ComponentBean(String ruleNo, String RuleGroup, String ver, String CQ, boolean value){
		
		this.ruleNo = ruleNo;
		this.RuleGroup = RuleGroup;
		this.ver = ver;
		this.value =value;
		
	}
	
	
	public String getRuleNo() {
		return ruleNo;
	}


	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}


	public String getRuleGroup() {
		return RuleGroup;
	}


	public void setRuleGroup(String ruleGroup) {
		RuleGroup = ruleGroup;
	}


	public String getVer() {
		return ver;
	}


	public void setVer(String ver) {
		this.ver = ver;
	}


	public String getCq() {
		return cq;
	}


	public void setCq(String cq) {
		this.cq = cq;
	}


	public CheckBox getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(CheckBox checkbox) {
		this.checkbox = checkbox;
	}

	public TextField getTextfield() {
		return textfield;
	}

	public void setTextfield(TextField textfield) {
		this.textfield = textfield;
	}

}
