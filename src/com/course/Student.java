package com.course;

import java.util.*;

/**
 * Represents the Student who acts as the user of the 
 * planner to register their courses.
 * @version 1.0
 * @since 2017-03-21
 */

public class Student {
	
	private String userName;
	private String firstName; 
	private String lastName;
	private String matricNumber;
	private char gender;
	private String nationality; 
	private int mobileNo;
	private String email;
	private Calendar accessStart;
	private Calendar accessEnd;
	private int notiMode;
	
	//constructor
	public Student (String userName, String firstName, String lastName, String matricNumber, char gender, String nationality, int mobileNo, String email, Calendar accessStart, Calendar accessEnd, int notiMode) {
		this.userName 			= userName;
		this.firstName 			= firstName;
		this.lastName 			= lastName;
		this.matricNumber		= matricNumber;
		this.gender				= gender;
		this.nationality		= nationality;
		this.mobileNo           = mobileNo;
		this.email              = email;
		this.accessStart 		= accessStart;
		this.accessEnd 			= accessEnd;
		this.notiMode			= notiMode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMatricNumber() {
		return matricNumber;
	}

	public void setMatricNumber(String matricNumber) {
		this.matricNumber = matricNumber;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public int getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(int mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getAccessStart() {
		return accessStart;
	}

	public void setAccessStart(Calendar accessStart) {
		this.accessStart = accessStart;
	}

	public Calendar getAccessEnd() {
		return accessEnd;
	}

	public void setAccessEnd(Calendar accessEnd) {
		this.accessEnd = accessEnd;
	}

	public int getNotiMode() {
		return notiMode;
	}

	public void setNotiMode(int notiMode) {
		this.notiMode = notiMode;
	}
	
	
}
