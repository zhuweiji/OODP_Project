package com.course;

import java.io.*;
import java.text.*;
import java.util.*;

import com.course.*;
import com.user.*;

public class StudentCourseController {
	/**
	 * Create a new course with the necessary information
	 * @throws ParseException 
	 * @throws IOException 
	 */	
	public static void registerCourse(Student s, int indexID) throws IOException, ParseException {
		ArrayList<Index> indexList = DataListController.getIndex();
		for (Index i : indexList){
			if (i.getIndexID() == indexID){
				int vacancy = i.getVacancy();
				int waitingList = i.getWaitingList();
				String registerStatus = "On Waiting List";
				String courseID = i.getCourseID();
				
				if (i.getVacancy() <= 0){
					waitingList++;
				}
				else if (i.getVacancy() > 0){
					vacancy--;
					registerStatus = "Registered";
				}
				
				// Adding
				StudentCourse newStudentCourse = new StudentCourse(s.getUserName(), courseID, indexID, registerStatus);
				DataListController.writeObject(newStudentCourse);
			    
				// Update new vacancy & waiting list
				indexList.remove(i); 
			    Index newIndex = new Index(i.getCourseID(), indexID, i.getTutorialGroup(), vacancy, waitingList);
			    DataListController.writeObject(newIndex);
				
				System.out.println();
				if (registerStatus.equals("On Waiting List")){
					System.out.println("Due to lack of vacancy, your Index " + indexID + " (" + courseID + ") will be put into waiting list.");
				}
				else if (registerStatus.equals("Registered")){
					System.out.println("Index " + indexID + " (" + courseID + ") has been successfully added!");
				}
				
				return;
			}
		}
			
	}
	
	public static void removeCourse(Student s, int indexID) throws IOException, ParseException{
		ArrayList<StudentCourse> studentCourseList = DataListController.getStudentCourses();
		ArrayList<Index> indexList = DataListController.getIndex();
		
		for(StudentCourse course : studentCourseList){
			if (course.getIndexID() == indexID && course.getUserName().equals(s.getUserName())){
				studentCourseList.remove(course);
				StudentCourseData.saveStudentCourses(studentCourseList);

				System.out.println("Index " + course.getIndexID() + " (" + course.getCourseID() + ") has been removed!");
				
				for (Index i : indexList){
					int vacancy = i.getVacancy();
					int waitingList = i.getWaitingList();
					
					if (course.getRegisterStatus().equals("Registered")){
						vacancy++;
					}
					else if (course.getRegisterStatus().equals("On Waiting List")){
						waitingList--;
					}
					
					if (i.getIndexID() == indexID){
						// Update new vacancy & waiting list
						indexList.remove(i); 
					    Index newIndex = new Index(i.getCourseID(), indexID, i.getTutorialGroup(), vacancy, waitingList);
					    DataListController.writeObject(newIndex);
						
						return;
					}
				}
			}
		}
	}
}