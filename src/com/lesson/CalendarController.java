package com.lesson;

import java.util.*;
import java.text.*;

import java.util.*;
import java.text.*;

public class CalendarController {
	static Scanner sc = new Scanner(System.in);
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	public String CaltoString(Calendar cal) {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		return String.format("%02d/%02d/%4d %02d:%02d", day, month + 1, year, hour, minute);
	}
	
	public static Calendar stringToCalendar(String s) throws ParseException {
		Date date = dateFormat.parse(s);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
}

	
