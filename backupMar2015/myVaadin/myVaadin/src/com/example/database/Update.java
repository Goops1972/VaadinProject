package com.example.database;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.example.myvaadin.DateConversion;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

public class Update {

	JdbcTemplate jt = Template.getTemplate();
	Query query = new Query();
	String userName = (String) VaadinSession.getCurrent().getAttribute(
			"userName");

	public void updateDataLoad(String bundle, java.sql.Date dataLoadDt) {

		try {
			
			jt.update("update bundle set scenario_dataLoad_dt ='"+dataLoadDt+"', complete_scenario_dataload = "+100+", status ='Scenario Data Loaded' where b_name ='"+bundle+"'");
			Notification.show("Update Success!!!");
			System.out.println("......update bundle set scenario_dataLoad_dt ='"+dataLoadDt+"', complete_scenario_dataload = "+100+", status ='Scenario Data Loaded' where b_name ='"+bundle+"'");
		} catch (Exception e) {

			e.printStackTrace();
			Notification.show("insert failed!!!", Notification.TYPE_ERROR_MESSAGE);
		}
	}

	public void updateCQ(String bundle, String cq, String role, String user, DateField updateDate, String hours) {
		Date inputDt = updateDate.getValue();
		final java.sql.Date updateDt = DateConversion.getSqlDate(inputDt.getTime());

		String updateField = null;
		String userField = null;
		String completionDetailsField = null;
		String bundleDetailsFieldExp = null;
		String completionField = null;
		if(role.equalsIgnoreCase("Design Updater")){
			updateField = "designUp_endDt";
			userField = "designUpdater";
			completionDetailsField = "compDesignUpDate";
			bundleDetailsFieldExp ="designUpPer";
			
		} else if(role.equalsIgnoreCase("Design Reviewer")) {
			updateField = "designRvw_endDt";
			userField = "designReviewer";
			completionDetailsField = "compDesignReview";
			bundleDetailsFieldExp ="designReviewPer";
		}else if(role.equalsIgnoreCase("DataLoad Scenario")) {
			updateField = "dataLoadEnd_dt";
			userField="DataLoader";
			completionDetailsField = "compDataLoad";
			bundleDetailsFieldExp ="scenario_dataLoad_percent";
		}else if(role.equalsIgnoreCase("Scenario Tester")) {
			updateField = "scenario_endDt";
			userField="scenarioTester";
			completionDetailsField ="compScenario";
			bundleDetailsFieldExp ="scenarioTestPer";
		}else if(role.equalsIgnoreCase("Over All Reviewer")){
			updateField = "oa_rvw_endDt";
			userField = "overAllReviewer";
			completionDetailsField ="compOverAllReview";
			bundleDetailsFieldExp ="overAllReviewPer";
			//testRvw_enddt
		}else if(role.equalsIgnoreCase("Test Reviewer")){
			updateField = "testRvw_enddt";
			userField = "testReviewer";
			completionDetailsField ="compTestReview";
			bundleDetailsFieldExp ="testReviewPer";
		
		}else {
			
			Notification.show("Didnt find approritate field ", Notification.TYPE_ERROR_MESSAGE);
		}

		try {
			System.out.println("....in Update.UpdateCQ (update cq set "+updateField+"='"+updateDt+"',"+ userField+"='"+user+"', hours ="+ Integer.parseInt(hours)
					+", "+completionDetailsField+"= 100, "+ "stage ='"+role+"' where bundleName ='"+bundle+"' and cqname ='"+cq+"'");
			
			jt.update("update cq set "+updateField+"='"+updateDt+"',"+ userField+"='"+user+"', hours ="+ Integer.parseInt(hours)
					+", "+completionDetailsField+"= 100, "+ "stage ='"+role+"' where bundleName ='"+bundle+"' and cqname ='"+cq+"'");
			
			Notification.show("Update Success!!!");
			System.out.println(userField +" : "+user+"  "+updateField+"  : "+updateDt);

			jt.update("update user_status_table set status ='Close', end_dt ='"+updateDt+"' where bundle ='"+bundle+"' and cq='"+cq+"' and role ='"+userField+"' and status ='Open' and user ='"+user+"'");
			
			recalculateCQCompletion(bundle, cq);
			
		} catch (Exception e) {

			e.printStackTrace();
			Notification.show("insert failed!!!", Notification.TYPE_ERROR_MESSAGE);
		}
		
		
	}

