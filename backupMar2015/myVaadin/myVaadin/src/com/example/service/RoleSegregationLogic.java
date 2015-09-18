package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.example.database.Template;
import com.example.pojo.RoleSegregration;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class RoleSegregationLogic {
	JdbcTemplate jt = Template.getTemplate();
//	String userName = (String) VaadinSession.getCurrent().getAttribute("userName");
	
	public RoleSegregration checkConfirmity(String user, String role, String cqname) {

		System.out.println("inside checkconfirmity  role :  "+role);
		
		RoleSegregration ruleSeg = new RoleSegregration();
		
		if(role.equalsIgnoreCase("Design Updater")){
			ruleSeg = getDesignUpdaterEligibility(user, role, cqname, ruleSeg);

		}else if(role.equalsIgnoreCase("Design Reviewer")){
			ruleSeg = getDesignReviewerEligibility(user, role, cqname, ruleSeg);
			
		}else if(role.equalsIgnoreCase("Rule Creator")){
			
			
		}else if(role.equalsIgnoreCase("Rule Tester")){
			
			
		}else if(role.equalsIgnoreCase("Rule Reviewer")){

			ruleSeg = getRuleReviewer(user, role, cqname, ruleSeg);

			
		}else if(role.equalsIgnoreCase("Scenario Tester")){
			ruleSeg = getScenarioTester(user, role, cqname, ruleSeg);
			
		}else if(role.equalsIgnoreCase("Test Reviewer")){

			ruleSeg = getTestReviewer(user, role, cqname, ruleSeg);
			
		}else if(role.equalsIgnoreCase("Over All Reviewer")){
		
			ruleSeg = getOverAllChangeReviewer(user, role, cqname, ruleSeg);
			
		}else if(role.equalsIgnoreCase("Regression Tester")){

		
		}
		
		
		
		return ruleSeg;
	}


	private RoleSegregration getRuleReviewer(String userName, String role, String cqname, RoleSegregration ruleSeg) {

		SqlRowSet signedUpStatus = jt.queryForRowSet("select cqname, ruleCreator, overAllReviewer from cq where cqname ='"+cqname+"'ruleCreator='"+userName+"'"
						+"' or overAllReviewer='"+userName+"' ");
		
		System.out.println(".....xxxxDD....select cqname, ruleCreator, overAllReviewer from cq where cqname ='"+cqname+"'ruleCreator='"+userName+"'"
						+"' or overAllReviewer='"+userName+"' ");
		
		ruleSeg.setEligible(true);
		
		while(signedUpStatus.next()){
			
			ruleSeg.setCqname(signedUpStatus.getString(1));
			ruleSeg.setRuleCreator(signedUpStatus.getString(2));
			ruleSeg.setOverAllChangeReviwer(signedUpStatus.getString(3));
			
			ruleSeg.setEligible(false);
		}

		if(ruleSeg.isEligible()){
			ruleSeg.setEligible(true);	
		}
		return ruleSeg;

	}


	private RoleSegregration getScenarioTester(String userName, String role, String cqname, RoleSegregration ruleSeg) {

		SqlRowSet signedUpStatus = jt.queryForRowSet("select cqname, designUpdater, ruleCreator, ruleTester, scenarioTester, "
				+ "overAllReviewer from cq where cqname ='"+cqname+"' and designUpdater='"+userName+"'  or ruleCreator='"+userName+"'"
						+ "or ruleTester='"+userName+"' or scenarioTester='"+userName+"' or overAllReviewer='"+userName+"' ");
		
		System.out.println(".....xxxx....select cqname, designUpdater, ruleCreator, ruleTester, scenarioTester, "
				+ "overAllReviewer from cq where cqname ='"+cqname+"' and designUpdater='"+userName+"'  or ruleCreator='"+userName+"'"
						+ "or ruleTester='"+userName+"' or scenarioTester='"+userName+"' or overAllReviewer='"+userName+"' ");
		
		ruleSeg.setEligible(true);
		
		while(signedUpStatus.next()){
			
			ruleSeg.setCqname(signedUpStatus.getString(1));
			ruleSeg.setRuleLead(signedUpStatus.getString(2));
			ruleSeg.setRuleCreator(signedUpStatus.getString(3));
			ruleSeg.setRuleTester(signedUpStatus.getString(4));
			ruleSeg.setScenarioTester(signedUpStatus.getString(5));
			ruleSeg.setOverAllChangeReviwer(signedUpStatus.getString(6));
			
			ruleSeg.setEligible(false);
		}

		if(ruleSeg.isEligible()){
			ruleSeg.setEligible(true);	
		}
		return ruleSeg;

	}


	private RoleSegregration getTestReviewer(String userName, String role, String cqname, RoleSegregration ruleSeg) {
		SqlRowSet signedUpStatus = jt.queryForRowSet("select cqname, designUpdater, ruleCreator, ruleTester, scenarioTester, "
				+ "overAllReviewer from cq where cqname ='"+cqname+"' and designUpdater='"+userName+"'  or ruleCreator='"+userName+"'"
						+ "or ruleTester='"+userName+"' or scenarioTester='"+userName+"' or overAllReviewer='"+userName+"' ");
		
		System.out.println(".........select cqname, designUpdater, ruleCreator, ruleTester, scenarioTester, "
				+ "overAllReviewer from cq where cqname ='"+cqname+"' and designUpdater='"+userName+"'  or ruleCreator='"+userName+"'"
						+ "or ruleTester='"+userName+"' or scenarioTester='"+userName+"' or overAllReviewer='"+userName+"' ");
		
		ruleSeg.setEligible(true);
		
		while(signedUpStatus.next()){
			
			ruleSeg.setCqname(signedUpStatus.getString(1));
			ruleSeg.setRuleLead(signedUpStatus.getString(2));
			ruleSeg.setRuleCreator(signedUpStatus.getString(3));
			ruleSeg.setRuleTester(signedUpStatus.getString(4));
			ruleSeg.setScenarioTester(signedUpStatus.getString(5));
			ruleSeg.setOverAllChangeReviwer(signedUpStatus.getString(6));

		}

		if(ruleSeg.isEligible()){
			ruleSeg.setEligible(true);	
		}
		return ruleSeg;

	}


	private RoleSegregration getOverAllChangeReviewer(String userName, String role, String cqname, RoleSegregration ruleSeg) {

		SqlRowSet signedUpStatus = jt.queryForRowSet("select cqname, designUpdater, designReviewer, ruleCreator, ruleTester, ruleReviewer, scenarioTester,"
				+ "testReviewer, overAllReviewer from cq where"
				+ " cqname ='"+cqname+"' and designUpdater='"+userName+"'  or designReviewer ='"+userName+"' or ruleCreator='"+userName+"'"
						+ "or ruleTester='"+userName+"' or ruleReviewer='"+userName+"' or scenarioTester='"+userName+"'"
								+ "or testReviewer='"+userName+"'or overAllReviewer='"+userName+"' ");
		
		System.out.println(".........select cqname, designUpdater, designReviewer, ruleCreator, ruleTester, ruleReviewer, scenarioTester,"
				+ "testReviewer, overAllReviewer from cq where"
				+ " cqname ='"+cqname+"' and designUpdater='"+userName+"'  or designReviewer ='"+userName+"' or ruleCreator='"+userName+"'"
						+ "or ruleTester='"+userName+"' or ruleReviewer='"+userName+"' or scenarioTester='"+userName+"'"
								+ "or testReviewer='"+userName+"'or overAllReviewer='"+userName+"' ");
		
		ruleSeg.setEligible(true);
		
		while(signedUpStatus.next()){
			
			ruleSeg.setCqname(signedUpStatus.getString(1));
			ruleSeg.setRuleLead(signedUpStatus.getString(2));
			ruleSeg.setRuleReviewer(signedUpStatus.getString(3));
			ruleSeg.setRuleCreator(signedUpStatus.getString(4));
			ruleSeg.setRuleTester(signedUpStatus.getString(5));
			ruleSeg.setRuleReviewer(signedUpStatus.getString(6));
			ruleSeg.setScenarioTester(signedUpStatus.getString(7));
			ruleSeg.setTestReviewer(signedUpStatus.getString(8));
			ruleSeg.setOverAllChangeReviwer(signedUpStatus.getString(9));

		}

		if(ruleSeg.isEligible()){
			ruleSeg.setEligible(true);	
		}
		return ruleSeg;
}


	private RoleSegregration getDesignReviewerEligibility(String userName,
			String role, String cqname, RoleSegregration ruleSeg) {


		SqlRowSet signedUpStatus = jt.queryForRowSet("select cqname, designUpdater, overAllReviewer from cq where"
				+ " cqname ='"+cqname+"' and designUpdater='"+userName+"'  or overAllReviewer='"+userName+"' ");
		
		System.out.println("sql query syntax : select cqname, designUpdater, overAllReviewer from cq where"
				+ " cqname ='"+cqname+"' and designUpdater='"+userName+"'  or overAllReviewer='"+userName+"' ");
		
		List<String> numberOfRoles = new ArrayList<String>();
		
		
		ruleSeg.setEligible(true);
		
		while(signedUpStatus.next()){
			ruleSeg.setCqname(signedUpStatus.getString(1));
			ruleSeg.setRuleLead(signedUpStatus.getString(2));
			ruleSeg.setOverAllChangeReviwer(signedUpStatus.getString(3));
			ruleSeg.setEligible(false);
			
		}

		if(ruleSeg.isEligible()){
			ruleSeg.setEligible(true);	
		}
		return ruleSeg;

	}


	private RoleSegregration getDesignUpdaterEligibility(String userName,
			String role, String cqname, RoleSegregration ruleSeg) {

		SqlRowSet signedUpStatus = jt.queryForRowSet("select cqname, designReviewer, scenarioTester, testReviewer, overAllReviewer from cq where"
				+ " cqname ='"+cqname+"' and designReviewer='"+userName+"' or scenarioTester='"+userName+"' or testReviewer='"+userName+"' or overAllReviewer='"+userName+"' ");
		
		System.out.println("inside Check Confirmtiy : select cqname, designReviewer, scenarioTester, testReviewer, overAllReviewer from cq where"
				+ " cqname ='"+cqname+"' and designReviewer='"+userName+"' or scenarioTester='"+userName+"' or testReviewer='"+userName+"' or overAllReviewer='"+userName+"' ");
		
		List<String> numberOfRoles = new ArrayList<String>();
		
		
		ruleSeg.setEligible(true);
		
		while(signedUpStatus.next()){
			ruleSeg.setCqname(signedUpStatus.getString(1));
			ruleSeg.setDesignReviewer(signedUpStatus.getString(2));
			ruleSeg.setScenarioTester(signedUpStatus.getString(3));
			ruleSeg.setTestReviewer(signedUpStatus.getString(4));
			ruleSeg.setOverAllChangeReviwer(signedUpStatus.getString(5));
			ruleSeg.setEligible(false);
			
		}

		if(ruleSeg.isEligible()){
			ruleSeg.setEligible(true);	
		}
		return ruleSeg;

	}


	public Table getTable(String user, String role, RoleSegregration roleSignedup) {
		List<String> numberOfRolesName = new ArrayList<String>();
		System.out.println("inside getTable................................"+roleSignedup.getScenarioTester());
		
		if (role.equalsIgnoreCase("Design Updater")) {
			if(!roleSignedup.getDesignReviewer().equalsIgnoreCase("-") && roleSignedup.getDesignReviewer().equalsIgnoreCase(user)){
				roleSignedup.setSignedUpRole("DesignReviewer");
				numberOfRolesName.add(roleSignedup.getSignedUpRole());
			};
	
				
			if(roleSignedup.getScenarioTester().equalsIgnoreCase(user)){
				roleSignedup.setSignedUpRole("Scenario Tester");
				numberOfRolesName.add(roleSignedup.getSignedUpRole());
			} else {
				numberOfRolesName.add(roleSignedup.getSignedUpRole());
				System.out.println("inside getTable ScenarioTester :  "+roleSignedup.getSignedUpRole());
			}
			
			System.out.println("inside getTable xxxxx after :  "+roleSignedup.getSignedUpRole());
			
			if(roleSignedup.getTestReviewer().equalsIgnoreCase(user)){
				numberOfRolesName.add("Test Reviewer");
				
				System.out.println("inside test reviewere");
			};
			
			if(roleSignedup.getOverAllChangeReviwer().equalsIgnoreCase(user)){
				numberOfRolesName.add("Over All Reviewer");
				System.out.println("inside overall reviewere...");
				
			};

		} else if (role.equalsIgnoreCase("Design Reviewer")) {
			
			if(roleSignedup.getOverAllChangeReviwer().equalsIgnoreCase(user)){
				numberOfRolesName.add("Over All Reviewer");
			} else if(roleSignedup.getRuleLead().equalsIgnoreCase(user)){
				numberOfRolesName.add("Rule Lead");
			}
		} else if(role.equalsIgnoreCase("Rule Creator")){
			roleSignedup.setSignedUpRole("Scenario Tester");
			numberOfRolesName.add(roleSignedup.getSignedUpRole());

			
		} else if(role.equalsIgnoreCase("Rule Tester")){
			System.out.println("inside ruleSegrationLogic RuleTester");
		}else if(role.equalsIgnoreCase("Rule Reviewer")){
			
		}else if(role.equalsIgnoreCase("Scenario Tester")){
			
		}else if(role.equalsIgnoreCase("Test Reviewer")){
			
		}else if(role.equalsIgnoreCase("Over All Tester")){
			
		}else if(role.equalsIgnoreCase("Regression Tester")){
			
		}
		
		
		
	
		
				
//		}
		
		VerticalLayout vl = new VerticalLayout();
		vl.setSpacing(true);
		
		Label status = new Label("You can not sign up as ' "+role+" ', because ");
		vl.addComponent(status);
		
		Table table = new Table();
		table.setHeight("200px");
		Button okButton = new Button("OK");
		table.addContainerProperty("Role Signed up", String.class, null);
		for (int i = 0; i < numberOfRolesName.size(); i++) {
			table.addItem(new Object[]{numberOfRolesName.get(i)}, i);
//			roleName.setValue(roleName.getValue());
			System.out.println(numberOfRolesName.get(i)+"  >>>>>>>>>>>");
			vl.addComponent(table);
			vl.addComponent(okButton);
			
		}
	return table;
	}


//	public RoleSegregration checkConfirmity(String userName, String role) {
//
//		RoleSegregration ruleSeg = new RoleSegregration();
//		
//		SqlRowSet signedUpStatus = jt.queryForRowSet("select cqname, ruleCreator, overAllReviewer from cq where"
//				+ " cqname ='"+cqname+"' and designReviewer='"+userName+"' or scenarioTester='"+userName+"' or testReviewer='"+userName+"' or overAllReviewer='"+userName+"' ");
//		
//		System.out.println("inside Check Confirmtiy : select cqname, designReviewer, scenarioTester, testReviewer, overAllReviewer from cq where"
//				+ " cqname ='"+cqname+"' and designReviewer='"+userName+"' or scenarioTester='"+userName+"' or testReviewer='"+userName+"' or overAllReviewer='"+userName+"' ");
//		
//		List<String> numberOfRoles = new ArrayList<String>();
//		
//		
//		ruleSeg.setEligible(true);
//		
//		while(signedUpStatus.next()){
//			ruleSeg.setCqname(signedUpStatus.getString(1));
//			ruleSeg.setDesignReviewer(signedUpStatus.getString(2));
//			ruleSeg.setScenarioTester(signedUpStatus.getString(3));
//			ruleSeg.setTestReviewer(signedUpStatus.getString(4));
//			ruleSeg.setOverAllChangeReviwer(signedUpStatus.getString(5));
//			ruleSeg.setEligible(false);
//			
//		}
//
//		if(ruleSeg.isEligible()){
//			ruleSeg.setEligible(true);	
//		}
//		return ruleSeg;
//
//	}


	public RoleSegregration checkConfirmityRuleReview(String userName, String role, String bundleSelected) {

		RoleSegregration ruleSeg = new RoleSegregration();
		
		SqlRowSet signedUpStatus = jt.queryForRowSet("select cqname, ruleCreator, overAllReviewer from cq where"
				+ " bundleName ='"+bundleSelected+"' and ruleCreator='"+userName+"' or overAllReviewer='"+userName+"' ");
		
		System.out.println("inside Check Confirmtiy : select cqname, ruleCreator, overAllReviewer from cq where"
				+ " bundleName ='"+bundleSelected+"' and ruleCreator='"+userName+"' or overAllReviewer='"+userName+"' ");
		
		List<String> numberOfRoles = new ArrayList<String>();
		
		ruleSeg.setEligible(true);
		
		while(signedUpStatus.next()){
			ruleSeg.setCqname(signedUpStatus.getString(1));
			ruleSeg.setRuleCreator(signedUpStatus.getString(2));
			ruleSeg.setOverAllChangeReviwer(signedUpStatus.getString(3));
			ruleSeg.setEligible(false);
		}

		if(ruleSeg.isEligible()){
			ruleSeg.setEligible(true);	
		}
		return ruleSeg;
	
	}


	public RoleSegregration checkConfirmityRuleReview(String userName, String role, String bundle, String cqName) {

		RoleSegregration ruleSeg = new RoleSegregration();
		
		SqlRowSet signedUpStatus = jt.queryForRowSet("select testReviewer, overAllReviewer from cq where"
				+ " bundleName ='"+bundle+"' and testReviewer='"+userName+"' or overAllReviewer='"+userName+"' ");
		
		System.out.println("inside Check Confirmtiy : select testReviewer, overAllReviewer from cq where"
				+ " bundleName ='"+bundle+"' and testReviewer='"+userName+"' or overAllReviewer='"+userName+"' ");
		
		List<String> numberOfRoles = new ArrayList<String>();
		
		ruleSeg.setEligible(true);
		
		while(signedUpStatus.next()){
			ruleSeg.setTestReviewer(signedUpStatus.getString(1));
			ruleSeg.setOverAllChangeReviwer(signedUpStatus.getString(2));
			ruleSeg.setEligible(false);
		}

		if(ruleSeg.isEligible()){
			ruleSeg.setEligible(true);	
		}
		return ruleSeg;

	}

}
