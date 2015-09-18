package com.example.database;

import java.util.Calendar;
import java.util.Date;

import com.example.myvaadin.DateConversion;
import com.example.pojo.BundleTarget;

public class Client {

	public static void main(String[] args) {

		Date myDate = new Date();
		long sqlDate = myDate.getTime();
		DateConversion dc = new DateConversion();
		java.sql.Date todayDate = dc.getSqlDate(sqlDate);
		
		StatusCheck sc = new StatusCheck();
		
		BundleTarget bt = sc.getTargetBundleStatus(todayDate, "bundle1");
		System.out.println(bt.getStage()+"  "+bt.getExpPercent()+" "+bt.getTargetDt());
		
	}

}
