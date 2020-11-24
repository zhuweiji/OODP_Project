package com.course;


public class StudentCourse {

	private String userName;
	
	/**
	 * The course code unique to each module
	 */
	private String courseID;
	
	/**
	 * The list of indexes for the course that the student can register to
	 */
	private int indexID;
	
	private String registerStatus;
	
	public StudentCourse(String userName, String courseID, int indexID, String registerStatus) {
		this.userName = userName;
		this.courseID = courseID;
		this.indexID = indexID;
		this.registerStatus = registerStatus;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public int getIndexID() {
		return indexID;
	}

	public void setIndexID(int indexID) {
		this.indexID = indexID;
	}
	
	public String getRegisterStatus() {
		return registerStatus;
	}
	
	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}
}