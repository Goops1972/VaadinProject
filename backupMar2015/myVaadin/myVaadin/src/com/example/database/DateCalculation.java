package com.example.database;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import oracle.sql.INTERVALYM;

import com.example.myvaadin.DateConversion;

public class DateCalculation {

	public static void main(String[] args) throws ParseException {

		DateConversion dateConv = new DateConversion();
		Date d = DateUtils.parseDate("2015-02-01", "yyyy-MM-dd");
		java.sql.Date newDate = dateConv.getSqlDate(new Date().getTime());
		java.sql.Date otherDate = dateConv.getSqlDate(d.getTime());
		
		int anotherDate = newDate.compareTo(otherDate);
		System.out.println(newDate);
		System.out.println(otherDate);
		
		int diff = daysBetween(newDate.getTime(), otherDate.getTime());
		System.out.println(diff+".....dys");
	}

	public static int daysBetween(long t1, long t2) {
	    return (int) (t2 - t1) / (1000 * 60 * 60 * 24);
	} 
}
