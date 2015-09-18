package com.example.database;

import java.sql.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.example.pojo.BundleTarget;

public class StatusCheck {

	
//	BUNDLE_LEAD("Design Update"), DESIGN_REVIEWER("Design Reviewer"), RULE_CREATOR("Rule Creation"), RULE_TESTER("Rule Testing");
	
	JdbcTemplate jt = Template.getTemplate();
	
	private String statusCode;
 
	public StatusCheck(){
		
	}
 
	public String getStatusCode() {
		
		return statusCode;
}

	public static String getStatus(String signedRole) {
		
		String status = null;
		if(signedRole.equalsIgnoreCase("Design Updater")){
			status = "Design Update";
		} else if (signedRole.equalsIgnoreCase("Design Reviewer")){
			status = "Design Review";
		}else if (signedRole.equalsIgnoreCase("Rule Creator")){
			status = "rule cr";
		}else if (signedRole.equalsIgnoreCase("Rule Tester")){
			status = "Rule test";
		}else if (signedRole.equalsIgnoreCase("Scenario Test")){
			status = "Design Review";
		}
		
		return status;
	}
	
	public BundleTarget getTargetBundleStatus(Date todayDate, String bundle){
		
		
		
		BundleTarget bstatus = new BundleTarget();

		SqlRowSet target = jt.queryForRowSet("select status, designupper, st_date from bundle_details where b_name ='"+bundle+"'" );
		
		while(target.next()){
			
			String myStatus = target.getString(1);
			int mytarget = target.getInt(2);
			Date myDate = target.getDate(3);
			
			System.out.println(todayDate);
				
//			if(myDate.before(todayDate) && myDate.after(otherDate)){
			if(myDate.before(todayDate)){
				System.out.println("is before");
			} else if(myDate.after(todayDate)){
				System.out.println("is after......");
			} else {
				System.out.println("date matched...");
			
			}
			
			bstatus.setTargetDt(myDate);
			bstatus.setStage(myStatus);
			bstatus.setExpPercent(mytarget);
			
		}
		
		return bstatus;
		
		
	}
}


