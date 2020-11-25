package com;

import java.io.*;
import java.text.*;
import java.util.*;

import com.course.Index;
import com.course.StudentCourse;
import com.course.StudentCourseController;
import com.user.Student;
import com.user.StudentController;
import com.user.DataListController;
import com.course.*;


public class StudentInterface {
	
	private static Student logged_on_user;
	private static StudentController studentController = StudentController.getInstance();
	private final ConsoleUserInterface cmd = ConsoleUserInterface.getInstance();
	private static final StudentInterface instance = new StudentInterface();
	private static final LogInHandler loginhandler = LogInHandler.startHandler();
	private static Scanner sc = new Scanner(System.in);

	public static StudentInterface getInstance(String Userid,String hashed_pw,String salt,String id)
	{
		Student logged_on_user = studentController.getExistingStudent(Userid, hashed_pw, salt, id);
		if (logged_on_user != null){
			instance.logged_on_user = logged_on_user;
		}
		return instance;
	}

	private StudentInterface(){
		// should remain empty
	}

	public void run() {
		System.out.println("Welcome "+ logged_on_user.getName()+ " !");
		Student s = null;
		logged_on_user = s;
		int choice;

		StudentWhileLoop:
		while (true){
			System.out.println("***Welcome to Student panel!***");
			System.out.println("Please select an action:");
			System.out.println("(1) Register Course");
			System.out.println("(2) Drop Course");
			System.out.println("(3) Check/Print Courses Registered");
			System.out.println("(4) Check Vacancies Available");
			System.out.println("(5) Change Index ID of Course");
			System.out.println("(6) Swop Index ID with Another Student");
			System.out.println("(7) Select Notification Mode");
			System.out.println("(8) Logout");

			System.out.print("> ");
			try {
				choice = Integer.parseInt(sc.nextLine());
				switch (choice) {
				// Register Course
					case 1 -> registerCourseUI();
				// Drop Course
					case 2 -> dropCourseUI();
				// Check/Print Courses Registered
					case 3 -> PrintController.printRegisteredCourses(s);
				// Check Vacancies Available
					case 4 -> checkVacancyUI();
				// Change Index ID of Course
					case 5 -> changeIndexIDUI();
				// Swop Index ID with Another Student
					case 6 -> swopIndexIDUI();
				// Select Notification Mode
					case 7 -> selectNotiModeUI();
				// Logout
					case 8 -> {
						System.out.println("Successfully Logged Out!");
						System.out.println();
						break StudentWhileLoop;
					}
					default -> System.out.println("Invalid Input! Please re-enter!");
				}
			} catch (Exception e) {
				System.out.println("Invalid Input! Please re-enter!");
			}
			System.out.println();
		}
	}


	private static void registerCourseUI() throws ParseException, IOException{
		ArrayList<StudentCourse> studentCourseList = DataListController.getStudentCourses();
		ArrayList<Index> indexList = DataListController.getIndex();
		
		int indexID = 0;
		while(true){
			try{
				System.out.print("Enter the Index ID: "); indexID = sc.nextInt();
				sc.nextLine();
				break;
			} catch (Exception e){
				sc.nextLine();
				System.out.println("Invalid input! Index ID must be a ID!");
			}
		}
	   
		// To check if the index ID input by the user exists in the database or not
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
		
		// To check if the student has already registered to the course's index ID
		for(StudentCourse sc: studentCourseList){
			if(sc.getUserid().equals(logged_on_user.getUserid()) && sc.getIndexID() == indexID){
				System.out.println();
				System.out.println("You have already registered to the course's index ID!");
				return;
			}
		}

		PrintController.printIndexInfo(indexID);
		
		System.out.println();
		System.out.print("Confirm to Add Course? (Y/N): ");
		char choice = sc.nextLine().charAt(0);
		if (choice == 'Y' || choice == 'y'){
			StudentCourseController.registerCourse(logged_on_user, indexID);
		}
	}
	
	private static void dropCourseUI() throws ParseException, IOException{
		PrintController.printRegisteredCourses(logged_on_user);
		
		System.out.println();
		System.out.print("Enter the index ID to drop: "); int indexID = sc.nextInt();
		sc.nextLine();
		
		PrintController.printIndexInfo(indexID);
		
		System.out.println();
		System.out.print("Confirm to Drop Course? (Y/N): ");
		char choice = sc.nextLine().charAt(0);
		if (choice == 'Y' || choice == 'y'){
			StudentCourseController.removeCourse(logged_on_user, indexID);

			NotificationController.sendAlertWaitlist(indexID);
		}
	}
	
