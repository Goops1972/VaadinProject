package com.example.myvaadin;

import java.sql.Date;
import java.text.SimpleDateFormat;
/*Sample to call this file
Date myDate = designUpdate.getValue();
java.sql.Date sqlDate = DateConversion.getSqlDate(myDate.getTime());
*/

public class DateConversion {

	public static java.sql.Date getSqlDate(long time) {
		Date inputDt = new Date(time);
		
		return inputDt;
	}

	public static String getDateFormat(java.util.Date myDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String date = sdf.format(myDate); 
		return date;
	}

}
