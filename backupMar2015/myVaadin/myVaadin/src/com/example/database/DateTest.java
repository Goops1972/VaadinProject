package com.example.database;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.example.myvaadin.DateConversion;

public class DateTest {

	public static void main(String[] args) throws ParseException {
		DateConversion dateConv = new DateConversion();
		DateCalculation dc = new DateCalculation();
		Date d = DateUtils.parseDate("2015-03-01", "yyyy-MM-dd");
		java.sql.Date newDate = dateConv.getSqlDate(new Date().getTime());
		java.sql.Date otherDate = dateConv.getSqlDate(d.getTime());
		
		int diff = dc.daysBetween(newDate.getTime(),otherDate.getTime());
		System.out.println(diff);
		
	}
}
