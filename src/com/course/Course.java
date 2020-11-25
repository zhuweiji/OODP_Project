package com.course;

import java.util.*;

/**
 * Each course represents a specific subject under the school
 *
 */
public class Course {
	/**
	 * The course code unique to each module
	 */
	private String courseID;
	
	/**
	 * The name of the course
	 */
	private String courseName;
	
	/**
	 * The number of Academic Unit (AU) of the course
	 */
	private int AU;
	
	/**
	 * The school that offers the module
	 */
	private String school;

	/** Constructor for Course
	 */
	public Course (String courseID, String courseName, int au, String school) {
		this.courseID   		= courseID;
		this.courseName			= courseName;
		this.AU					= au;
		this.school				= school;
	}
	

	public Course(String courseName2, String school2, String courseID2, int totalCapacity, int acadUnits) {
	}


	public String getCourseID() {
		return courseID;
	}
	
	/**
	 * Change the course code
	 *
	 */
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getAU() {
		return AU;
	}

	public void setAU(int aU) {
		AU = aU;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Course) {
			Course st = (Course)o;
			return (getCourseID().equals(st.getCourseID()));
		}
		return false;
	}
	public String[] getAllDetails() {
		return new String[] {
				courseName,
				school,
				courseID,
				Integer.toString(AU) };
	}

	public String toString() {
		return "Course Name: " + courseName
				+ ", School: " + school
				+ ", CourseID: " + courseID
				+ ", Academic Units: " + AU;
	}


}