	public void recalculateCQCompletion(String bundle, String cq) {

		SqlRowSet cqcompletionstatus = jt.queryForRowSet("select compDesignUpdate, compDesignReview, "
				+ "compDataLoad, comprulepercent, compScenario, compTestReview, compOverAllReview from cq where bundleName ='"
				+ bundle+"' and cqname ='"+cq+"'");
		
		int percentComplete = 0;
		
		while(cqcompletionstatus.next()){
			int designUp = cqcompletionstatus.getInt(1);
			int designReview = cqcompletionstatus.getInt(2);
			int dataload = cqcompletionstatus.getInt(3);
			int ruleCompletion = cqcompletionstatus.getInt(4);
			int scenario = cqcompletionstatus.getInt(5);
			int testreview = cqcompletionstatus.getInt(6);
			int overAll = cqcompletionstatus.getInt(7);
			
			int averageCompletion = designUp+designReview+dataload+ruleCompletion+scenario+testreview+overAll;
			percentComplete = averageCompletion/7;
		
		}
		
		jt.update("update cq set totalcomplete ="+percentComplete+" where bundleName ='"+bundle+"' and cqname ='"+cq+"'");
		System.out.println("..update cq set totalcomplete ="+percentComplete+" where bundleName ='"+bundle+"' and cqname ='"+cq+"'");
		
		jt.update("update bundle set complete_cq_percent ="+percentComplete);
		System.out.println("........update bundle set complete_cq_percent ="+percentComplete);
		
		updateBundleCompletionStatus(bundle);
	}

