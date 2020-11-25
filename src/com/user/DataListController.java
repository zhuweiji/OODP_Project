package com.user;

import java.io.IOException;
import java.util.ArrayList;

import com.course.*;
import com.course.Lesson;

public class DataListController {
	
public static ArrayList<Student> studentList = StudentData.studentList;
public static ArrayList<Course> courseList = CourseData.courseList;
public static ArrayList<Index> indexList = IndexData.indexList;
public static ArrayList<Lesson> lessonList = LessonData.lessonList;
public static ArrayList<StudentCourse> studentCourseList = StudentCourseData.studentCourseList;
public static ArrayList<UserAcc> accountList = AccountData.accountList;
	
	// Student
	public static ArrayList<Student> getStudents(){ return studentList; }
	
	// Course
	public static ArrayList<Course> getCourses(){ return courseList; }
	
	// Index
	public static ArrayList<Index> getIndex(){ return indexList; }
		
	// Lesson
	public static ArrayList<Lesson> getLessons(){ return lessonList; }
		
	// StudentCourse
	public static ArrayList<StudentCourse> getStudentCourses(){ return studentCourseList; }

	// Account
	public static ArrayList<UserAcc> getAccounts(){ return accountList; }
	
	// WriteObject
	public static void writeObject(Object newObj) throws IOException{
		if (newObj instanceof Student){
		    studentList.add((Student) newObj);
		    StudentData.saveStudents(studentList);
		}
		else if (newObj instanceof Course){
			courseList.add((Course) newObj);
			CourseData.saveCourses(courseList);
		}
		else if (newObj instanceof Index){
			indexList.add((Index) newObj);
			IndexData.saveIndexes(indexList);
		}
		else if (newObj instanceof Lesson){
			lessonList.add((Lesson) newObj);
			LessonData.saveLessons(lessonList);
		}
		else if (newObj instanceof StudentCourse){
			studentCourseList.add((StudentCourse) newObj);
			StudentCourseData.saveStudentCourses(studentCourseList);
		}
		else if (newObj instanceof UserAcc){
			accountList.add((UserAcc) newObj);
			AccountData.saveAccounts(accountList);
		}
	}
}
