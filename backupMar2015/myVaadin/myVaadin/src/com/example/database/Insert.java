package com.example.database;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.example.myvaadin.DateConversion;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class Insert {
	JdbcTemplate jt = Template.getTemplate();

	Update update = new Update();

	public void signupRule(String bname, String cname, String rname,
			String signedRole, String userName, java.sql.Date signUpdt) {

		if(signedRole.equalsIgnoreCase("Rule Creator")){
			java.sql.Date signdt = DateConversion.getSqlDate(signUpdt.getTime());
			signedRole = "Rule Creator";
			try {
				jt.execute("insert into ruleGroup (bundleName, cqname, ruleName, role, user, rule_create_stDt) values ('"
						+ bname+"','"+cname+"','"+rname+"','"+signedRole+"','"+userName+"','"+signUpdt+"')");
					System.out.println(bname+" "+cname+" "+rname+" "+signedRole+" "+userName+" "+signUpdt);
			Notification.show("Insert Successfull");
			System.out.println("signing up Rule........");
			
			} catch (Exception e) {

				Notification.show("insert failed - Rule Creator", Notification.Type.ERROR_MESSAGE);
				e.printStackTrace();
			}
		} else {
			java.sql.Date signdt = DateConversion.getSqlDate(signUpdt.getTime());
			signedRole = "Rule Tester";
	
		try {
			jt.execute("insert into ruleGroup (bundleName, cqname, ruleName, role, user, rule_tester_stDt) values ('"
					+ bname+"','"+cname+"','"+rname+"','"+signedRole+"','"+userName+"','"+signUpdt+"')");
				System.out.println(bname+" "+cname+" "+rname+" "+signedRole+" "+userName+" "+signUpdt);
		Notification.show("Insert Successfull");
			
		} catch (Exception e) {
			Notification.show("insert failed - Tester", Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	}

	public void addNewRule(String bundle, String cqName, String ruleName,
			String ver) {
		try {
			jt.execute("insert into ruleGroup(ruleName,ver,bundleName,cqname, stage, rule_creator, rule_tester, rule_reviewer) values ('"
					+ ruleName+ "',"+ "'"+ ver+ "','"+ bundle+ "','"+ cqName + "', 'Created', '-', '-', '-')");
			Notification.show("insert Successfull");

			update.updateRuleCompletionPercentage(bundle, cqName);
			
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			Notification.show("insert failed", Notification.TYPE_ERROR_MESSAGE);
		}

	}

//	public void updateRuleCompletionPercentage(String bundle, String cqName) {}

			

	public void bundlePercent(String bundleName, String designUpdate,
			String designReview, String ruleCreate, String ruleTest,  String ruleReview, String scenarioData, String scenarioTest,
			String testReviewer, String overAllreview, String regression) {

		try {
			
//			jt.execute("insert into bundle_details (b_name, designUpPer, designReviewPer, ruleCreatePer, ruleTestPer, scenarioTestPer, testReviewPer, overAllReviewPer, regressionPer, "
//					+ "status) values "
//					+ "('"+bundleName+"', '"+ Integer.parseInt(designUpdate)+"', '"+Integer.parseInt(designReview)+"', '"+Integer.parseInt(ruleCreate)+"', '"+
//					Integer.parseInt(ruleTest)+"', '"+Integer.parseInt(scenarioTest)+"', '"+Integer.parseInt(testReviewer)+"', '"+Integer.parseInt(overAllreview)+"', '"+Integer.parseInt(regression)+"', 'Created')");
//			
			jt.execute("update bundle_details set designUpPer ="+Integer.parseInt(designUpdate)
					+ ", designReviewPer ="+Integer.parseInt(designReview)
					+ ", ruleCreatePer="+Integer.parseInt(ruleCreate)
					+ ", ruleTestPer="+Integer.parseInt(ruleTest)
					+ ", ruleReviewPer="+Integer.parseInt(ruleReview)
					+ ", scenario_dataLoad_percent="+Integer.parseInt(scenarioData)
					+ ", scenarioTestPer="+Integer.parseInt(scenarioTest)
					+ ", testReviewPer="+Integer.parseInt(testReviewer)
					+ ", overAllReviewPer="+Integer.parseInt(overAllreview)
					+ ", regressionPer="+Integer.parseInt(regression)
					+ ", status='Created' where b_name='"+bundleName+"'");
			
			Notification.show("inserted to bundle_details Percetage");
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	
	public void addBundle(String bundleName, String bundleDesc, int bundleDays,
			java.sql.Date designReviewDt, java.sql.Date designUpdateDt,
			java.sql.Date overallReviewDt, java.sql.Date ruleCreateDt,
			java.sql.Date ruleReviewDt, java.sql.Date ruleTestDt, 
			Date scenarioDataTargetDt, java.sql.Date scenarioTestDt,
			java.sql.Date testReviewDt, java.sql.Date regressionDt) {


//		public void addBundle(String string, String string2, int bundleDays,
//				java.sql.Date designReviewDt, java.sql.Date designUpdateDt,
//				java.sql.Date overallReviewDt, java.sql.Date ruleCreateDt,
//				java.sql.Date ruleTestDt, java.sql.Date ruleReviewDate,
//				DateField scenarioData, java.sql.Date scenarioTestDt,
//				java.sql.Date testReviewDt, java.sql.Date regressionDt) {
//			
//		}
		try {
			System.out.println("inside insert addBundle method.....");	
			jt.execute("insert into bundle (b_name, bun_desc, bun_period_dys, design_rvw_dt, design_update_dt, overall_rvw_dt, rule_create_dt, RULE_RVW_DT, rule_test_dt, scenario_dataLoad_dt, scenario_test_dt, test_rvw_dt, REGRES_DT, status) values ('"
			+ bundleName+ "','"+ bundleDesc+ "','"+ bundleDays+ "','"+ designReviewDt+ "','"+ designUpdateDt+ "','"+ overallReviewDt+ "','"
			+ ruleCreateDt+ "','"+ ruleReviewDt+ "','"+ ruleTestDt+ "','"+scenarioDataTargetDt+ "','" + scenarioTestDt + "','" + testReviewDt +"','"+ regressionDt+"','Created')");
	System.out.println("insert success in Bundle");
			//inserting into Bundle_details
	
		} catch (Exception e) {
			System.out.println("entry failed into bundle");
			e.printStackTrace();
		}
		
		try {
			jt.execute("insert into bundle_details (b_name, designReviewTarget, DesignUpTarget, OverAllReviewTarget, RuleCreateTarget, RuleTestTarget, RuleReviewTarget, scenarioDataTargetDt, ScenarioTestTarget, TestReviewTarget, RegressionTarget, status, Deployment) values ('"
					+ bundleName+ "','"+ designReviewDt+ "','"+ designUpdateDt+ "','"+ overallReviewDt+ "','"
					+ ruleCreateDt+ "','"+ ruleTestDt+ "','"+ ruleReviewDt+"','"+ scenarioDataTargetDt + "','" + scenarioTestDt + "','" + testReviewDt + "','"+ regressionDt+"','Created', 'Development')");
			System.out.println("insert success in Bundle_Details..........>>>>");

		} catch (Exception e) {
			System.out.println("entry failed into bundle_details");
			e.printStackTrace();
		}
	}

	public void bundleTargetDt(java.util.Date designUpdate, java.util.Date designReview,
			java.util.Date ruleCreate, java.util.Date ruleTest,
			java.util.Date scenarioDt, java.util.Date scenarioTestDt,
			java.util.Date testReviewDt, java.util.Date overAllReviewDt, java.util.Date regressionDt) {

		try {
			
//			jt.execute("update into bundle_details);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

	public void insertRegressionTests(List<String> regressionList, String bundleNameSelected) {

		for (String regressionName : regressionList) {

			try {
				jt.execute("insert into regression (b_name, regression_type) values ('"+bundleNameSelected+"', '"+regressionName+"')");
				Notification.show("insert success in regression table");
				
			} catch (Exception e) {

				e.printStackTrace();
				
			}
		}
		}

	public void insertRegressionTest(String regressionName, String bundleNameSelected) {

		
		
			try {
				jt.execute("insert into regression (b_name, regression_type) values ('"+bundleNameSelected+"', '"+regressionName+"')");
				Notification.show("insert success in regression table");
				
			} catch (Exception e) {

				e.printStackTrace();
				
		}
		}

	public void changeLogTargetDt(TextField bundleName, TextField bundleLead,
			TextArea reason) {

		try {
		java.util.Date todayDate = new java.util.Date();
		Date upDateDt = DateConversion.getSqlDate(todayDate.getTime());
		
			jt.execute("insert into bundleChangeLog (b_name, bundle_lead, reason, upDatedDt) values ('"+bundleName.getValue()+"','"+bundleLead.getValue()+"', '"+reason.getValue()+"', '"+upDateDt+"')");
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}


}
