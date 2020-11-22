package com.course;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CourseData {
	
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
	
	public static ArrayList<Course> courseList = new ArrayList<Course>();
    /** Initialise the courses before application starts
     * @throws IOException
     * @throws ParseException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<Course> initCourses() throws IOException, ParseException {
		// read String from text file
		ArrayList<String> stringArray = (ArrayList)  read("src/db/Course.txt");
		
		
        for (int i = 0 ; i < stringArray.size() ; i++) {
        	
				String field = (String) stringArray.get(i);
				
				// get individual 'fields' of the string separated by SEPARATOR
				// pass in the string to the string tokenizer using delimiter "," 
				StringTokenizer tokenizer = new StringTokenizer(field, SEPARATOR);	
				
				//first to fifth tokens
				String  courseName = tokenizer.nextToken().trim();	
				String school = tokenizer.nextToken().trim(); 
				String  courseID = tokenizer.nextToken().trim();	
				int totalCapacity = Integer.parseInt(tokenizer.nextToken().trim());
				int acadUnits = Integer.parseInt(tokenizer.nextToken().trim());
	
				// create Course object from file data
				Course course = new Course(courseName, school, courseID, totalCapacity, acadUnits);
				// add to Courses list 
				courseList.add(course) ;
		}
		return courseList ;
	}



	/** Save the courses that has been added during the session
	 * @param CourseToUpdate
	 * @throws IOException
	 */
	public static void saveCourses(ArrayList<Course> CourseToUpdate) throws IOException {
		ArrayList <String> courseListRename = new ArrayList<String>() ;// to store Courses data

        for (int i = 0 ; i < CourseToUpdate.size() ; i++) {
				Course course = (Course) CourseToUpdate.get(i);
				StringBuilder stringBuild =  new StringBuilder() ;
				stringBuild.append(course.getCourseName().trim());
				stringBuild.append(SEPARATOR);
				stringBuild.append(course.getSchool());
				stringBuild.append(SEPARATOR);
				stringBuild.append(course.getCourseID().trim().toUpperCase());
				stringBuild.append(SEPARATOR);
				stringBuild.append(course.getTotalCapacity());
				stringBuild.append(SEPARATOR);
				stringBuild.append(course.getAcadUnits());

				courseListRename.add(stringBuild.toString()) ;
			}
			write("src/db/Course.txt",courseListRename);
	}
}
