package com.course;

import java.io.*;
import java.text.*;
import java.util.*;



public class StudentCourseData {

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

	public static ArrayList <StudentCourse> studentCourseList = new ArrayList<StudentCourse>() ;
	
    /** Initialize the courses before application starts
     * @param filename
     * @throws IOException
     * @throws ParseException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<StudentCourse> initStudentCourses() throws IOException, ParseException {
		// read String from text file
		ArrayList<String> stringArray = (ArrayList) read("src/db/StudentCourse.txt");
			
		if (stringArray.size() == 0){
			return new ArrayList<StudentCourse>();
		}
        for (int i = 0 ; i < stringArray.size() ; i++) {
        	
				String field = (String) stringArray.get(i);
				
				// get individual 'fields' of the string separated by SEPARATOR
				// pass in the string to the string tokenizer using delimiter "," 
				StringTokenizer tokenizer = new StringTokenizer(field, SEPARATOR);	
				
				//first to fifth tokens
				String  userName = tokenizer.nextToken().trim();	
				String  courseID = tokenizer.nextToken().trim();	
				int indexID = Integer.parseInt(tokenizer.nextToken().trim());
				String registerStatus = tokenizer.nextToken().trim();
				
				// create Course object from file data
				StudentCourse course = new StudentCourse(userName, courseID, indexID, registerStatus);
				// add to Courses list 
				studentCourseList.add(course) ;
		}
		return studentCourseList ;
	}

	/** Save the courses that has been added during the session
	 * @param CourseToUpdate
	 * @throws IOException
	 */
	public static void saveStudentCourses(ArrayList<StudentCourse> CourseToUpdate) throws IOException {
		ArrayList <String> courseList = new ArrayList<String>() ;// to store Courses data

        for (int i = 0 ; i < CourseToUpdate.size() ; i++) {
				StudentCourse course = (StudentCourse) CourseToUpdate.get(i);
				StringBuilder stringBuild =  new StringBuilder() ;
				stringBuild.append(course.getUserName().trim());
				stringBuild.append(SEPARATOR);
				stringBuild.append(course.getCourseID().trim());
				stringBuild.append(SEPARATOR);
				stringBuild.append(course.getIndexID());
				stringBuild.append(SEPARATOR);
				stringBuild.append(course.getRegisterStatus());
				
				courseList.add(stringBuild.toString()) ;
			}
			write("src/db/StudentCourse.txt", courseList);
	}
}
