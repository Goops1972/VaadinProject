package com.example.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.example.database.Query;
import com.example.myvaadin.DateConversion;
import com.example.pojo.BundleStatusPojo;

public class ClientToTest {

	public static void main(String[] args) {
		BundleStatusPojo bundleStatus = new BundleStatusPojo();
		Date idate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		Calendar calendar = new GregorianCalendar(2013,0,31);
		System.out.println(sdf.format(calendar.getTime()));
		
		
		Date mdate = DateConversion.getSqlDate(idate.getTime());
		Query q = new Query();
//		bundleStatus = q.getStatusTodate(mdate);
		System.out.println(bundleStatus.getTargetDt());
	}

}
