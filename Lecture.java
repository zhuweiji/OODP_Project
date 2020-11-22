import java.util.*;
https://github.com/zhuweiji/OODP_Project/blob/master/Lecture
public class Lecture implements Lesson {
	public Calendar StartTime, EndTime;
	public String Day, Venue;
	public Lecture (String Day, String Venue, Calendar StartTime, Calendar EndTime) {
		this.StartTime = StartTime;
		this.EndTime = EndTime;
		this.Day = Day;
		this.Venue = Venue;
	}
	public void setDay() {
		this.Day = Day;
	}
	public String getDay() {
		return Day;
	}
	public void setStartTime() {
		this.StartTime = StartTime;
	}
	public Calendar getStartTime() {
		return StartTime;
	}
	public void setEndTime() {
		this.EndTime = EndTime;
	}
	public Calendar getEndTime() {
		return EndTime;
	}
	public void setVenue() {
		this.Venue = Venue;
	}
	public String getVenue() {
		return Venue;
	}
}