	private static void checkVacancyUI() throws IOException, ParseException{
		ArrayList<Index> indexList = DataListController.getIndex();
		
		int indexID = 0;
		while(true){
			try{
				System.out.print("Please enter the index ID to check: "); indexID = sc.nextInt();
				sc.nextLine();
				break;
			} catch (Exception e){
				sc.nextLine();
				System.out.println("Invalid input! Index ID must be a ID!");
			}
		}
	   
		// To check if the index ID input by the user exists in the database or not
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
		
		PrintController.printIndexInfo(indexID);
		
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
	
	private static void changeIndexIDUI() throws IOException, ParseException{
		System.out.print("\nEnter Current Index ID: "); int currentIndexID = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter New Index ID: "); int newIndexID = sc.nextInt();
		sc.nextLine();
		
		System.out.println();
		System.out.println("Current Index Information");
		System.out.println("=========================");
		PrintController.printIndexInfo(currentIndexID);
		
		System.out.println();
		System.out.println("New Index Information");
		System.out.println("=====================");
		PrintController.printIndexInfo(newIndexID);
		
		System.out.println();
		System.out.print("Confirm to Change Index ID? (Y/N): ");
		char choice = sc.nextLine().charAt(0);
		if (choice == 'Y' || choice == 'y'){
			StudentCourseController.removeCourse(logged_on_user, currentIndexID);
			StudentCourseController.registerCourse(logged_on_user, newIndexID);
			
			System.out.println("Index ID " + currentIndexID + " has been changed to " + newIndexID);
			
			NotificationController.sendAlertWaitlist(currentIndexID);
		}
	}
	
	private void swopIndexIDUI() throws IOException, ParseException{
		String peerUsername = cmd.input("Enter Peer's Username");
		char[] peerPasswordArr = cmd.secretInput("Enter Peer's Password: ");
		String peerPassword = new String(peerPasswordArr);
		String[] peerCredentials = loginhandler.login(peerUsername, peerPassword);

		ArrayList<Student> studList = DataListController.getStudents();
		if (peerCredentials != null) { // Successfully logged in
			Student peerAcc = studentController.getExistingStudent(peerCredentials[0],
																   peerCredentials[2],
																   peerCredentials[1],
																   peerCredentials[0]);
		for (Student peer : studList){
			if (peer.getUserName().equals(peerAcc.getUserName())){
					System.out.print("Enter Your Index ID: "); int yourIndexID = sc.nextInt();
					sc.nextLine();
					System.out.print("Enter Peer's Index ID: "); int peerIndexID = sc.nextInt();
					sc.nextLine();
					
					System.out.println();
					System.out.println("Student #1 (" + logged_on_user.getMatricID() + ")'s Index Information");
					System.out.println("================================================");
					PrintController.printIndexInfo(yourIndexID);
					
					System.out.println();
					System.out.println("Student #2 (" + peer.getMatricID() + ")'s Index Information");
					System.out.println("================================================");
					PrintController.printIndexInfo(peerIndexID);
					
					System.out.println();
					System.out.print("Confirm to Change Index ID? (Y/N): ");
					char choice = sc.nextLine().charAt(0);
					if (choice == 'Y' || choice == 'y'){
						StudentCourseController.removeCourse(logged_on_user, yourIndexID);
						StudentCourseController.registerCourse(logged_on_user, peerIndexID);

						StudentCourseController.removeCourse(peer, peerIndexID);
						StudentCourseController.registerCourse(peer, yourIndexID);
						
						System.out.println(logged_on_user.getMatricID() + "-Index ID " + yourIndexID + " has been successfully swopped with " + peer.getMatricID() + "-Index ID " + peerIndexID);
					}
				}
			}
		}else{
			System.out.println();
			System.out.println("Incorrect peer's username or password!");
		}
	}
	
	private static void selectNotiModeUI() throws IOException, ParseException{
		System.out.println("Please select your notification mode:");
		System.out.println("=====================================");
		System.out.println("(1) Send SMS");
		System.out.println("(2) Send Email");
		System.out.println("(3) Send both");
		int choice = sc.nextInt();
		sc.nextLine();
		String notiMode;
		switch (choice){
			case 1 -> notiMode = "SMS";
			case 2 -> notiMode = "Email";
			case 3 -> notiMode = "SMS/Email";
			default -> notiMode = "SMS/Email";
		}

		ArrayList<Student> studentList = DataListController.getStudents();
		System.out.println("Size: " + studentList.size());
		for(Student s : studentList){
			if (s.getUserName().equals(logged_on_user.getUserName())){
				// Updating
				studentList.remove(s); 
				Student newStud = new Student(s.getUserid(), s.getUserName(), s.getName() ,
						s.getMatricID(), s.getGender(), s.getNationality(), s.getEmail(), s.getCourse_of_study(),
						s.getPhone_number(),s.getDate_matriculated(), s.getAccessStart(), s.getAccessEnd(), notiMode);
				DataListController.writeObject(newStud);
				
				// necessary to prevent re-looping of updated textfile
				return;
			}
		}
	}
}