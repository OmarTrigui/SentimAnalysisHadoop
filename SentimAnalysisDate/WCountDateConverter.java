package enis.hadoop.wordcount;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class WCountDateConverter {

	final static int BASE_MONTH = 1;
	final static int BASE_YEAR = 1900;

	public static String getMonth(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE MMM dd kk:mm:ss z yyyy");
		Date d = null;

		try {
			d = sdf.parse(date);
		} catch (ParseException e) {

		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int parsedYear = cal.get(Calendar.YEAR)- BASE_YEAR;
		int parsedMonth = cal.get(Calendar.MONTH) - BASE_MONTH;

		int result = parsedYear * 12 + parsedMonth + 1;
		
		return  Integer.toString(result);
		
	}

	public static String monthToReadableDate(String months) {
		int numMonth = Integer.parseInt(months);

		int year = numMonth / 12;
		int month = (numMonth % 12);

		String monthName = new DateFormatSymbols().getMonths()[month];

		return monthName + " " + (year + 1900);

	}

}