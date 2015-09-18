package com.example.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.example.myvaadin.BundleForm;
import com.example.myvaadin.BundlePojo;
import com.example.myvaadin.DateConversion;
import com.example.myvaadin.RegressionForm;
import com.example.myvaadin.RulePojo;
import com.example.pojo.BundleStatusPojo;
import com.example.pojo.CQstatusPojo;
import com.example.pojo.RegressionPojo;
import com.example.pojo.RoleSegregration;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;

public class Query implements Serializable{

	JdbcTemplate jt = Template.getTemplate();

	RulePojo ruleBean = new RulePojo();
	BundleStatusPojo bundleStatus = new BundleStatusPojo();
	public List<String> getBundles() {
		
		List bundleList = new ArrayList<>();
		SqlRowSet bundleNameList = jt.queryForRowSet("select * from bundle");
		
		while(bundleNameList.next()){
			String bundle = bundleNameList.getString(1).toString();
			bundleList.add(bundle);
		}
		return bundleList;
	}

	public List<String> getCQlist(String bundle) {

		List cqList = new ArrayList<>();
		
		SqlRowSet cqNameList = jt.queryForRowSet("select cqname from cq where bundleName ='"+bundle+"'");
		
		while(cqNameList.next()){
			String cqName = cqNameList.getString(1).toString();
			
			System.out.println("...."+cqName);
			cqList.add(cqName);
		}
		
		return cqList;
	}


	public List<RulePojo> getRulelist(String selectedbundle, String selectedcq) {

		List<RulePojo> ruleList = new ArrayList<RulePojo>();
		SqlRowSet ruleNameList = jt.queryForRowSet("select ruleName, ver, rule_creator_complete, rule_test_complete from ruleGroup where bundleName ='"+selectedbundle+"' and cqname ='"
				+selectedcq+ "'");
		System.out.println("select ruleName, ver, rule_creator_complete, rule_test_complete from ruleGroup where bundleName ='"+selectedbundle+"' and cqname ='"
				+selectedcq+ "'");
		
		while(ruleNameList.next()){

			String ruleName = ruleNameList.getString(1);
			int ver = ruleNameList.getInt(2);
			int createComplete = ruleNameList.getInt(3);
			int testComplete = ruleNameList.getInt(4);
			
			RulePojo rule = new RulePojo();
			rule.setRuleGroupName(ruleName);
			rule.setVersion(ver);
			rule.setRuleCreatePercent(createComplete);
			rule.setRuleTestPercent(testComplete);
			
			ruleList.add(rule);
		}
		
		return ruleList;
	}

	public List<String> getRuleOnly(String selectedbundle, String selectedcq) {

		List ruleList = new ArrayList<>();
		SqlRowSet ruleNameList = jt.queryForRowSet("select ruleName, ver from ruleGroup where bundleName ='"+selectedbundle+"' and cqname ='"
				+selectedcq+ "'");
		while(ruleNameList.next()){

			String ruleName = ruleNameList.getString(1);
			
			ruleList.add(ruleName);
		}
		
		return ruleList;
	}

	public boolean getBundlesStatus(String eventvalue) {

		
		SqlRowSet requestBundle = jt.queryForRowSet("select b_name from bundle where b_name ='"+eventvalue+"'");
		while(requestBundle.next()){
			
			String bundleName = requestBundle.getString(1);
			System.out.println("returned bundle Name: xxxxxxxxxx"+bundleName);
			return true;
		}
		return false;
	
	}

