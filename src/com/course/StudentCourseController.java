package com.course;

import java.io.*;
import java.text.*;
import java.util.*;
import java.io.IOException;
import java.util.ArrayList;

import db.CourseData;
import db.IndexData;
import db.StudentCourseData;
import db.LessonData;
import model.StudentCourse;
import uml.Student;

public class StudentCourseController{
	
	private static Scanner sc = new Scanner(System.in);
	private static Student loggedInStudent;
	loggedInStudent = s;
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
	
	public static void printIndexInfo(int i) throws IOException, ParseException{
		ArrayList<Index> indexList = getIndex();
		ArrayList<Lesson> lessonList = getLesson();
		ArrayList<Course> courseList = getCourse();
		
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
	
	public static void printRegisteredCourses(Student s) throws IOException, ParseException{
		ArrayList<StudentCourse> studentCourseList = getStudentCourses();
		ArrayList<Course> courseList = getCourse();
		
		if(studentCourseList.size() <= 0){
			System.out.println("There is no registered course.");
			return;
		}
		else{
			int totalAURegistered = 0;
			System.out.println();
			System.out.println("Course Code\t AU\t Course Type\t Index Number\t Status");
			System.out.println("-------------------------------------------------------------------");
			for(StudentCourse sc  : studentCourseList) {
				if (sc.getUserName().equals(s.getUserName())){
					for(Course c : courseList){
						if (c.getCourseID().equals(sc.getCourseID())){
							System.out.print(sc.getCourseID() + "\t\t ");
							System.out.print(c.getAcadUnits() + "\t ");
							System.out.print(sc.getIndexID() + "\t\t ");
							System.out.print(sc.getRegisterStatus());
							System.out.println();
							
							if (sc.getRegisterStatus().equals("Registered")){
								totalAURegistered += c.getAcadUnits();
							}
						}
					}
				}
			}			
			System.out.println("Total AU Registered: " + totalAURegistered);
		}
	}
	
	
	
	
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
				StudentCourse newStudentCourse = new StudentCourse(s.getUserName(), courseID, indexID, registerStatus);
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
					    Index newIndex = new Index(indexID,i.getCourseID(), i.getTutorialGroup(), vacancy, waitingList);
					    writeObject(newIndex);
						
						return;
					}
				}
			}
		}
	}
	
	private static void registerCourseUI() throws ParseException, IOException{
    	ArrayList<StudentCourse> studentCourseList = getStudentCourses();
    	ArrayList<Index> indexList = getIndex();
    	
    	int indexID = 0;
        while(true){
        	try{
        		System.out.print("Enter the Index ID: "); indexID = sc.nextInt();
        		sc.nextLine();
        		break;
        	} catch (Exception e){
        		sc.nextLine();
        		System.out.println("Invalid input! Index ID must be a number!");
        	}
        }
       
        // To check if the index number input by the user exists in the database or not
        boolean foundIndexID = false;
        for(Index i: indexList){
			if(i.getIndexID() == indexID){
				foundIndexID = true;
			}
		}
        if(!foundIndexID){
        	System.out.println();
        	System.out.println("Index ID you entered is not found!");
        	return;
        }
        
		// To check if the student has already registered to the course's index number
			for(StudentCourse sc: studentCourseList){
				if(sc.getUserName().equals(loggedInStudent.getUserName()) && sc.getIndexID() == indexID){
					System.out.println();
					System.out.println("You have already registered to the course's index number!");
					return;
			}
		}


			printIndexInfo(indexID);
			System.out.println();
			System.out.print("Confirm to Add Course? (Y/N): ");
			char choice = sc.nextLine().charAt(0);
			if (choice == 'Y' || choice == 'y'){
				registerCourse(loggedInStudent, indexID);
		}
    }
    
    private static void dropCourseUI() throws ParseException, IOException{
    	printRegisteredCourses(loggedInStudent);
    	
    	System.out.println();
    	System.out.print("Enter the index number to drop: "); int indexID = sc.nextInt();
    	sc.nextLine();
    	
    	printIndexInfo(indexID);
		
		System.out.println();
		System.out.print("Confirm to Drop Course? (Y/N): ");
		char choice = sc.nextLine().charAt(0);
		if (choice == 'Y' || choice == 'y'){
			removeCourse(loggedInStudent, indexID);

			//NotificationMgr.sendAlertWaitlist(indexID);
		}
    }
    
    private static void checkVacancyUI() throws IOException, ParseException{
		ArrayList<Index> indexList = getIndex();
		
		int indexID = 0;
        while(true){
        	try{
        		System.out.print("Please enter the index ID to check: "); indexID = sc.nextInt();
        		sc.nextLine();
        		break;
        	} catch (Exception e){
        		sc.nextLine();
        		System.out.println("Invalid input! Index ID must be a number!");
        	}
        }
       
        // To check if the index number input by the user exists in the database or not
        boolean foundIndexID = false;
        for(Index i: indexList){
			if(i.getIndexID() == indexID){
				foundIndexID = true;
			}
		}
        if(!foundIndexID){
        	System.out.println();
        	System.out.println("Index ID you entered is not found!");
        	return;
        }
		
		printIndexInfo(indexID);
		
		for(Index index : indexList){
			if (index.getIndexID() == indexID){
				
				System.out.println();
				System.out.print("Vacancy: " + index.getVacancy()); 
				System.out.print("\t\tWaiting List: " + index.getWaitingList());
				System.out.println();
				
				return;
			}
		}
	}
    
    private static void changeIndexNumberUI() throws IOException, ParseException{
		System.out.print("\nEnter Current Index ID: "); int currentIndexID = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter New Index ID: "); int newIndexID = sc.nextInt();
		sc.nextLine();
		
		System.out.println();
		System.out.println("Current Index Information");
		System.out.println("=========================");
		printIndexInfo(currentIndexID);
		
		System.out.println();
		System.out.println("New Index Information");
		System.out.println("=====================");
		printIndexInfo(newIndexID);
		
		System.out.println();
		System.out.print("Confirm to Change Index Number? (Y/N): ");
		char choice = sc.nextLine().charAt(0);
		if (choice == 'Y' || choice == 'y'){
			removeCourse(loggedInStudent, currentIndexID);
			registerCourse(loggedInStudent, newIndexID);
			
			System.out.println("Index ID " + currentIndexID + " has been changed to " + newIndexID);
			
			//NotificationMgr.sendAlertWaitlist(currentIndexID);
		}
	}
    
    /**private static void swopIndexNumberUI() throws IOException, ParseException{
    	System.out.print("\nEnter Peer's Username: "); String peerUsername = sc.nextLine();
    	System.out.print("Enter Peer's Password: "); String peerPassword = sc.nextLine();
    	
    	Account peerAcc = UserValidationMgr.compareUserPass(peerUsername, peerPassword, "Student");
    	ArrayList<Student> studList = DataListMgr.getStudents();
		if (!(peerAcc == null)) { // Successfully logged in
    	for (Student peer : studList){
    		if (peer.getUserName().equals(peerAcc.getUsername())){
    				System.out.print("Enter Your Index Number: "); int yourIndexNumber = sc.nextInt();
    				sc.nextLine();
    				System.out.print("Enter Peer's Index Number: "); int peerIndexNumber = sc.nextInt();
    				sc.nextLine();
    				
    				System.out.println();
    				System.out.println("Student #1 (" + loggedInStudent.getMatricNumber() + ")'s Index Information");
    				System.out.println("================================================");
    				PrintMgr.printIndexInfo(yourIndexNumber);
    				
    				System.out.println();
    				System.out.println("Student #2 (" + peer.getMatricNumber() + ")'s Index Information");
    				System.out.println("================================================");
    				PrintMgr.printIndexInfo(peerIndexNumber);
    				
    				System.out.println();
    				System.out.print("Confirm to Change Index Number? (Y/N): ");
    				char choice = sc.nextLine().charAt(0);
    				if (choice == 'Y' || choice == 'y'){
    					StudentCourseMgr.removeCourse(loggedInStudent, yourIndexNumber);
    					StudentCourseMgr.registerCourse(loggedInStudent, peerIndexNumber);

    					StudentCourseMgr.removeCourse(peer, peerIndexNumber);
    					StudentCourseMgr.registerCourse(peer, yourIndexNumber);
    					
    					System.out.println(loggedInStudent.getMatricNumber() + "-Index Number " + yourIndexNumber + " has been successfully swopped with " + peer.getMatricNumber() + "-Index Number " + peerIndexNumber);
    				}
    			}
    		}
		}else{
			System.out.println();
			System.out.println("Incorrect peer's username or password!");
		}
    }*/
    
    /** private static void selectNotiModeUI() throws IOException, ParseException{
    	System.out.println("Please select your notification mode:");
    	System.out.println("=====================================");
    	System.out.println("(1) Send SMS");
    	System.out.println("(2) Send Email");
    	System.out.println("(3) Send both");
    	int choice = sc.nextInt();
    	sc.nextLine();
    	
		ArrayList<Student> studentList = getStudent();
		System.out.println("Size: " + studentList.size());
    	for(Student s : studentList){
    		if (s.getUserName().equals(loggedInStudent.getUserName())){
    			// Updating
			    studentList.remove(s); 
			    Student newStud = new Student(s.getUserName(), s.getFirstName(), s.getLastName(), 
			    		s.getMatricNumber(), s.getGender(), s.getNationality(), s.getMobileNo(), 
			    		s.getEmail(), s.getAccessStart(), s.getAccessEnd(), choice);
			    DataListMgr.writeObject(newStud);
			    
			    // necessary to prevent re-looping of updated textfile
			    return;
    		}
    	}
    }**/
}