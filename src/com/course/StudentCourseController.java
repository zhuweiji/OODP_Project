package com.course;

import java.text.*;
import java.io.IOException;
import java.util.ArrayList;

import com.lesson.Lesson;
import com.user.Student;


public class StudentCourseController{
	
	/**
	 * Create a new course with the necessary information
	 * @throws ParseException 
	 * @throws IOException 
	 */	
	public static ArrayList<Course> courseList = CourseData.courseList;
	public static ArrayList<Index> indexList = IndexData.indexList;
	public static ArrayList<StudentCourse> studentCourseList = StudentCourseData.studentCourseList;
	public static ArrayList<Lesson> lessonList = LessonData.lessonList;
	public static ArrayList<Student> studentList = StudentData.studentList;
	
	//Student
	public static ArrayList<Student> getStudents(){ return studentList; }
	
	// Lesson
	public static ArrayList<Lesson> getLesson(){ return lessonList; }
	
	// Course
	public static ArrayList<Course> getCourse(){ return courseList; }
		
	// Index
	public static ArrayList<Index> getIndex(){ return indexList; }
	
	//StudentCourse
	public static ArrayList<StudentCourse> getStudentCourses(){ return studentCourseList; }
	
	public static void writeObject(Object newObj) throws IOException{
		if (newObj instanceof Course){
			courseList.add((Course) newObj);
			CourseData.saveCourses(courseList);
		}
		else if (newObj instanceof Index){
			indexList.add((Index) newObj);
			IndexData.saveIndexes(indexList);
		}
		else if (newObj instanceof StudentCourse){
			studentCourseList.add((StudentCourse) newObj);
			StudentCourseData.saveStudentCourses(studentCourseList);
		}
	}
		
	public static void registerCourse(Student s, int indexID) throws IOException, ParseException {
		ArrayList<Index> indexList = getIndex();
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
				StudentCourse newStudentCourse = new StudentCourse(s.getUserid(), s.getUsername(), courseID, indexID, registerStatus);
			    writeObject(newStudentCourse);
			    
				// Update new vacancy & waiting list
				indexList.remove(i); 
				Index newIndex = new Index(indexID,i.getCourseID(), i.getTutorialGroup(), vacancy, waitingList);
			    writeObject(newIndex);
				
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
		ArrayList<StudentCourse> studentCourseList = getStudentCourses();
		ArrayList<Index> indexList = getIndex();
		
		for(StudentCourse course : studentCourseList){
			if (course.getIndexID() == indexID && course.getUserid().equals(s.getUserid())){
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
					    Index newIndex = new Index(indexID,i.getCourseID(), i.getTutorialGroup(), vacancy, waitingList);
					    writeObject(newIndex);
						
						return;
					}
				}
			}
		}
	}
}