package com.course;


public class StudentCourse {

	private String userid;
	private String username;
	/**
	 * List of courses under a school
	 */
	private String courseID;

	/**
	 * The list of indexes for the course that the student can register to
	 */
	private int indexID;

	private String registerStatus;

	public StudentCourse(String userid, String username, String courseID, int indexID, String registerStatus) {
		this.userid = userid;
		this.username = username;
		this.courseID = courseID;
		this.indexID = indexID;
		this.registerStatus = registerStatus;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID (String courseID) {
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