package com.course;

import java.io.*;
import java.text.*;
import java.util.*;

import com.user.*;

/**
 * Manages the printing of the student list and staff list
 * @version 1.0
 * @since 2017-03-21
 */

public class PrintController {

	/**
	 * Print out all the student names on the Student List
	 * from index 0 to the end of the array
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static void printStudentList(int indexID) throws IOException, ParseException{
		ArrayList<Student> studentList = DataListController.getStudents();
		ArrayList<StudentCourse> studentCourseList = DataListController.getStudentCourses();
		
		System.out.println();
		System.out.println("Index ID: " + indexID);
		System.out.println("Username\t Matric ID\t Full Name");
		System.out.println("-----------------------------------------------------------------");
		for (StudentCourse sc : studentCourseList){
			if(sc.getIndexID() == indexID){
				for (Student s: studentList){
					if(sc.getUsername().equals(s.getUserName())){
						System.out.print(s.getUserName() + "\t\t ");
                        System.out.print(s.getMatricID() + "\t ");
                        System.out.print(s.getName());
						System.out.println();
					}
				}
			}
		}
	}
	
	public static void printStudentList(String courseID) throws IOException, ParseException{
		ArrayList<Student> studentList = DataListController.getStudents();
		ArrayList<StudentCourse> studentCourseList = DataListController.getStudentCourses();
		
		System.out.println();
		System.out.println("Course ID: " + courseID);
		System.out.println("Username\t Matric ID\t Full Name");
		System.out.println("-----------------------------------------------------------------");
		for (StudentCourse sc : studentCourseList){
			if(sc.getCourseID().equals(courseID)){
				for (Student s: studentList){
					if(sc.getUsername().equals(s.getUserName())){
						System.out.print(s.getUserName() + "\t\t ");
                        System.out.print(s.getMatricID() + "\t");
                        System.out.print(s.getName());
                        
						System.out.println();
					}
				}
			}
		}
	}
	
	public static void printStudentList() throws IOException, ParseException{
		ArrayList<Student> studentList = DataListController.getStudents();
		
		System.out.println();
		System.out.println("Matric ID\tFull Name");
		System.out.println("---------------------------------------------------");
		for (Student s: studentList){
			System.out.print(s.getMatricID() + "\t");
			System.out.print(s.getName());
			System.out.println();
		}
	}
	
	public static void printIndexInfo(int i) throws IOException, ParseException{
		ArrayList<Index> indexList = DataListController.getIndex();
		ArrayList<Lesson> lessonList = DataListController.getLessons();
		ArrayList<Course> courseList = DataListController.getCourses();
		
		for (Index in : indexList) {
			if (in.equals(i)) {
				System.out.println("Index ID: " + i);
				for (Course c : courseList) {
					if (c.getCourseID().equals(in.getCourseID())) {
						System.out.println("Course: " + in.getCourseID());
					
					}
				}
				
				System.out.println();
				System.out.println("Type\t Group\t\t Day\t\t Time\t\t Venue");
				System.out.println("-----------------------------------------------------------------");
				for (Lesson le : lessonList)
				{
					if(le.equals(i)){
						System.out.print(le.getLessonType() + "\t ");
						System.out.print(in.getTutorialGroup() + "\t\t ");
						System.out.print(le.getLessonDay() + "\t\t ");
						System.out.print(le.getLessonTime() + "\t ");
						System.out.print(le.getLessonVenue() + "\t ");
						System.out.println();
					}
				}
			}
		}
	}
	
	/**
	 * Print out all the courses on the Course List
	 * from index 0 to the end of the array
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static void printCourseList() throws IOException, ParseException{
		ArrayList<Course> courses = DataListController.getCourses();
		if(courses.size() <= 0){
			System.out.println("There are no course in the list.");
			return;
		}
		else{
			System.out.println("Course ID\t Course Name");
			System.out.println("----------------------------------------");
			for (Course course : courses)
			{
				System.out.print(course.getCourseID() + "\t\t ");
				System.out.print(course.getCourseName());
				System.out.println();
			}
		}
	}
	
	/**
	 * Print out all the indexes of a course
	 * from index 0 to the end of the array
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static void printIndexList(String courseID) throws IOException, ParseException{
		ArrayList<Index> indexList = DataListController.getIndex();
		if(indexList.size() <= 0){
			System.out.println("There is no index for this course.");
			return;
		}
		else{
			System.out.println();
			System.out.println("Course ID\t Index ID");
			System.out.println("----------------------------------------");
			for(Index index : indexList) {
				if (index.getCourseID().equals(courseID)){
					System.out.print(index.getCourseID() + "\t\t ");
					System.out.print(index.getIndexID());
					System.out.println();
				}
			}
		}
	}
	
	public static void printRegisteredCourses(Student s) throws IOException, ParseException{
		ArrayList<StudentCourse> studentCourseList = DataListController.getStudentCourses();
		ArrayList<Course> courseList = DataListController.getCourses();
		
		if(studentCourseList.size() <= 0){
			System.out.println("There is no registered course.");
			return;
		}
		else{
			int totalAURegistered = 0;
			System.out.println();
			System.out.println("Course ID\t AU\t Course Type\t Index ID\t Status");
			System.out.println("-------------------------------------------------------------------");
			for(StudentCourse sc  : studentCourseList) {
				if (sc.getUsername().equals(s.getUserName())){
					for(Course c : courseList){
						if (c.getCourseID().equals(sc.getCourseID())){
							System.out.print(sc.getCourseID() + "\t\t ");
							System.out.print(c.getAU() + "\t ");
							System.out.print(sc.getIndexID() + "\t\t ");
							System.out.print(sc.getRegisterStatus());
							System.out.println();
							
							if (sc.getRegisterStatus().equals("Registered")){
								totalAURegistered += c.getAU();
							}
						}
					}
				}
			}			
			System.out.println("Total AU Registered: " + totalAURegistered);
		}
	}
}