	public SqlRowSet getTargetDate(String bundleName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getBundlesStatusList(String eventvalue) {

		
		List<String> bundleStatus = new ArrayList<String>();
		
		try {
//			SqlRowSet requestBundle = jt.queryForRowSet("select cqname, cqstatus from statusData where bname ='"+eventvalue+"'");
			SqlRowSet requestBundle = jt.queryForRowSet("select cqname, cqstatus from statusData where bname ='"+eventvalue+"'");
			while(requestBundle.next()){
				
				String bundleName = requestBundle.getString(1);
				System.out.println("returned bundle Name: xxxxxxxxxx .."+bundleName);
				
				bundleStatus.add(bundleName);
			}	
		} catch (Exception e) {
			Notification.show("failed fetching data Bundle");// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return bundleStatus;
	}

	public boolean getCQstatus(String eventvalue) {

		try {
//			SqlRowSet requestCQ = jt.queryForRowSet("select ruleName, currentStatus, cqstatus from statusData where cqname ='"+eventvalue+"'");
			SqlRowSet requestCQ = jt.queryForRowSet("select cqname from rulegroup where cqname ='"+eventvalue+"'");
			while(requestCQ.next()){
				
			return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean getRuleStatus(String eventvalue) {

		try {
			SqlRowSet requestRule = jt.queryForRowSet("select ruleName from statusData where ruleName ='"+eventvalue+"'");
			while(requestRule.next()){
				return true;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	public int getCqCompletePercent(String bundle){
		int percentComplete =0;

			SqlRowSet cqcompletionstatus = jt.queryForRowSet("select cqname, compDesignUpdate, compDesignReview, "
					+ "compDataLoad, compRuleReview, compScenario, compTestReview, compOverAllReview from cq where bundleName ='"
					+ bundle+"'");
			
//			SqlRowSet cqcompletionstatus = jt.queryForRowSet("select compDesignUpdate, compDesignReview, "
//					+ "compDataLoad, comprulepercent, compRuleReview, compScenario, compTestReview, compOverAllReview from cq where bundleName ='"
//					+ bundle+"'");
//			
			while(cqcompletionstatus.next()){
				System.out.println("inside cqcompletion status .... query class getCqCompletePercent ");
				String cqName = cqcompletionstatus.getString(1);
				int designUp = cqcompletionstatus.getInt(2);
				int designReview = cqcompletionstatus.getInt(3);
				int dataload = cqcompletionstatus.getInt(4);
				
//				int ruleCompletion = cqcompletionstatus.getInt(4);
				RulePojo ruleComplete = getRuleCompleteStatus(bundle, cqName);
//				int ruleCompletion = getRuleCompleteStatus(cqName, bundle);
				int ruleReview = cqcompletionstatus.getInt(5);
				int scenario = cqcompletionstatus.getInt(6);
				int testreview = cqcompletionstatus.getInt(7);
				int overAll = cqcompletionstatus.getInt(8);
				
System.out.println("    "+designUp+"    "+designReview+"    "+dataload+"    "+ruleComplete.getRuleCompletePercent()+"    "+scenario+testreview+"    "+overAll);



				int averageCompletion = designUp+designReview+dataload+ruleComplete.getRuleCompletePercent()+scenario+testreview+overAll;
System.out.println("averageCompletion    >>>>>>>>>>>>>>>>>>   "+averageCompletion);

				percentComplete = averageCompletion/8;
System.out.println("percentcomplet........................"+percentComplete);			
			}

		return percentComplete;
	}
	
	public int getCqCompletePercent(String bundle, String cq){
		int percentComplete =0;
		
		try {
			
			SqlRowSet cqcompletionstatus = jt.queryForRowSet("select compDesignUpdate, compDesignReview, "
					+ "compDataLoad, comprulepercent, compScenario, compTestReview, compOverAllReview from cq where bundleName ='"
					+ bundle+"'");
			
			while(cqcompletionstatus.next()){
				System.out.println("inside cqcompletion status .... query class getCqCompletePercent ");
				int designUp = cqcompletionstatus.getInt(1);
				int designReview = cqcompletionstatus.getInt(2);
				int dataload = cqcompletionstatus.getInt(3);
				int ruleCompletion = cqcompletionstatus.getInt(4);
				int scenario = cqcompletionstatus.getInt(5);
				int testreview = cqcompletionstatus.getInt(6);
				int overAll = cqcompletionstatus.getInt(7);

				int averageCompletion = designUp+designReview+dataload+ruleCompletion+scenario+testreview+overAll;
				System.out.println("averageCompletion  "+averageCompletion);
				percentComplete = averageCompletion/7;
				
			}
			
			System.out.println("..........total cq complete .... :  "+percentComplete);
			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return percentComplete;
	}

	public int getTotalBundleCompletion(String bundle) {

		int bundleCompletion =0;
		
		SqlRowSet bundleData = jt.queryForRowSet("select complete_cq_percent, complete_regression_percent from bundle where b_name ='"+bundle+"'");
		
		while(bundleData.next()){
			int cqcompletion = bundleData.getInt(1);
			int regcompletion = bundleData.getInt(2);
			
			int sum = cqcompletion+regcompletion;
			bundleCompletion = sum/2;
			
			System.out.println(" CQ completion :  "+cqcompletion+" regression : "+regcompletion);
		}
		
		System.out.println("bundle complete percent ..with scenarioData + cqCompl + regCompl..... "+bundleCompletion);
		
		return bundleCompletion;
	}

	public int getRuleCompletionPercent(String bundle, String cq) {
	
		int rulecompletePercent = 0;
		SqlRowSet rulePercent = jt.queryForRowSet("select completion_percent from rulegroup where bundleName ='"+bundle+"' and cqname ='"+cq+"'");
		System.out.println("...........select completion_percent from rulegroup where bundleName ='"+bundle+"' and cqname ='"+cq+"'");
		int n =0;
		int sum =0;
		while(rulePercent.next()){
			rulecompletePercent = rulePercent.getInt(1);
//			rulecompletePercent = rulecompletePercent+rulePercent.getInt(1);
			sum = sum+rulecompletePercent;
			n++;
			
			System.out.println("Inside query.getRuleCopletionPercent: number of rules "+n+"    "+rulecompletePercent);
			
		}
		int ruleComplete = sum/n;
		return ruleComplete;
	}

	public List<String> getRegressionType(String bundleselected) {

		List<String> regressionTypes = new ArrayList<String>();
		SqlRowSet regType = jt.queryForRowSet("select regression_type from regression where b_name ='"+bundleselected+"'");
		
		while(regType.next()){

			regressionTypes.add(regType.getString(1));
			
		}
		return regressionTypes;
	}

//	public RoleSegregration checkConfirmity(String userName, String role, String cqname) {
//		RoleSegregration ruleSeg = new RoleSegregration();
//		
//		SqlRowSet signedUpStatus = jt.queryForRowSet("select cqname, designReviewer, scenarioTester, testReviewer, overAllReviewer from cq where"
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
////			
////			numberOfRoles.add(signedUpStatus.getString(2));
////			numberOfRoles.add(signedUpStatus.getString(3));
////			numberOfRoles.add(signedUpStatus.getString(4));
////			numberOfRoles.add(signedUpStatus.getString(5));
////			
//		}
//
//		if(ruleSeg.isEligible()){
//			ruleSeg.setEligible(true);	
//		}
//		
//		return ruleSeg;
//
//		
//	}

	public List<RulePojo> getRulelist(String bundleName, String cqName,
			String ruleNameExcluded, int ver) {

		List<RulePojo> rulesAfterExcluded = new ArrayList<RulePojo>();
		
		SqlRowSet rulesFromDB = jt.queryForRowSet("select bundleName, cqName, ruleName, ver from rulegroup where bundleName='"+bundleName+"' and cqname='"+cqName+"' and ruleName ='"+ruleNameExcluded+"' "
				+ "and ver !="+ver);
		System.out.println("........select bundleName, cqName, ruleName, ver from rulegroup where bundleName='"+bundleName+"' and cqname='"+cqName+"' and ruleName ='"+ruleNameExcluded+"' "
				+ "and ver !="+ver);
		
		while(rulesFromDB.next()){
			
			RulePojo ruleBean = new RulePojo(); 
			ruleBean.setBundleName(rulesFromDB.getString(1));
			ruleBean.setCqName(rulesFromDB.getString(2));
			ruleBean.setRuleGroupName(rulesFromDB.getString(3));
			ruleBean.setVersion(rulesFromDB.getInt(4));
			rulesAfterExcluded.add(ruleBean);	
		}
		return rulesAfterExcluded;
	}

	public List<RulePojo> getRulelist(String bundleNumber, String cqNumber,
			String ruleGroupName, String ver) {

		List<RulePojo> ruleList = new ArrayList<RulePojo>();
		
		SqlRowSet eligibleRules = jt.queryForRowSet("select bundleName, cqname, ruleName, ver from ruleGroup where bundleName='"+bundleNumber+"' and cqname='"+cqNumber+"' "
				+ "and ruleName ='"+ruleGroupName+"' and ver !="+ver);
		
		System.out.println("............select bundleName, cqname, ruleName, ver from ruleGroup where bundleName='"+bundleNumber+"' and cqname='"+cqNumber+"' "
				+ "and ruleName ='"+ruleGroupName+"' and ver !="+ver);
		
		
		while(eligibleRules.next()){
		
			RulePojo rulepojo = new RulePojo();
			rulepojo.setBundleName(eligibleRules.getString(1));
			rulepojo.setCqName(eligibleRules.getString(2));
			rulepojo.setRuleGroupName(eligibleRules.getString(3));
			rulepojo.setVersion(eligibleRules.getInt(4));
		
			ruleList.add(rulepojo);
		}
		
		return ruleList;
	}

	public Table getCQsignUpstatus(String bundleSelected) {
		final Table table = new Table();
		
		List<RoleSegregration> roles = new ArrayList<RoleSegregration>();
		RoleSegregration roleSignup = new RoleSegregration();
		
		SqlRowSet cqs = jt.queryForRowSet("select cqname from cq where bundleName='"+bundleSelected+"'");
		
			SqlRowSet cqroles = jt.queryForRowSet("select cqname, designUpdater, designReviewer, scenarioTester, testReviewer, overAllReviewer from cq where bundleName='"+bundleSelected+"'");
System.out.println("........select cqname, designUpdater, designReviewer, scenarioTester, testReviewer, overAllReviewer from cq where bundleName='"+bundleSelected+"'");
		
			table.addContainerProperty("CQ Name",  String.class, null);
			table.addContainerProperty("Rule Name",  String.class, null);
			table.addContainerProperty("Version",  Integer.class, null);
			table.addContainerProperty("Rule Lead", String.class, null);
			table.addContainerProperty("Design Reviewer", String.class, null);
			table.addContainerProperty("Rule Creator", String.class, null);
			table.addContainerProperty("Rule Tester", String.class, null);
			table.addContainerProperty("Rule Reviewer", String.class, null);
			table.addContainerProperty("Scenario Tester", String.class, null);
			table.addContainerProperty("Test Reviewer", String.class, null);
			table.addContainerProperty("Over All Reviewer", String.class, null);

			table.setWidth("100%");
			table.setColumnCollapsingAllowed(true);
			
			table.setColumnWidth("CQ Name", 150);
			table.setColumnWidth("Rule Name", 75);
			table.setColumnWidth("Ver",  25);
			table.setColumnWidth("Rule Lead", 85);
			table.setColumnWidth("Design Reviewer", 105);
			table.setColumnWidth("Rule Creator", 105);
			table.setColumnWidth("Rule Tester", 107);
			table.setColumnWidth("Rule Reviewer", 107);
			table.setColumnWidth("Scenario Tester", 107);
			table.setColumnWidth("Test Reviewer", 107);
			table.setColumnWidth("Over All Reviewer", 107);

			int n=1;
			while(cqroles.next()){
				
				roleSignup.setCqname(cqroles.getString(1));
				roleSignup.setRuleLead(cqroles.getString(2));
				roleSignup.setDesignReviewer(cqroles.getString(3));
				roleSignup.setScenarioTester(cqroles.getString(4));
				roleSignup.setTestReviewer(cqroles.getString(5));
				roleSignup.setOverAllChangeReviwer(cqroles.getString(6));

				SqlRowSet ruleRoles = jt.queryForRowSet("select rule_creator, rule_tester, rule_reviewer, ruleName, ver from rulegroup where cqname='"+roleSignup.getCqname()+"' and bundleName='"+bundleSelected+"'");

				while(ruleRoles.next()){
					roleSignup.setRuleCreator(ruleRoles.getString(1));
					roleSignup.setRuleTester(ruleRoles.getString(2));
					roleSignup.setRuleReviewer(ruleRoles.getString(3));
					roleSignup.setRuleName(ruleRoles.getString(4));
					roleSignup.setVer(ruleRoles.getInt(5));
				}
				
		table.addItem(new Object[]{roleSignup.getCqname(), roleSignup.getRuleName(), roleSignup.getVer(), roleSignup.getRuleLead(),
				roleSignup.getDesignReviewer(), roleSignup.getRuleCreator(), roleSignup.getRuleTester(), roleSignup.getRuleReviewer(),
				roleSignup.getScenarioTester(), roleSignup.getTestReviewer(), roleSignup.getOverAllChangeReviwer()}, n);
		n++;
		roles.add(roleSignup);						
			}
	
				
			
//		}
		

		return table;
	}

	public List<BundleStatusPojo> getStatusTodate(Date todayDate) {

		List<BundleStatusPojo> statusList = new ArrayList<BundleStatusPojo>();
		BundleStatusPojo bundleStatus = new BundleStatusPojo();
		
//		String statusExpected = null;
		
		SqlRowSet expectedStatusToDate = jt.queryForRowSet("select designUptarget, designreviewtarget, rulecreateTarget, ruletesttarget, "
				+ "rulereviewtarget, scenariotesttarget, testreviewtarget, overallreviewtarget, regressiontarget,"
				+ "designReviewPer, designUpPer, ruleCreatePer, ruleTestPer, ruleReviewPer, scenarioTestPer, testReviewPer, overallReviewPer, b_name "
				+ " from bundle_details where Deployment !='Deployed'");
		
		
		BundlePojo bpojo = new BundlePojo();
		int n=1;
		
		while(expectedStatusToDate.next()){
			bpojo.setDesignUptarget(expectedStatusToDate.getDate(1));
			bpojo.setDesignreviewtarget(expectedStatusToDate.getDate(2));
			bpojo.setRulecreateTarget(expectedStatusToDate.getDate(3));
			bpojo.setRuletesttarget(expectedStatusToDate.getDate(4));
			bpojo.setRulereviewtarget(expectedStatusToDate.getDate(5));
			bpojo.setScenariotesttarget(expectedStatusToDate.getDate(6));
			bpojo.setTestreviewtarget(expectedStatusToDate.getDate(7));
			bpojo.setOverallreviewtarget(expectedStatusToDate.getDate(8));
			bpojo.setRegressiontarget(expectedStatusToDate.getDate(9));
			
			bpojo.setDesignUpPer(expectedStatusToDate.getInt(10));
			bpojo.setDesignReviewPer(expectedStatusToDate.getInt(11));
			bpojo.setRuleCreatePer(expectedStatusToDate.getInt(12));
			bpojo.setRuleTestPer(expectedStatusToDate.getInt(13));
			bpojo.setRuleReviewPer(expectedStatusToDate.getInt(14));
			bpojo.setScenarioTestPer(expectedStatusToDate.getInt(15));
			bpojo.setTestReviewPer(expectedStatusToDate.getInt(16));
			bpojo.setOverallReviewPer(expectedStatusToDate.getInt(17));
			
			bpojo.setBname(expectedStatusToDate.getString(18));
			
			bundleStatus = getExpectedDate(todayDate, bpojo);
			
			statusList.add(bundleStatus);
			
			System.out.println(bundleStatus.getRegressionComplete()+"   reg%  ......... cqcomplete..... "+bundleStatus.getCqComplete());
			n++;
		}
		
		
		for (int i = 0; i < statusList.size(); i++) {
			System.out.println(statusList.get(i).getBundleName());
		}
		return statusList;
	}

	private BundleStatusPojo getExpectedDate(Date todayDate, BundlePojo bpojo ) {
		
		java.sql.Date myDate = DateConversion.getSqlDate(todayDate.getTime());
		
		if(myDate.before(bpojo.getDesignUptarget())){
			bundleStatus.setBundleName(bpojo.getBname());
			bundleStatus.setTargetDt(bpojo.getDesignUptarget());
			bundleStatus.setTargetPercent(bpojo.getDesignUpPer());
			
		} else if (myDate.before(bpojo.getDesignreviewtarget())){
			bundleStatus.setBundleName(bpojo.getBname());
			bundleStatus.setTargetDt(bpojo.getDesignreviewtarget());
			bundleStatus.setTargetPercent(bpojo.getDesignReviewPer());

		} else if(myDate.before(bpojo.getRulecreateTarget())){
			System.out.println(bpojo.getRulecreateTarget()+"     before myDate   "+myDate);
			bundleStatus.setBundleName(bpojo.getBname());
			bundleStatus.setTargetDt(bpojo.getRulecreateTarget());
			bundleStatus.setTargetPercent(bpojo.getRuleCreatePer());
		} else if (myDate.before(bpojo.getRuletesttarget())){
			bundleStatus.setBundleName(bpojo.getBname());
			bundleStatus.setTargetDt(bpojo.getRuletesttarget());
			bundleStatus.setTargetPercent(bpojo.getRuleTestPer());
			bundleStatus.setExpectedStage("Rule Testing");
			
		} else if (myDate.before(bpojo.getRulereviewtarget())){
			bundleStatus.setBundleName(bpojo.getBname());
			bundleStatus.setTargetDt(bpojo.getRulereviewtarget());
			bundleStatus.setTargetPercent(bpojo.getRuleReviewPer());
			System.out.println(bpojo.getRulereviewtarget()+"     before ruleReview   "+myDate);

		} else if (myDate.before(bpojo.getScenariotesttarget())){
			bundleStatus.setBundleName(bpojo.getBname());
			bundleStatus.setTargetDt(bpojo.getTestreviewtarget());
			bundleStatus.setTargetPercent(bpojo.getScenarioTestPer());
			System.out.println("....myDate : "+myDate+"  ScenarioTest target  "+bpojo.getScenariotesttarget());

		} else if (myDate.before(bpojo.getTestreviewtarget())){
			bundleStatus.setBundleName(bpojo.getBname());
			bundleStatus.setTargetDt(bpojo.getTestreviewtarget());
			bundleStatus.setTargetPercent(bpojo.getTestReviewPer());
			System.out.println("test review target "+bpojo.getTestreviewtarget());

		} else if (myDate.before(bpojo.getOverallreviewtarget()) || myDate.equals(bpojo.getOverallreviewtarget())){
			bundleStatus.setBundleName(bpojo.getBname());
			bundleStatus.setTargetDt(bpojo.getOverallreviewtarget());
			bundleStatus.setTargetPercent(bpojo.getOverallReviewPer());
//			statusList.add(bundleStatus);
			System.out.println("overall review target " + bpojo.getOverallreviewtarget());
		} else{
			
			bundleStatus.setBundleName(bpojo.getBname());
			bundleStatus.setTargetDt(bpojo.getDesignUptarget());
			bundleStatus.setTargetPercent(10000);
			Notification.show("didn't fit anywhere");
		}

		System.out.println("while getting setting data based on today's date:   "+bundleStatus.getBundleName());
		
//		try {
			
			bundleStatus = getCompleteStatus(bpojo.getBname());
			
			bundleStatus = getRegressionCompletion(bpojo.getBname());
			
//			SqlRowSet allbundleList = jt.queryForRowSet("select total_bundleCompletion from bundle where status !='Deployed' and b_name='"+bpojo.getBname()+"'");
//			SqlRowSet allbundleList = jt.queryForRowSet("select total_bundleCompletion from bundle where status !='Deployed' and b_name='"+bpojo.getBname()+"'");
//			while(allbundleList.next()){
//				int completePercent = allbundleList.getInt(1);
//				bundleStatus.setCurrentPrecent(completePercent);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		SqlRowSet allbundleList = jt.queryForRowSet("select total_bundleCompletion from bundle where status !='Deployed' and b_name='"+bpojo.getBname()+"'");
		int stexpected = 0;
		int stcomplete = 0;

		

		System.out.println(".........select total_bundleCompletion from bundle where status !='Deployed' and b_name='"+bpojo.getBname());
		return bundleStatus;
	}

	public BundleStatusPojo getRegressionCompletion(String bname) {
		
		SqlRowSet regressionComplete = jt.queryForRowSet("SELECT compl_percent, regression_type from regression where b_name='"+bname+"'");

		System.out.println("...........x   .... SELECT compl_percent, regression_type from regression where b_name='"+bname+"'");
		
		int regressionCompletePercent =0;
		int n =0;

		RegressionPojo regressionpojo = new RegressionPojo();
		
		while(regressionComplete.next()){
			int regressComplete = regressionComplete.getInt(1);
			regressionpojo.setBundleName(regressionComplete.getString(2));
			regressionCompletePercent = regressionCompletePercent+regressComplete;
			n++;
			
			int totalRegressionComplete = regressionCompletePercent/n;
			
			bundleStatus.setRegressionComplete(totalRegressionComplete);
			System.out.println("Regression Complete      :  "+bundleStatus.getRegressionComplete());
		}
		
		
		
		return bundleStatus;
	}

	public BundleStatusPojo getCompleteStatus(String bname) {

		SqlRowSet completedCQelement = jt.queryForRowSet("select cqname, compDesignUpdate, compDesignReview, compRuleReview, "
				+ "compScenario, compTestReview, compOverAllReview, compDataLoad from cq where bundleName='"+bname+"'");
		
		
		CQstatusPojo cqcomplete = new CQstatusPojo();
		
		
		
		while(completedCQelement.next()){
			cqcomplete.setCqName(completedCQelement.getString(1));
			cqcomplete.setDesignUpPer(completedCQelement.getInt(2));
			cqcomplete.setDesignReviewPer(completedCQelement.getInt(3));
//			cqcomplete.setTotalRuleComplete(completedCQelement.getInt(3));
			//calculatin ruleComplete
			RulePojo ruleBean = getRuleCompleteStatus(cqcomplete.getCqName(), bname);
			cqcomplete.setTotalRuleComplete(ruleBean.getRuleCompletePercent());
					
			System.out.println("cqcomplete calculated: ......xxxxxx.................  "+cqcomplete.getTotalRuleComplete());
			
			cqcomplete.setRuleReviewPer(completedCQelement.getInt(4));
			cqcomplete.setScenarioTestPer(completedCQelement.getInt(5));
			cqcomplete.setTestReviewPer(completedCQelement.getInt(6));
			cqcomplete.setOverallReviewPer(completedCQelement.getInt(7));
			cqcomplete.setScenarioDataLoad(completedCQelement.getInt(8));
			
			int completePercent = cqcomplete.getDesignUpPer() +
					cqcomplete.getDesignReviewPer()+
					cqcomplete.getTotalRuleComplete()+
					cqcomplete.getScenarioDataLoad()+
					cqcomplete.getRuleReviewPer()+
					cqcomplete.getScenarioTestPer()+
					cqcomplete.getTestReviewPer()+
					cqcomplete.getOverallReviewPer();
					
			
			bundleStatus.setCqComplete(completePercent/8);
		}

		System.out.println("..............cqcompelte percent = "+bundleStatus.getCqComplete());
		
		return bundleStatus;
	}

	public BundlePojo getBundlesTargetDt(String bundleSelected) {
		BundlePojo bundleTargetDtinfo = new BundlePojo();
		
		SqlRowSet bundleinfo = jt.queryForRowSet("select b_name, bundle_lead, designuptarget, designreviewtarget, rulecreatetarget, ruletesttarget, rulereviewtarget, "
				+ "scenarioDataTargetDt, scenariotesttarget, testreviewtarget, overallreviewtarget, regressionTarget"
				+ " from bundle_details where b_name='"+bundleSelected+"'");
		
		while(bundleinfo.next()){
			bundleTargetDtinfo.setBname(bundleinfo.getString(1));
			bundleTargetDtinfo.setBundleLead(bundleinfo.getString(2));
			bundleTargetDtinfo.setDesignUptarget(bundleinfo.getDate(3));
			bundleTargetDtinfo.setDesignreviewtarget(bundleinfo.getDate(4));
			bundleTargetDtinfo.setRulecreateTarget(bundleinfo.getDate(5));
			bundleTargetDtinfo.setRuletesttarget(bundleinfo.getDate(6));
			bundleTargetDtinfo.setRulereviewtarget(bundleinfo.getDate(7));
			bundleTargetDtinfo.setScenarioDataTarget(bundleinfo.getDate(8));
			bundleTargetDtinfo.setScenariotesttarget(bundleinfo.getDate(9));
			bundleTargetDtinfo.setTestreviewtarget(bundleinfo.getDate(10));
			bundleTargetDtinfo.setOverallreviewtarget(bundleinfo.getDate(11));
			bundleTargetDtinfo.setRegressiontarget(bundleinfo.getDate(12));
			
		}
				
		return bundleTargetDtinfo;
	}

	public BundlePojo getBundlesTargetPercent(String bundleSelected) {
		BundlePojo bundleTargetPercentinfo = new BundlePojo();
		
		SqlRowSet bundleinfo = jt.queryForRowSet("select b_name, bundle_lead, designupper, designupper, rulecreateper, ruletestper, rulereviewper, "
				+ "scenario_dataLoad_percent, scenariotestper, testreviewper, overallreviewper, regressionper"
				+ " from bundle_details where b_name='"+bundleSelected+"'");

		while(bundleinfo.next()){
	
			bundleTargetPercentinfo.setBname(bundleinfo.getString(1));
			bundleTargetPercentinfo.setBundleLead(bundleinfo.getString(2));
			bundleTargetPercentinfo.setDesignUpPer(bundleinfo.getInt(3));
			bundleTargetPercentinfo.setDesignReviewPer(bundleinfo.getInt(4));
			bundleTargetPercentinfo.setRuleCreatePer(bundleinfo.getInt(5));
			bundleTargetPercentinfo.setRuleTestPer(bundleinfo.getInt(6));
			bundleTargetPercentinfo.setRuleReviewPer(bundleinfo.getInt(7));
			bundleTargetPercentinfo.setScenarioDataPer(bundleinfo.getInt(8));
			bundleTargetPercentinfo.setScenarioTestPer(bundleinfo.getInt(9));
			bundleTargetPercentinfo.setTestReviewPer(bundleinfo.getInt(10));
			bundleTargetPercentinfo.setOverallReviewPer(bundleinfo.getInt(11));
			bundleTargetPercentinfo.setRegressionPer(bundleinfo.getInt(12));

		}
		return bundleTargetPercentinfo;
	}

	public CQstatusPojo getCqCompletePercentDetail(Object bundleName) {

		CQstatusPojo cqDetail = new CQstatusPojo();
		int percentComplete =0;

		SqlRowSet cqcompletionstatus = jt.queryForRowSet("select cqname, compDesignUpdate, compDesignReview, "
				+ "compDataLoad, compRuleReview, compScenario, compTestReview, compOverAllReview from cq where bundleName ='"
				+ bundleName+"'");
		
		while(cqcompletionstatus.next()){
			
			cqDetail.setCqName(cqcompletionstatus.getString(1));
			int designUp = cqcompletionstatus.getInt(2);
			int designReview = cqcompletionstatus.getInt(3);
			int dataload = cqcompletionstatus.getInt(4);
			
			int ruleReview = cqcompletionstatus.getInt(5);
			int scenario = cqcompletionstatus.getInt(6);
			int testreview = cqcompletionstatus.getInt(7);
			int overAll = cqcompletionstatus.getInt(8);

			RulePojo ruleBean1 = getRuleCompleteStatus(bundleName.toString(), cqDetail.getCqName());
			System.out.println("ruleBean getCompleteStatus....=============================xxxxx............. "+ruleBean1.getRuleCompletePercent()+ruleBean1.getCqName());
			
			int ruleCompletion = ruleBean1.getRuleCompletePercent();
			
			System.out.println("ruleCompletion ....... called by getRuleCompletionStatus......."+ruleCompletion);
			
			int averageCompletion = designUp+designReview+dataload+ruleCompletion+ruleReview+scenario+testreview+overAll;
			percentComplete = averageCompletion/8;

			System.out.println("averageComplete Calculation   : "+averageCompletion+" percentComplete : "+percentComplete);
			cqDetail.setDesignUpPer(designUp);
			cqDetail.setDesignReviewPer(designReview);
			cqDetail.setScenarioDataLoad(dataload);
			cqDetail.setTotalRuleComplete(ruleCompletion);
			cqDetail.setScenarioTestPer(scenario);
			cqDetail.setTestReviewPer(testreview);
			cqDetail.setOverallReviewPer(overAll);
			cqDetail.setCurrentPercent(percentComplete);
			
		}

		
		return cqDetail;
	}

	public RulePojo getRuleCompleteStatus(String bundleName, String cqName){
		RulePojo rulePojo = new RulePojo();
		
		SqlRowSet ruleDetails = jt.queryForRowSet("select ruleName, ver, rule_creator_complete, rule_test_complete, rule_review_complete from ruleGroup where cqname='"+cqName.toString()+"' and bundleName='"+bundleName+"'");

		System.out.println("/+++++++++++++++   select ruleName, ver, rule_creator_complete, rule_test_complete, rule_review_complete from ruleGroup where cqname='"+cqName.toString()+"' and bundleName='"+bundleName+"'");
		int totalRuleComplete =0;
		int n =0;
		int returningSum =0;
		while(ruleDetails.next()){
			System.out.println("whileLoop ruleDetails..............");
			String ruleName	= ruleDetails.getString(1);
			
			int ver =ruleDetails.getInt(2);
			rulePojo.setRuleCreatePercent(ruleDetails.getInt(3));
			rulePojo.setRuleTestPercent(ruleDetails.getInt(4));
			rulePojo.setRuleReviewPercent(ruleDetails.getInt(5));
			n++;

			int cal = rulePojo.getRuleCreatePercent()+rulePojo.getRuleTestPercent()+rulePojo.getRuleReviewPercent();
			System.out.println("    "+rulePojo.getRuleCreatePercent()+"    "+rulePojo.getRuleTestPercent()+"    "+rulePojo.getRuleReviewPercent());
			System.out.println("..................calculation /3......"+cal/3+"   n= "+n);
			int eachruleCompletion = cal/3;
			returningSum = returningSum+eachruleCompletion;
			
			System.out.println("eachRuleTotal =   "+eachruleCompletion);
			System.out.println("Accumulated......."+returningSum);
			
			rulePojo.setRuleCompletePercent((rulePojo.getRuleCreatePercent()+rulePojo.getRuleTestPercent()+rulePojo.getRuleReviewPercent())/3);
			
//			int t = returningSum/n;
			totalRuleComplete = returningSum/n;
			
		}
		
		rulePojo.setRuleCompletePercent(totalRuleComplete);
		System.out.println("returing sum of all ruleCompleted  : ===================  "+rulePojo.getRuleCompletePercent());

		return rulePojo;
	}
	
//	public RulePojo getRuleDetaillist(Object bundleName, Object cqName){
//		RulePojo ruleData = new RulePojo();
//		SqlRowSet ruleDetails = jt.queryForRowSet("select ruleName, ver, rule_creator_complete, rule_creator, rule_test_complete, rule_tester, rule_review_complete, rule_reviewer from ruleGroup where cqname='"+cqName.toString()+"' "
//				+ "and bundleName='"+bundleName.toString()+"'");
//		
//	}
	
	public List<RulePojo> getRuleDetaillist(Object bundleName, Object cqName, Object rule, Object ver) {
		
		List<RulePojo> ruleDataList = new ArrayList<RulePojo>();
		RulePojo ruleData = new RulePojo();
		int convertedVersion = Integer.parseInt(ver.toString());
		
		SqlRowSet ruleDetails = jt.queryForRowSet("select ruleName, ver, rule_creator_complete, rule_creator, rule_test_complete, rule_tester, rule_review_complete, rule_reviewer from ruleGroup where cqname='"+cqName.toString()+"' "
				+ "and bundleName='"+bundleName.toString()+"' and ruleName='"+rule.toString()+"' and ver="+convertedVersion);
		
		System.out.println(".........select ruleName, ver, rule_creator_complete, rule_creator, rule_test_complete, rule_tester, rule_review_complete, rule_reviewer from ruleGroup where cqname='"+cqName.toString()+"' "
				+ "and bundleName='"+bundleName.toString()+"' and ruleName='"+rule.toString()+"' and ver="+convertedVersion);

		while(ruleDetails.next()){
			
			ruleData.setRuleGroupName(ruleDetails.getString(1));
			ruleData.setVersion(ruleDetails.getInt(2));
			ruleData.setRuleCreatePercent(ruleDetails.getInt(3));
			ruleData.setRuleCreator(ruleDetails.getString(4));
			ruleData.setRuleTestPercent(ruleDetails.getInt(5));
			ruleData.setRuleTester(ruleDetails.getString(6));
			ruleData.setRuleReviewPercent(ruleDetails.getInt(7));
			ruleData.setRuleReviewer(ruleDetails.getString(8));
			ruleData.setBundleName(bundleName.toString());
			ruleData.setCqName(cqName.toString());
			
			ruleDataList.add(ruleData);		
		}
		
		return ruleDataList;
	}
}
