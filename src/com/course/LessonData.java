package com.course;
import com.lesson.Lesson;
import uml.Lesson;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class LessonData {
	
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
	
	public static ArrayList <Lesson> lessonList = new ArrayList<Lesson>() ;
	
    /** Initialise the courses before application starts
     * @throws IOException
     * @throws ParseException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
public static ArrayList<Lesson> initLessons() throws IOException, ParseException {
		// read String from text file
		ArrayList<String> stringArray = (ArrayList) read("src/db/lesson.txt");
		
		if (stringArray.size() == 0){
			return new ArrayList<Lesson>();
		}
        for (int i = 0 ; i < stringArray.size() ; i++) {
				String field = (String) stringArray.get(i);
				
				// get individual 'fields' of the string separated by SEPARATOR
				// pass in the string to the string tokenizer using delimiter "," 
				StringTokenizer tokenizer = new StringTokenizer(field, SEPARATOR);	
				
				//first to fifth tokens
				int  indexNumber = Integer.parseInt(tokenizer.nextToken().trim());	
				String lessonType = tokenizer.nextToken().trim();	
				String lessonDay = tokenizer.nextToken().trim();	
				String lessonTime = tokenizer.nextToken().trim();	
				String lessonVenue = tokenizer.nextToken().trim();	
				
				// create Lesson object from file data
				Lesson lesson = new Lesson(indexNumber, lessonType, lessonDay, lessonTime, lessonVenue);
				// add to Lesson list 
				lessonList.add(lesson) ;
		}
		return lessonList;
	}

	/** Initialise the lessons before application starts
     * @param filename
     * @throws IOException
     * @throws ParseException 
     */
	/** Save the courses that has been added during the session
	 * @param CourseToUpdate
	 * @throws IOException
	 */
	public static void saveLessons(ArrayList<Lesson> LessonToUpdate) throws IOException {
		ArrayList <String> cl = new ArrayList<String>() ;// to store Courses data

        for (int i = 0 ; i < LessonToUpdate.size() ; i++) {
				Lesson lesson = (Lesson) LessonToUpdate.get(i);
				StringBuilder stringBuild =  new StringBuilder() ;
				stringBuild.append(lesson.getIndexNumber());
				stringBuild.append(SEPARATOR);
				stringBuild.append(lesson.getLessonType());
				stringBuild.append(SEPARATOR);
				stringBuild.append(lesson.getLessonDay());
				stringBuild.append(SEPARATOR);
				stringBuild.append(lesson.getLessonTime());
				stringBuild.append(SEPARATOR);
				stringBuild.append(lesson.getLessonVenue());

				cl.add(stringBuild.toString()) ;
			}
			write("src/db/lessons.txt",cl);
	}
}
