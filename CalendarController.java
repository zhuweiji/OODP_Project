import java.util.*;
import java.text.*;

public class CalendarController {
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	public String DatetoString(Calendar cal) {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		return String.format("%02d/%02d/%4d", day, month+1, year);
	}
	public static String TimetoString(Calendar cal) {
		int hour = cal.get(Calendar.HOUR);
		int minute = cal.get(Calendar.MINUTE);
		return String.format("%02/d:%02d", hour, minute);
	}
	public static Calendar StringtoDate(String s) throws ParseException {
		Date date = dateFormat.parse(s);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	public static Calendar StringtoTime(String s) throws ParseException {
		Date time = timeFormat.parse(s);
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		return cal; 
	}
}
