package com.course;

import java.io.*;
import java.text.*;
import java.util.*;

import com.lesson.CalendarController;
import com.user.Student;

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
	 * @param fileName
	 * @return
	 * @throws IOException
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
     * @param filename
     * @throws IOException
     * @throws ParseException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<Student> initStudents() throws IOException, ParseException {
		// read String from text file
		ArrayList<String> stringArray = (ArrayList) read("src/data/students_info.txt");
		
		for (int i = 0; i < stringArray.size(); i++) {
			String st = (String) stringArray.get(i);
			
			// get individual 'fields' of the string separated by SEPARATOR
			// pass in the string to the string tokenizer using delimiter ","
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); 

			String userid = star.nextToken().trim(); // first token
			String name = star.nextToken().trim(); // first token
			String matricNum = star.nextToken().trim(); // third token
			char gender = (star.nextToken().trim()).charAt(0); // fourth token
			String nationality = star.nextToken().trim(); // fifth token
			String email = star.nextToken().trim(); // sixth token
			int phoneno = Integer.parseInt((star.nextToken().trim())); //seventh token
			Calendar accessStart = CalendarController.stringToCalendar(star.nextToken().trim()); // eight token
			Calendar accessEnd = CalendarController.stringToCalendar(star.nextToken().trim()); // nine token
			String notiMode = star.nextToken().trim(); //tenth token

			Student std = new Student(userid, name, matricNum, gender, nationality, email, phoneno, accessStart, accessEnd, notiMode);
			
			// add to Students list
			studentList.add(std);
		}
		return studentList;
	}

	// an example of saving
	public static void saveStudents(ArrayList<Student> al) throws IOException {
		ArrayList<String> alw = new ArrayList<String>();// to store Studetns data

		for (int i = 0; i < al.size(); i++) {
			Student std = (Student) al.get(i);
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
			st.append(CalendarController.CaltoString(std.getAccessStart()));
			st.append(SEPARATOR);
			st.append(CalendarController.CaltoString(std.getAccessEnd()));
			st.append(SEPARATOR);
			st.append(std.getNotiMode());

			alw.add(st.toString());
		}
		write("src/data/student_info.txt", alw);
	}
}