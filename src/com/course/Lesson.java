package com.course;

public class Lesson {
	private int indexID; 
	private String lessonType;
	private String lessonDay;
	private String lessonVenue;
	private String lessonTime;
	
	public Lesson (int indexID, String lessonType, String lessonDay, String lessonTime, String lessonVenue){
		this.indexID = indexID;
		this.lessonType = lessonType;
		this.lessonDay = lessonDay;
		this.lessonTime = lessonTime;
		this.lessonVenue = lessonVenue;
	}

	public int getIndexID() {
		return indexID;
	}

	public void setIndexID(int indexID) {
		this.indexID = indexID;
	}

	public String getLessonType() {
		return lessonType;
	}

	public void setLessonType(String lessonType) {
		this.lessonType = lessonType;
	}

	public String getLessonDay() {
		return lessonDay;
	}

	public void setLessonDay(String lessonDay) {
		this.lessonDay = lessonDay;
	}

	public String getLessonVenue() {
		return lessonVenue;
	}

	public void setLessonVenue(String lessonVenue) {
		this.lessonVenue = lessonVenue;
	}

	public String getLessonTime() {
		return lessonTime;
	}

	public void setLessonTime(String lessonTime) {
		this.lessonTime = lessonTime;
	}
	
	public boolean equals(int o) {
		return getIndexID() == o;
	}
}