	public void updateBundleCompletionStatus(String bundle){
	
		
		SqlRowSet bundleCompletion = jt.queryForRowSet("select complete_cq_percent, complete_regression_percent from bundle where b_name ='"+bundle+"'");
		System.out.println(".......from updateBundle Completion ===== select complete_cq_percent, complete_regression_percent from bundle where b_name ='"+bundle+"'");
		
		int n=0;
		int sum =0;
		while(bundleCompletion.next()){
			int cqPercent = bundleCompletion.getInt(1);
			int regPercent = bundleCompletion.getInt(2);
			
			sum = cqPercent+regPercent;
			n++;
		}
		
		try {
			jt.update("update bundle set total_bundleCompletion ="+sum/2+" where b_name='"+bundle+"'");
			
			System.out.println(" .......update bundle set total_bundleCompletion ="+sum/2);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	public void signupCQ(String bname, String cqname, String role, String userName, java.sql.Date signUpdt) {
	
		String updateField = null;
		String userField = null;
		if(role.equalsIgnoreCase("Design Updater")){
			updateField = "designUp_stDt";
			userField = "designUpdater";
		} else if(role.equalsIgnoreCase("Design Reviewer")) {
			updateField = "designRvw_stDt";
			userField = "designReviewer";
		}else if(role.equalsIgnoreCase("Scenario Tester")) {
			updateField = "scenario_stDt";
			userField="scenarioTester";
		}else if(role.equalsIgnoreCase("Over All Reviewer")){
			updateField = "oa_rvw_stdt";
			userField = "overAllReviewer";
		}else if(role.equalsIgnoreCase("Test Reviewer")){
			updateField = "testRvw_stdt";
			userField = "testReviewer";
		} else {
		
		Notification.show("Didnt find approritate field ", Notification.TYPE_ERROR_MESSAGE);
	}

		try {
			jt.update("update cq set "+updateField+"='"+signUpdt+"',"+ userField+"='"+userName+"', stage = '"+role+"' where bundleName ='"+bname+"' and cqname ='"+cqname+"'");
System.out.println("........update cq set "+updateField+"='"+signUpdt+"',"+ userField+"='"+userName+"', stage = '"+role+"' where bundleName ='"+bname+"' and cqname ='"+cqname+"'");	

			jt.update("update statusdata set cqstatus ='"+role+"' where bname ='"+bname+"' and cqname ='"+cqname+"'");
System.out.println("updated status data  cqstatus "+role);			
			jt.execute("update bundle_details set status ='"+role+"', st_date= '"+signUpdt +"' where b_Name ='"+bname+"'");
System.out.println("updated bundle_details  >status = "+role );

jt.execute("insert into user_status_table (role, task, user, status, st_dt, bundle, cq) values ('"+userField+"', '"+cqname+" from  "+bname+"', '"
+userName+"', 'Open', '"+signUpdt+"', '"+bname+"', '"+cqname+"')");
			Notification.show("Update Success!!!");
		} catch (Exception e) {

			e.printStackTrace();
			Notification.show("insert failed!!!", Notification.TYPE_ERROR_MESSAGE);
		}

	}

	public void signupBundle(String bname, String role, String userName, java.sql.Date signUpdt) {

		try {
			jt.execute("update bundle set role ='"+role+"', bundle_lead ='"+userName+"' where b_name ='"+bname+"'");
			
			System.out.println(bname+" "+role+" "+userName+" "+signUpdt+"..........");
			Notification.show("insert successfull-Bundle");
			
			jt.execute("update bundle_details set bundle_lead ='"+userName+"', st_date= '"+signUpdt +"' where b_Name ='"+bname+"'");
			System.out.println("insert successfull-Bundle_details.....");
			
			jt.execute("update statusdata set bstatus ='"+role+"', currentstatus ='"+role+"' where bname ='"+bname+"'");
			System.out.println("insert statusdata success........");
			
			jt.execute("insert into user_status_table (task, user, status, st_dt, bundle) values ('Bundle Lead for : "+bname+"', '"+userName+"', 'Open', '"+signUpdt+"', '"+bname+"')");
//			System.out.println(" from Update SignupBundle -insert into user_status_table (task, user, status, st_dt) values ('Bundle Lead for : "+bname+"', ','"+userName+"', 'Open', '"+signUpdt+"')");
		} catch (Exception e) {
			Notification.show("insert failed", Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void updateRuleGroup(String bundle, String cq, String role, String user, DateField updateDate, String hour, 
			int noOfRules, int rulesCreateCompletionPercent,  int rulesTestCompletionPercent, String ruleName) {

		Date inputDt = updateDate.getValue();
		final java.sql.Date updateDt = DateConversion.getSqlDate(inputDt.getTime());

		String updateField = null;
		String userField = null;
		String completePercent = null;
		String completeRuleStatus =null;
		int completionValue =0;
		int ruleGroupCompletion =0;
		
		if(role.equalsIgnoreCase("Rule Creator")) {
			updateField = "rule_creator_endDt";
			userField = "rule_creator";
			completePercent  = "rule_creator_complete";
			completeRuleStatus = "compRuleCreate";
			ruleGroupCompletion = 25;
			completionValue = rulesCreateCompletionPercent/noOfRules;
		}else if(role.equalsIgnoreCase("Rule Tester")) {
			updateField = "rule_tester_endDt";
			userField="rule_tester";
			completePercent  = "rule_test_complete";
			completeRuleStatus = "compRuleTest";
			ruleGroupCompletion = 75;
			completionValue = rulesTestCompletionPercent/noOfRules;
		} else if(role.equalsIgnoreCase("Rule Reviewer")){
			updateField = "rule_review_end_Dt";
			userField="rule_reviewer";
			completePercent  = "rule_review_complete";
			completeRuleStatus = "compRuleReview";
			ruleGroupCompletion = 100;
		}
		
		try {
			
			jt.update("update ruleGroup set "+updateField+"='"+updateDt+"',"+ userField+"='"+user+"', actualHours ="+ Integer.parseInt(hour)
					+", stage ='"+role+"', "+"completion_percent = "+ruleGroupCompletion+", "
					+completePercent+" = 100 where bundleName ='"+bundle+"' and cqname ='"+cq+"' and ruleName ='"+ruleName+"'");
		
			System.out.println("inserted as :............  "+userField+"  "+bundle+"  "+cq);
		
			jt.update("update user_status_table set status ='Close', end_dt ='"+updateDt+"' where bundle ='"+bundle+"' and cq='"+cq+"' and rule='"+ruleName+"' and role ='"+role+"' and status ='Open' and user ='"+user+"'");
		System.out.println("....xxxxxxx..........update user_status_table set status ='Close', end_dt ='"+updateDt+"' where bundle ='"+bundle+"' and cq='"+cq+"' and rule='"+ruleName+"' and role ='"+role+"' and status ='Open' and user ='"+user+"'");
			Notification.show("update Suceess!!!!");

		} catch (Exception e) {

			e.printStackTrace();
			Notification.show("insert failed!!! - updateRuleGroup", Notification.TYPE_ERROR_MESSAGE);
		}

		updateRuleCompletionPercentage(bundle, cq);
		updateCQtotalCompletion(bundle, cq);

		
	}

	public void updateRuleCompletionPercentage(String bundle, String cq) {
		
		int n =0;
		int sum =0;
		
		try {
			
			SqlRowSet ruleCompletion  = jt.queryForRowSet("select completion_percent from rulegroup");
			
			while(ruleCompletion.next()){
				int ruleComplete = ruleCompletion.getInt(1);
				sum = sum+ruleComplete;
				n++;
			}
			
			System.out.println("number of rules : "+n);
			System.out.println("sum : "+sum);
			System.out.println("average rule completion : "+sum/n);
			
			jt.update("update cq set comprulepercent = "+sum/n+" where bundleName ='"+bundle+"' and cqname ='"+cq+"'");
			
			System.out.println("updated in cq rulecomplete Percent : update cq set comprulepercent = "+sum/n+" where bundleName ='"+bundle+"', and cqname ='"+cq+"'");
		} catch (Exception e) {

			e.printStackTrace();
		}
			
	
	}

	//call when any updates happen rule, cq,  
	public void updateCQtotalCompletion(String bundle, String cq) {

		int rulecompletepercent = query.getRuleCompletionPercent(bundle, cq);
		System.out.println(".............update cq set comprulepercent ="+rulecompletepercent);
		jt.update("update cq set comprulepercent ="+rulecompletepercent);
		
		
		
		//get all cq field completion and average
		int cqcompletePercent = query.getCqCompletePercent(bundle, cq);
		
		jt.update("update cq set TotalComplete = "+cqcompletePercent+" where bundleName = '"+bundle+"' and cqname ='"+cq+"'");
		
		//copy to bundle and bundle_details completion
		jt.update("update bundle set complete_cq_percent = "+cqcompletePercent+" where b_name = '"+bundle+"'");
		
//		jt.update("update bundle_details set percent_complete = "+cqcompletPercent+" where b_name = '"+bundle+"'");
		updateBundleCompletion(bundle);
	}

	//call when any updates happen rule, cq,  
	public void updateBundleCompletion(String bundle) {
		
		int totalBundleCompletion = query.getTotalBundleCompletion(bundle);
		jt.update("update bundle set total_bundleCompletion = "+totalBundleCompletion+" where b_name = '"+bundle+"'");
		jt.update("update bundle_details set percent_complete = "+totalBundleCompletion+" where b_name = '"+bundle+"'");
	}

	public void signupRuleGroup(String bname, String cname, String role, String userName, java.sql.Date signUpdt, String newRuleGroup, TextField ver) {
		
		String updateField = null;
		String userField = null;
		if(role.equalsIgnoreCase("Rule Creator")) {
			updateField = "rule_creator_stDt";
			userField = "rule_creator";
		}else if(role.equalsIgnoreCase("Rule Tester")) {
			updateField = "rule_tester_stDt";
			userField="rule_tester";
		} else {
		
		Notification.show("Didnt find approritate field ", Notification.TYPE_ERROR_MESSAGE);
	}

		try {
			
			if(!ver.getValue().toString().isEmpty()){
				jt.execute("insert into ruleGroup(ruleName,ver,bundleName,cqname, rule_creator, rule_creator_stDt, stage) values ('"+newRuleGroup+"',"+"'"+ver.getValue().toString()+"','"
						+bname+"','"+cname+"', '"+userName+"', '"+signUpdt+"','Created')");
				
				System.out.println("newly created rule success........!!!!!!");
			} 
			jt.execute("update ruleGroup set "+updateField+"='"+signUpdt+"',"+ userField+"='"+userName+"', stage ='"+role+"', ruleName ='"+newRuleGroup
					+"' where bundleName ='"+bname+"' and cqname ='"+cname+"'");
			
			System.out.println("update ruleGroup set "+updateField+"='"+signUpdt+"',"+ userField+"='"+userName+"', stage ='"+role+"' where bundleName ='"+bname+"' and cqname ='"+cname+"'...........");
			Notification.show("Signup Success!!!  RuleGroup");

			jt.execute("update statusdata set ruleStatus ='"+role+"', currentStatus ='"+role+"' where bname ='"+bname+"' and cqname='"+cname+"'");
			System.out.println("update success in statusdata table...");
			
			jt.execute("insert into user_status_table (role, task, user, status, st_dt, bundle, cq, rule) values ('"+role+"', '"+newRuleGroup+" for "+cname+" from "+bname+"', '"
			+userName+"', 'Open', '"+signUpdt+"', '"+bname+"', '"+cname+"', '"+newRuleGroup+"')");
		} catch (Exception e) {

			e.printStackTrace();
			Notification.show("Signup failed!!! RuleGroup", Notification.TYPE_ERROR_MESSAGE);
		}

	}

	public void updateDeploymentDate(String bundle, java.sql.Date deployDt) {

	try {
			
			jt.update("update bundle set deployDt ='"+deployDt+"', status ='Deployed' where b_name ='"+bundle+"'");
			Notification.show("Update Success!!!");
			
			jt.update("update user_status_table set status ='Close', end_dt ='"+deployDt+"' where bundle ='"+bundle+"'");
			System.out.println("Update Success - user_status_table...");
			
			jt.update(("update bundle_details set Deployment='Deployed' where b_name='"+bundle+"'"));
			System.out.println("Update Success - BundleDetails_table...");
			
		} catch (Exception e) {

			e.printStackTrace();
			Notification.show("Update failed!!!", Notification.TYPE_ERROR_MESSAGE);
		}
	
	
	}

	public void updateBundleStatus(String bname, String signedRole, String userName) {

		System.out.println("inside updatebundle status.......role : "+signedRole);
		String status = StatusCheck.getStatus(signedRole);
		
		try {
			
			jt.update("update statusdata set currentStatus ='"+status+"', bstatus ='"+status+"' where bname='"+bname+"'");
			
			System.out.println("inside updatebundle status......after update....xxx.");
			Notification.show("update Success!!!!");
			System.out.println("update statusdata set currentStatus ='"+status+"' where bname='"+bname+"'");
		} catch (Exception e) {

			e.printStackTrace();
			Notification.show("update status failed!!!", Notification.TYPE_ERROR_MESSAGE);
		}
		
	}

	public void updateRuleStatus(String bundle, String cqName, String ruleName, String version) {

		try {
			
			jt.update("update statusdata set rulename ='"+ruleName+"', ver ='"+version+"' where bname = '"+bundle+"' and cqname ='"+cqName+"'");
			Notification.show("update in StatusTable success!!!!");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void cleanAll() {
		jt.execute("delete from bundle_details");
		jt.execute("delete from bundle");
		jt.execute("delete from cq");
		jt.execute("delete from bundle");
		jt.execute("delete from statusdata");
		jt.execute("delete from rulegroup");
		
		Notification.show("cleaned all database!!!!");
		
		try {
		
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
	}


	public void updateRegressionData(String regType, String bundleselected,
			java.sql.Date myDate, String hours, String userName) {
	
		try {
			
			jt.update("update regression set reg_end_dt ='"+myDate+"', hours_taken = "+Integer.parseInt(hours)+", COMPL_PERCENT =100 where b_name = '"+bundleselected+"' and regression_type = '"+regType+"'");
			Notification.show("update success in Regression Type");

			
			updateBundleCompletionStatus(bundleselected);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		try {
	
			jt.update("update user_status_table set status ='Close', end_dt ='"+myDate+"' where user ='"+userName+"' and status='Open' and regress_type ='"+regType+"' and bundle ='"+bundleselected+"' and user='"+userName+"'");
			System.out.println("----------update user_status_table set status ='Close', end_dt ='"+myDate+"' where user ='"+userName+"' and status='Open' and regress_type ='"+regType+"' and bundle ='"+bundleselected+"' and user='"+userName+"'");
	
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		updateRegressionCompletion(bundleselected);
			}

	public void updateRegressionCompletion(String bundleName){
		//updating total completion to bundle
				int n =0;
				int sum =0;
				try {
//					SqlRowSet regressCompletion  = jt.queryForRowSet("select compl_percent from regression where b_name ='"+bundleName+"'" );
					SqlRowSet regressCompletion  = jt.queryForRowSet("select compl_percent from regression" );
					while(regressCompletion.next()){
						
						int regressCompl = regressCompletion.getInt(1);
						sum = sum+regressCompl;
						n++;
						System.out.println("reg : "+ regressCompl);
						
					}
					System.out.println("average percent Regression = : "+sum/n);
			
					//updating regression in Bundle table		
					try {
						Notification.show("ready to update  in bundle table");
						jt.update("update bundle set complete_regression_percent = "+sum/n+" where b_name = '"+bundleName+"'");
						System.out.println("Updated in bundle : update bundle set complete_regression_percent = "+sum/n+" where b_name = '"+bundleName+"'");
					} catch (Exception e) {

					}
				} catch (Exception e) {

					e.printStackTrace();
				}
	
	}
	public void signupRegressionTest(String bundleselected, String regressionType, java.sql.Date myDate, String userName) {
		Notification.show("im inside update");

		try {

			jt.update("update regression set reg_st_dt ='"+myDate+"' where b_name = '"+bundleselected+"' and regression_type = '"+regressionType+"'");
			System.out.println("update regression set reg_st_dt ='"+myDate+"' where b_name = '"+bundleselected+"' and regression_type = '"+regressionType+"'");
			Notification.show("update Success!!!");
			
			jt.execute("insert into user_status_table (role, task, user, status, st_dt, bundle, regress_type) values ('Regression Tester', '"+regressionType+" for "+bundleselected+"', '"+userName
					+"', 'Open', '"+myDate+"', '"+bundleselected+"', '"+regressionType+"')");
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void updateRuleReview(String bundleName, String cqName, String userName, String hours, Date upDateDt, int noOfRules) {

		try {
			int hour = Integer.parseInt(hours);
			Date sqlDt = DateConversion.getSqlDate(upDateDt.getTime());
			jt.update("update ruleGroup set ruleReviewHours="+hour/noOfRules+", rule_review_enddt ='"+sqlDt+"', rule_reviewer='"+userName+"', rule_review_complete =100"
					+ " where bundleName='"+bundleName+"' and cqname='"+cqName+"'");
			
			System.out.println("......update ruleGroup set ruleReviewHours="+hour/noOfRules+", rule_review_enddt ='"+sqlDt+"', rule_reviewer='"+userName+"', rule_review_complete =100"
					+ " where bundleName='"+bundleName+"' and cqname='"+cqName+"'");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void updateRuleReviewIncident(String incidentNo, java.sql.Date mySql,
			String bundleNumber, String cqNumber, String ruleGroupName, String ver) {

		try {
		
			jt.update("update ruleGroup set incidentNo ='"+incidentNo+"', incidentOpen_Dt = '"+mySql+"', incident = 'Open'"
					+ "where bundleName='"+bundleNumber+"' and cqname='"+cqNumber+"' and ruleName='"+ruleGroupName+"' and ver ="+ver);
			
			System.out.println("xxxxxxxxxxxxx  update ruleGroup set incidentNo ='"+incidentNo+"', incidentOpen_Dt = '"+mySql+"', incident = 'Open'"
					+ "where bundleName='"+bundleNumber+"' and cqname='"+cqNumber+"' and ruleName='"+ruleGroupName+"' and ver ="+Integer.parseInt(ver));
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		System.out.println("..........xxxx update ruleGroup set incidentNo ='"+incidentNo+"', incidentOpen_Dt = '"+mySql+"', incident = 'Open'"
				+ "where bundleName='"+bundleNumber+"' and cqname='"+cqNumber+"' and ruleName='"+ruleGroupName+"'");
	}

	public void updateRuleReviewIncidentClose(String bundleNumber, String cqNumber, String ruleNumber, String version, Date updateDt, TextField hours) {

		try {
			java.sql.Date myDate = DateConversion.getSqlDate(updateDt.getTime());
			int hour = Integer.parseInt(hours.getValue());
			System.out.println(".....cccc......update ruleGroup set incident='Close', rule_review_enddt='"+myDate+"', ruleReviewHours="+hour+", rule_reviewer='"+userName+"'"
					+ " where bundleName='"+bundleNumber+"' and cqname='"+cqNumber+"' and ruleName='"+ruleNumber+"' and ver="+Integer.parseInt(version));
			
			jt.update("update ruleGroup set incident='Close', rule_review_complete=100, rule_review_enddt='"+myDate+"', ruleReviewHours="+hour+", rule_reviewer='"+userName+"'"
					+ " where bundleName='"+bundleNumber+"' and cqname='"+cqNumber+"' and ruleName='"+ruleNumber+"' and ver="+Integer.parseInt(version));
			System.out.println("....cc. update Success...");
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void updateRuleGroupWithException(String bundleNumber,
			String cqNumber, String ruleGroupName, int version, TextField hoursWorked, DateField incidentDt, int noOfRules, String updater) {

		java.sql.Date myDate = DateConversion.getSqlDate(incidentDt.getValue().getTime());
		int hours = Integer.parseInt(hoursWorked.getValue().toString());

		try {
			jt.update("update ruleGroup set rule_review_endDt='"+myDate+"', ruleReviewHours ="+hours/noOfRules+", rule_reviewer='"+updater+"' where bundleName='"+bundleNumber+"' and "
					+ " cqname='"+cqNumber+"' and ruleName='"+ruleGroupName+"' and ver="+version);
			
			System.out.println("............update ruleGroup set rule_review_endDt='"+myDate+"', ruleReviewHours ="+hours/noOfRules+" rule_reviewer='"+updater+"' where bundleName='"+bundleNumber+"' and "
					+ " cqname='"+cqNumber+"' and ruleName='"+ruleGroupName+"' and ver="+version);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void updateRuleReviewSignUp(String bundle, String role, String user, DateField signUpDt) {
		
		try {

			Date date = signUpDt.getValue();
			java.sql.Date myDate = DateConversion.getSqlDate(date.getTime());
			
			jt.update("update ruleGroup set rule_Reviewer ='"+user+"', rule_Review_stDt ='"+myDate+"' where bundleName='"+bundle+"'");
			
			Notification.show("Signup Update Success!!!!");
			
			System.out.println(".......signup Rulereview Signup....");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void updateBundleTargetDt(DateField designUpdate,
			DateField designReview, DateField ruleCreate,
			DateField ruleTestdate, DateField ruleReviewDate,
			DateField scenarioData, DateField scenarioTestDate,
			DateField testReviewDate, DateField overallReviewDate,
			DateField regressionDate, TextField bundleName, TextField bundleLead) {

		java.sql.Date designupDt = DateConversion.getSqlDate(designUpdate.getValue().getTime());
		java.sql.Date designReviewDt = DateConversion.getSqlDate(designReview.getValue().getTime());
		java.sql.Date ruleCreateDt = DateConversion.getSqlDate(ruleCreate.getValue().getTime());
		java.sql.Date ruleTestDt = DateConversion.getSqlDate(ruleTestdate.getValue().getTime()); 
		java.sql.Date ruleReviewDt = DateConversion.getSqlDate(ruleReviewDate.getValue().getTime());
		java.sql.Date scenarioDataDt = DateConversion.getSqlDate(scenarioData.getValue().getTime()); 
		java.sql.Date scenarioTestDt = DateConversion.getSqlDate(scenarioTestDate.getValue().getTime());
		java.sql.Date testReviewDt = DateConversion.getSqlDate(testReviewDate.getValue().getTime()); 
		java.sql.Date overallReviewDt = DateConversion.getSqlDate(overallReviewDate.getValue().getTime());
		java.sql.Date regressionDt = DateConversion.getSqlDate(regressionDate.getValue().getTime());

		try {
			
			//TODO: Activate during deployment
//			jt.update("update bundle_Details set designuptarget='"+designupDt+"', designreviewTarget='"+designReviewDt+"', rulecreatetarget='"+ruleCreateDt+"', ruletesttarget='"+ruleTestDt+"',"
//					+ "ruleReviewTarget='"+ruleReviewDt+"', scenarioDataTargetDt='"+scenarioDataDt+"', scenarioTestTarget='"+scenarioTestDt+"', testReviewtarget='"+testReviewDt+"',"
//							+ "OverAllReviewtarget='"+overallReviewDt+"', RegressionTarget='"+regressionDt+"' where b_name='"+bundleName.getValue()+"' and bundle_lead='"+bundleLead.getValue()+"'");

			jt.update("update bundle_Details set designuptarget='"+designupDt+"', designreviewTarget='"+designReviewDt+"', rulecreatetarget='"+ruleCreateDt+"', ruletesttarget='"+ruleTestDt+"',"
					+ "ruleReviewTarget='"+ruleReviewDt+"', scenarioDataTargetDt='"+scenarioDataDt+"', scenarioTestTarget='"+scenarioTestDt+"', testReviewtarget='"+testReviewDt+"',"
							+ "OverAllReviewtarget='"+overallReviewDt+"', RegressionTarget='"+regressionDt+"' where b_name='"+bundleName.getValue()+"'");
//			
			System.out.println("...............update bundle_Details set designuptarget='"+designupDt+"', designreviewTarget='"+designReviewDt+"', rulecreatetarget='"+ruleCreateDt+"', ruletesttarget='"+ruleTestDt+"',"
					+ "ruleReviewTarget='"+ruleReviewDt+"', scenarioDataTargetDt='"+scenarioDataDt+"', scenarioTestTarget='"+scenarioTestDt+"', testReviewtarget='"+testReviewDt+"',"
					+ "OverAllReviewtarget='"+overallReviewDt+"', RegressionTarget='"+regressionDt+"' where b_name='"+bundleName.getValue());
			
			Notification.show("Update Success!!!!!      "+designupDt);		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	public void updateBundlePercent(TextField designUpdatePer,
			TextField designReviewPer, TextField ruleCreatePer,
			TextField ruleTestPer, TextField ruleReviewPer,
			TextField scenarioDataPer, TextField scenarioTestPer,
			TextField testReviewPer, TextField overAllReviewPer,
			TextField regressionPer, TextField bundleName, TextField bundleLead) {

		
		jt.update("update bundle_details set designupper ="+designUpdatePer.getValue()+", designreviewper="+designReviewPer.getValue()+", rulecreatePer="+ruleCreatePer.getValue()+","
				+ "ruleTestPer="+ruleTestPer.getValue()+", ruleReviewPer="+ruleReviewPer.getValue()+", scenario_dataLoad_percent="+scenarioDataPer.getValue()+", scenarioTestPer="+scenarioTestPer.getValue()+","
						+ "testReviewPer="+testReviewPer.getValue()+", overAllReviewPer="+overAllReviewPer.getValue()+", regressionPer="+regressionPer.getValue()+" where b_name='"+bundleName.getValue()+"' and bundle_lead='"+bundleLead.getValue()+"'");

		System.out.println("........update bundle_details set designupper ="+designReviewPer.getValue()+", designreviewper="+designReviewPer.getValue()+", rulecreatePer="+ruleCreatePer.getValue()+","
				+ "ruleTestPer="+ruleTestPer.getValue()+", ruleReviewPer="+ruleReviewPer.getValue()+", scenario_dataLoad_percent="+scenarioDataPer.getValue()+", scenarioTestPer="+scenarioTestPer.getValue()+","
						+ "testReviewPer="+testReviewPer.getValue()+", overAllReviewPer="+overAllReviewPer.getValue()+", regressionPer="+regressionPer.getValue()+" where b_name='"+bundleName.getValue()+"' and bundle_lead='"+bundleLead.getValue()+"'");

		Notification.show("Update Success!!!!");
	}

	
}
