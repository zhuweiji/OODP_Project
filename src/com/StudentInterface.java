package com;

import java.io.*;
import java.text.*;
import java.util.*;

import com.course.Course;
import com.course.CourseData;
import com.course.Index;
import com.course.IndexData;
import com.course.LessonData;
import com.course.StudentCourse;
import com.course.StudentCourseController;
import com.course.StudentCourseData;
import com.course.StudentData;
import com.lesson.Lesson;
import com.user.Student;
import com.user.StudentController;
import com.user.UserAcc;
import com.user.LogInHandler;

public class StudentInterface {
	private static Student logged_on_user;
	
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
	
    private static StudentController studentController = StudentController.getInstance();
    private final ConsoleUserInterface cmd = ConsoleUserInterface.getInstance();
    private static final StudentInterface instance = new StudentInterface();
    private static Scanner sc = new Scanner(System.in);
    
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
        cmd.display("\n\n-------------------------------");
        cmd.display("Welcome "+ logged_on_user.getName()+ " !");
    }
        
    public static void showStudentOption(Student s){

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
    			System.out.println("(5) Change Index Number of Course");
    			System.out.println("(6) Swop Index Number with Another Student");
    			System.out.println("(7) Select Notification Mode");
    			System.out.println("(8) Logout");

    			System.out.print("> ");
    			try {
    				choice = Integer.parseInt(sc.nextLine());
    				switch (choice) {
    				case 1: // Register Course
    					registerCourseUI();
    					break;
    				case 2: // Drop Course
    					dropCourseUI();
    					break;
    				case 3: // Check/Print Courses Registered
    					printRegisteredCourses(s);
    					break;
    				case 4: // Check Vacancies Available
    					checkVacancyUI();
    					break;
    				case 5: // Change Index Number of Course
    					changeIndexIDUI();
    					break;
    				case 6: // Swop Index Number with Another Student
    					swopIndexNumberUI();
    					break;
    				case 7: // Select Notification Mode
    					selectNotiModeUI();
    					break;
    				case 8: // Logout
    					System.out.println("Successfully Logged Out!");
    					System.out.println();
    					break StudentWhileLoop;
    				default:
    					System.out.println("Invalid Input! Please re-enter!");
    				}
    			} catch (Exception e) {
    				System.out.println("Invalid Input! Please re-enter!");
    			}
    			System.out.println();
    		}
    	}
    
    
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
				if (sc.getUserid().equals(s.getUserid())){
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
	
    private static void registerCourseUI() throws ParseException, IOException{
    	ArrayList<StudentCourse> studentCourseList = getStudentCourses();
    	ArrayList<Index> indexList = getIndex();
    	
    	int indexID = 0;
        while(true){
        	try{
        		System.out.print("Enter the Index Number: "); indexID = sc.nextInt();
        		sc.nextLine();
        		break;
        	} catch (Exception e){
        		sc.nextLine();
        		System.out.println("Invalid input! Index Number must be a number!");
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
        	System.out.println("Index Number you entered is not found!");
        	return;
        }
        
		// To check if the student has already registered to the course's index number
		for(StudentCourse sc: studentCourseList){
			if(sc.getUserid().equals(logged_on_user.getUserid()) && sc.getIndexID() == indexID){
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
			StudentCourseController.registerCourse(logged_on_user, indexID);
		}
    }
    
    private static void dropCourseUI() throws ParseException, IOException{
    	printRegisteredCourses(logged_on_user);
    	
    	System.out.println();
    	System.out.print("Enter the index number to drop: "); int IndexID = sc.nextInt();
    	sc.nextLine();
    	
    	printIndexInfo(IndexID);
		
		System.out.println();
		System.out.print("Confirm to Drop Course? (Y/N): ");
		char choice = sc.nextLine().charAt(0);
		if (choice == 'Y' || choice == 'y'){
			StudentCourseController.removeCourse(logged_on_user, IndexID);

			NotificationController.sendAlertWaitlist(IndexID);
		}
    }
    
    private static void checkVacancyUI() throws IOException, ParseException{
		ArrayList<Index> indexList = getIndex();
		
		int IndexID = 0;
        while(true){
        	try{
        		System.out.print("Please enter the index number to check: "); IndexID = sc.nextInt();
        		sc.nextLine();
        		break;
        	} catch (Exception e){
        		sc.nextLine();
        		System.out.println("Invalid input! Index Number must be a number!");
        	}
        }
       
        // To check if the index number input by the user exists in the database or not
        boolean foundIndexID = false;
        for(Index i: indexList){
			if(i.getIndexID() == IndexID){
				foundIndexID = true;
			}
		}
        if(!foundIndexID){
        	System.out.println();
        	System.out.println("Index Number you entered is not found!");
        	return;
        }
		
		printIndexInfo(IndexID);
		
		for(Index index : indexList){
			if (index.getIndexID() == IndexID){
				
				System.out.println();
				System.out.print("Vacancy: " + index.getVacancy()); 
				System.out.print("\t\tWaiting List: " + index.getWaitingList());
				System.out.println();
				
				return;
			}
		}
	}
    
    private static void changeIndexIDUI() throws IOException, ParseException{
		System.out.print("\nEnter Current Index Number: "); int currentIndexID = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter New Index Number: "); int newIndexID = sc.nextInt();
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
			StudentCourseController.removeCourse(logged_on_user, currentIndexID);
			StudentCourseController.registerCourse(logged_on_user, newIndexID);
			
			System.out.println("Index Number " + currentIndexID + " has been changed to " + newIndexID);
			
			NotificationController.sendAlertWaitlist(currentIndexID);
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
    	
		ArrayList<Student> studentList = getStudents();
		System.out.println("Size: " + studentList.size());
    	for(Student s : studentList){
    		if (s.getUserid().equals(logged_on_user.getUserid())){
    			// Updating
			    studentList.remove(s); 
			    Student newStud = new Student(s.getUserid(), s.getName(), 
			    		s.getMatricID(), s.getGender(), s.getNationality(), s.getPhone_number(), 
			    		s.getEmail(), s.getAccessStart(), s.getAccessEnd(), choice);
			    writeObject(newStud);
			    
			    // necessary to prevent re-looping of updated textfile
			    return;
    		}
    	}
    }
    
    private static void swopIndexNumberUI() throws IOException, ParseException{
    	System.out.print("\nEnter Peer's Username: "); String peerUsername = sc.nextLine();
    	System.out.print("Enter Peer's Password: "); String peerPassword = sc.nextLine();
    	
    	UserAcc peerAcc = LogInHandler.compareUserPass(peerUsername, peerPassword);
    	ArrayList<Student> studList = getStudents();
		if (!(peerAcc == null)) { // Successfully logged in
    	for (Student peer : studList){
    		if (peer.getUsername().equals(peerAcc.getUsername())){
    				System.out.print("Enter Your Index Number: "); int yourIndexID = sc.nextInt();
    				sc.nextLine();
    				System.out.print("Enter Peer's Index Number: "); int peerIndexID = sc.nextInt();
    				sc.nextLine();
    				
    				System.out.println();
    				System.out.println("Student #1 (" + logged_on_user.getMatricID() + ")'s Index Information");
    				System.out.println("================================================");
    				printIndexInfo(yourIndexID);
    				
    				System.out.println();
    				System.out.println("Student #2 (" + peer.getMatricID() + ")'s Index Information");
    				System.out.println("================================================");
    		        printIndexInfo(peerIndexID);
    				
    				System.out.println();
    				System.out.print("Confirm to Change Index Number? (Y/N): ");
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

}

