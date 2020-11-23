package com.course;

import com.user.Student;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.*;

public class StudentData {
	
	@SuppressWarnings("rawtypes")
	public static void write(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName));

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}

	/** Read the contents of the given file.
	 *
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List read(String fileName) throws IOException {
		List data = new ArrayList();
		Scanner scanner = new Scanner(new FileInputStream(fileName));
		try {
			while (scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}
		return data;
	}
	
	public static final String SEPARATOR = "|";

	public static ArrayList<Student> studentList = new ArrayList<Student>();
    /** Initialise the courses before application starts
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<Student> initStudents() throws IOException, ParseException {
		// read String from text file
		ArrayList<String> stringArray = (ArrayList) read("src/data/students_info.txt");

		for (String s : stringArray) {
			String st = (String) s;

			// get individual 'fields' of the string separated by SEPARATOR
			// pass in the string to the string tokenizer using delimiter ","
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);

			String userid = star.nextToken().trim(); // first token
			String name = star.nextToken().trim(); // first token
			String matricNum = star.nextToken().trim(); // third token
			String gender = (star.nextToken().trim()); // fourth token
			String nationality = star.nextToken().trim(); // fifth token
			String email = star.nextToken().trim(); // sixth token
			String course_of_study = star.nextToken().trim();
			String date_matriculated = star.nextToken().trim();
			String phoneno = (star.nextToken().trim()); //seventh token
			Calendar accessStart = CalendarController.stringToCalendar(star.nextToken().trim()); // eight token
			Calendar accessEnd = CalendarController.stringToCalendar(star.nextToken().trim()); // nine token
			String notiMode = star.nextToken().trim(); //tenth token

			Student std = new Student(userid, name, matricNum, gender, nationality, email, course_of_study,date_matriculated ,phoneno, accessStart, accessEnd, notiMode);

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
		write("src/data/student_info.txt", alw);
	}
}