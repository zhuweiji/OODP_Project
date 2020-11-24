package com.course;


import java.io.*;
import java.text.*;
import java.util.*;

import com.user.IO;
import com.lesson.Lesson;

public class LessonData {
	public static final String SEPARATOR = "|";
	
	public static ArrayList <Lesson> lessonList = new ArrayList<Lesson>() ;
	
    /** Initialise the courses before application starts
     * @param filename
     * @throws IOException
     * @throws ParseException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
public static ArrayList<Lesson> initLessons() throws IOException, ParseException {
		// read String from text file
		ArrayList<String> stringArray = (ArrayList) IO.read("data/lessons.txt");
		
		if (stringArray.size() == 0){
			return new ArrayList<Lesson>();
		}
        for (int i = 0 ; i < stringArray.size() ; i++) {
				String field = (String) stringArray.get(i);
				
				// get individual 'fields' of the string separated by SEPARATOR
				// pass in the string to the string tokenizer using delimiter "," 
				StringTokenizer tokenizer = new StringTokenizer(field, SEPARATOR);	
				
				//first to fifth tokens
				int  indexID = Integer.parseInt(tokenizer.nextToken().trim());	
				String lessonType = tokenizer.nextToken().trim();	
				String lessonDay = tokenizer.nextToken().trim();	
				String lessonTime = tokenizer.nextToken().trim();	
				String lessonVenue = tokenizer.nextToken().trim();	
				
				// create Lesson object from file data
				Lesson lesson = new Lesson(indexID, lessonType, lessonDay, lessonTime, lessonVenue);
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
				stringBuild.append(lesson.getIndexID());
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
			IO.write("data/lessons.txt",cl);
	}
}