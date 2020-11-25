package com.course;

import java.io.*;
import java.text.*;
import java.util.*;

import com.Main;
import com.user.*;

public class StudentData {
	public static final String SEPARATOR = ",";

	public static ArrayList<Student> studentList = new ArrayList<Student>();
    /** Initialise the courses before application starts
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<Student> initStudents() throws IOException, ParseException {
		// read String from text file
		ArrayList<String> stringArray = (ArrayList) IO.read(Main.studentinfopath.toString());
		if (stringArray == null){
			System.out.println("Couldn't read from student info database");
			return null;
		}

		studentList.clear();
		for (String s : stringArray) {
			// get individual 'fields' of the string separated by SEPARATOR
			// pass in the string to the string tokenizer using delimiter ","
			StringTokenizer star = new StringTokenizer(s, SEPARATOR);

			String userid = star.nextToken().trim(); // first token
			String userName = star.nextToken().trim();
			String name = star.nextToken().trim(); // first token
			String matricNum = star.nextToken().trim(); // third token
			String gender = (star.nextToken().trim()); // fourth token
			String nationality = star.nextToken().trim(); // fifth token
			String email = star.nextToken().trim(); // sixth token
			String course_of_study = star.nextToken().trim();
			String phoneno = (star.nextToken().trim()); //seventh token
			String date_matriculated = star.nextToken().trim();
			Calendar accessStart = CalendarController.stringToCalendar(star.nextToken().trim()); // eight token
			Calendar accessEnd = CalendarController.stringToCalendar(star.nextToken().trim()); // nine token
			String notiMode = star.nextToken().trim(); //tenth token
			Student std = new Student(userid, userName, name, matricNum, gender, nationality, email, course_of_study,phoneno,date_matriculated, accessStart, accessEnd, notiMode);
			// add to Students list
			studentList.add(std);
		}
		return studentList;
	}

	// an example of saving
	public static void saveStudents(ArrayList<Student> al) throws IOException {
		ArrayList<String> alw = new ArrayList<String>();// to store Studetns data

		for (Student student : al) {
			Student std = (Student) student;
			StringBuilder st = new StringBuilder();
			st.append(std.getUserid().trim());
			st.append(SEPARATOR);
			st.append(std.getUserName().trim());
			st.append(SEPARATOR);
			st.append(std.getName().trim());
			st.append(SEPARATOR);
			st.append(std.getMatricID().trim());
			st.append(SEPARATOR);
			st.append(std.getGender());
			st.append(SEPARATOR);
			st.append(std.getNationality());
			st.append(SEPARATOR);
			st.append(std.getPhone_number());
			st.append(SEPARATOR);
			st.append(std.getEmail());
			st.append(SEPARATOR);
			st.append(CalendarController.caltoString(std.getAccessStart()));
			st.append(SEPARATOR);
			st.append(CalendarController.caltoString(std.getAccessEnd()));
			st.append(SEPARATOR);
			st.append(std.getNotiMode());

			alw.add(st.toString());
		}
		IO.write(Main.studentinfopath.toString(), alw);
	}
}