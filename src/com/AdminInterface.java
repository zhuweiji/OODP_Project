package com;

import com.course.*;
import com.user.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class AdminInterface {
    /**
     * UI layer that allows admins to perform
     */
    private Admin logged_on_user;
    private Calendar defaultAccessStart;
    private Calendar defaultAccessEnd;
    private boolean defaultAccessAvail = true;
    {
        try {
            defaultAccessStart = CalendarController.stringToCalendar("25/11/2020 00:00");
            defaultAccessEnd = CalendarController.stringToCalendar("31/11/2020 23:59");
        } catch (ParseException e) {
            defaultAccessAvail = false;
            e.printStackTrace();
        }
    }

    private static final UserController userController = UserController.getInstance();
    private static final StudentController studentController = StudentController.getInstance();
    private static final AdminController adminController = AdminController.getInstance();
    private final ConsoleUserInterface cmd = ConsoleUserInterface.getInstance();
    private static final AdminInterface instance = new AdminInterface();
    private static final CourseIndexController courseIndexController = CourseIndexController.getInstance();

    public static AdminInterface getInstance(String username,String hashed_pw,String salt,String id)
    {
        Admin logged_on_user = adminController.getExistingAdmin(username, hashed_pw, salt, id);
        if (logged_on_user != null){
            instance.logged_on_user = logged_on_user;
        }
        return instance;
    }

    private AdminInterface(){
        // should remain empty
    }

    public void run(){
        cmd.display("\n\n-------------------------------");
        cmd.display("Welcome "+ logged_on_user.getName()+ " !");
        while (true){
            cmd.display("Here are your available options: ");
            cmd.display("0: Edit Student's access period"); //todo implement change all student's access period
            cmd.display("1: Create new Admin user");
            cmd.display("2: Create new Student user");
            cmd.display("3: Update a course");
            cmd.display("4: Add a new course");
            cmd.display("5: Update an index");
            cmd.display("6: Add a new index");
            cmd.display("7: Update index vacancy");
            cmd.display("8: Check available vacancy for an index number");
            cmd.display("9: Print student list by index number");
            cmd.display("10: Print student list by course");
            cmd.display("-1: Exit");
            int choice = Integer.parseInt(cmd.input("Enter your choice: "));
            switch (choice){
                case 0 -> editAccessPeriod();
                case 1 -> CreateAdmin();
                case 2 -> CreateStudent();
                case 3 -> UpdateCourse();
                case 4 -> CreateCourse();
                case 5 -> UpdateIndex(false);
                case 6 -> CreateIndex();
                case 7 -> UpdateIndex(true);
                case 8 -> CheckVacancy();
                case 9 -> DisplayStudentsByIndex();
                case 10 -> DisplayStudentsByCourse();
                case -1 -> System.exit(0);
            }
        }

    }

    private Course ReadCourseHelper(String courseID, Scanner sc)
    {
        System.out.print("Enter the course's name: ");
        String courseName = sc.nextLine();

        System.out.print("Enter the School which offers the course (E.g. SCSE): ");
        String school = sc.nextLine();

        int totalCapacity = 0;
        while (true) {
            try {
                System.out.print("Enter the total capacity for this course: ");
                totalCapacity = sc.nextInt();
                sc.nextLine();
                break;
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Invalid input. Total Capacity must be a number.");
            }
        }

        int acadUnits = 0;
        while (true) {
            try {
                System.out.print("Enter the number of AUs: ");
                acadUnits = sc.nextInt();
                sc.nextLine();
                break;
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Invalid input. Academic Unit must be a number.");
            }
        }

        return new Course(courseName, school, courseID, totalCapacity, acadUnits);
    }

    public Admin CreateAdmin(){
        int num_tries = 3;
        String try_again_message = "Please try again.";
        String failure_message = "You have made " + num_tries + " wrong inputs, cancelling operation.";

        String username = cmd.input("Enter new admin's username: ");
        String password;
        if (cmd.consoleAvail()){
            char[] password_char = cmd.secretInput("Enter new admin's password: ");
            password = new String(password_char);
        }
        else{
            password = cmd.input("Enter new admin's password: ");
        }

        UserAcc.acc_info acc_info = new UserAcc.acc_info(username, password);
        userController.getNewUserAcc(acc_info, "admin");
        Admin newAdmin = adminController.getNewAdmin(acc_info);

        int location = 0;
        boolean complete = false;
        while (!complete) {
            if (num_tries <= 0){
                cmd.display(failure_message);
                complete = true;
                break;
            }
            try {
                switch (location){
                    case 0:
                        String name = cmd.input("Enter admin name: ");
                        newAdmin.setName(name);
                        location++;
                    case 1:
                        String email = cmd.input("Enter admin e-mail: ");
                        newAdmin.setEmail(email);
                        location++;

                        cmd.display("Admin created with following details:");
                        System.out.println("Name: " + newAdmin.getName() + ", E-mail: " + newAdmin.getEmail() );
                        adminController.setUser(newAdmin);
                        adminController.saveAdminToDB(newAdmin);
                        System.out.println("saved!");
                        adminController.refreshAdminDB();
                        System.out.println("infodb refreshed");
                        adminController.readUserCredDB();
                        System.out.println("creddb refreshed");
                        complete = true;
                        break;
                }

            }
            catch (IllegalArgumentException e){
                num_tries --;
                cmd.display(e.getMessage());
                cmd.display(try_again_message);
            }
        }
        return newAdmin;
    }

    public void CheckVacancy() {
        ArrayList<Course> courseList = courseIndexController.getCourseinfoDB();
        Scanner sc = new Scanner(System.in);
        String courseID = "";
        while (true)
        {
            boolean found = false;
            System.out.print("Enter course ID: ");
            courseID = sc.nextLine().toUpperCase();
            for (Course c : courseList) {
                if (c.getCourseID().equals(courseID)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                System.out.println("Course ID does not exists. Please enter again.");
            else
                break;
        }
        IndexData indexData = new IndexData();
        ArrayList<Index> allIndexes = new ArrayList<>();
        try {
            allIndexes = IndexData.initIndex();
        } catch (Exception e) {
            System.out.println("Display students by index encountered exception - " + e.getMessage());
            return;
        }

        ArrayList<Index> indexByCourse = new ArrayList<Index>();
        for (Index index : allIndexes)
            if (index.getCourseID().equals(courseID)) {
                indexByCourse.add(index);
                System.out.println(index.getIndexID());
            }
        int indexid = 0;
        while (true)
        {
            boolean found = false;
            System.out.print("Enter index ID: ");
            indexid = sc.nextInt();
            for (Index i : indexByCourse) {
                if (i.getIndexID() == indexid) {
                    found = true;
                    break;
                }
            }
            if (!found)
                System.out.println("Index ID does not exists. Please enter again.");
            else
                break;
        }
        for (Index index : indexByCourse)
            if (index.getIndexID() == indexid)
                System.out.println(index.getCourseID() + " " + index.getIndexID() + " " + index.getVacancy() + " " + index.getWaitingList());
    }

    public void UpdateCourse() {
        ArrayList<Course> courseList = courseIndexController.getCourseinfoDB();
        Scanner sc = new Scanner(System.in);
        String courseCode = "";
        while (true)
        {
            boolean found = false;
            System.out.print("Enter course ID: ");
            courseCode = sc.nextLine().toUpperCase();
            for (Course c : courseList) {
                if (c.getCourseID().equals(courseCode)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                System.out.println("Course ID does not exists. Please enter again.");
            else
                break;
        }

        System.out.println("Please enter course details...");
        Course existingCourse = ReadCourseHelper(courseCode, sc);
        int result = courseIndexController.updateCourse(existingCourse, courseCode);
        if (result == 0) {
            System.out.println("Successfully updated course");
            System.out.println(existingCourse.toString());
        }
        else {
            System.out.println("Encountered error while updating course");
        }
    }

    public void UpdateIndex(boolean updateVacancy) {
        IndexData indexData = new IndexData();

        ArrayList<Course> courseList = courseIndexController.getCourseinfoDB();
        ArrayList<Index> indexList = courseIndexController.getIndexinfoDB();
        Scanner sc = new Scanner(System.in);
        String courseID = "";

        ArrayList<Index> allIndexes = new ArrayList<>();
        try {
            allIndexes = IndexData.initIndex();
        } catch (Exception e) {
            System.out.println("Display students by index encountered exception - " + e.getMessage());
            return;
        }

        while (true)
        {
            boolean found = false;
            System.out.print("Enter course ID: ");
            courseID = sc.nextLine().toUpperCase();
            for (Course c : courseList) {
                if (c.getCourseID().equals(courseID)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                System.out.println("Course ID does not exists. Please enter again.");
            else
                break;
        }
        ArrayList<Index> indexByCourse = new ArrayList<Index>();
        for (Index index : allIndexes)
            if (index.getCourseID().equals(courseID)) {
                indexByCourse.add(index);
                System.out.println(index.getIndexID());
            }
        int indexid = 0;
        while (true)
        {
            boolean found = false;
            System.out.print("Enter index ID to modify: ");
            indexid = sc.nextInt();
            for (Index i : indexByCourse) {
                if (i.getIndexID() == indexid) {
                    found = true;
                    break;
                }
            }
            if (!found)
                System.out.println("Index ID does not exists. Please enter again.");
            else
                break;
        }
        if (updateVacancy)
        {
            System.out.print("Please enter new vacancy: ");
            int newVacancy = sc.nextInt();
            for (Index index : indexByCourse) {
                if (indexid == index.getIndexID()) {
                    index.setVacancy(newVacancy);
                    courseIndexController.updateIndex(index, indexid);
                }
            }
        }
        else
        {
            System.out.print("Please enter new index ID: ");
            int newIndexid = sc.nextInt();
            for (Index index : indexByCourse) {
                if (newIndexid == index.getIndexID()) {
                    index.setIndexID(newIndexid);
                    courseIndexController.updateIndex(index, indexid);
                }
            }
        }

    }

    public void DisplayStudentsByIndex() {
        StudentCourseData studentData = new StudentCourseData();
        StudentData studentsData = new StudentData();
        IndexData indexData = new IndexData();
        ArrayList<StudentCourse> allStudents = new ArrayList<>();
        try {
            allStudents = StudentCourseData.initStudentCourses();
        } catch (Exception e) {
            System.out.println("Display students by course encountered exception - " + e.getMessage());
            return;
        }
        ArrayList<Student> students = new ArrayList<>();
        try {
            students = StudentData.initStudents();
        } catch (Exception e) {
            System.out.println("Display students by course encountered exception - " + e.getMessage());
            return;
        }
        ArrayList<Index> allIndexes = new ArrayList<>();
        try {
            allIndexes = IndexData.initIndex();
        } catch (Exception e) {
            System.out.println("Display students by index encountered exception - " + e.getMessage());
            return;
        }

        ArrayList<Course> courseList = courseIndexController.getCourseinfoDB();
        String courseID = "";
        Scanner sc = new Scanner(System.in);
        while (true)
        {
            boolean found = false;
            System.out.print("Enter course ID: ");
            courseID = sc.nextLine().toUpperCase();
            for (Course c : courseList) {
                if (c.getCourseID().equals(courseID)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                System.out.println("Course ID does not exists. Please enter again.");
            else
                break;
        }
        ArrayList<Index> indexByCourse = new ArrayList<Index>();
        for (Index index : allIndexes)
            if (index.getCourseID().equals(courseID)) {
                indexByCourse.add(index);
                System.out.println(index.getIndexID());
            }
        int indexid = 0;
        while (true)
        {
            boolean found = false;
            System.out.print("Enter index ID: ");
            indexid = sc.nextInt();
            for (Index i : indexByCourse) {
                if (i.getIndexID() == indexid) {
                    found = true;
                    break;
                }
            }
            if (!found)
                System.out.println("Index ID does not exists. Please enter again.");
            else
                break;
        }

        ArrayList<StudentCourse> studentByIndex = new ArrayList<StudentCourse>();
        for (StudentCourse student : allStudents)
            if (student.getCourseID().equals(courseID) && student.getIndexID() == indexid)
                studentByIndex.add(student);
        System.out.println("Course ID: " + courseID);
        System.out.println("Index ID: " + indexid);
        for (StudentCourse studentInCourse : studentByIndex)
        {
            StudentCourse course = null;
            for (StudentCourse studentCourse : allStudents)
                if (studentCourse.getUserid().equals(studentInCourse.getUserid())) {
                    course = studentCourse;
                    break;
                }
            for (Student student : students)
                if (student.getUserid().equals(studentInCourse.getUserid())) {
                    System.out.println(student.getUserName() + " " + student.getMatricID() + " " + student.getName() + " " + course.getRegisterStatus());
                    break;
                }
        }

    }



    public void DisplayStudentsByCourse() {
        StudentCourseData studentData = new StudentCourseData();
        StudentData studentsData = new StudentData();
        ArrayList<StudentCourse> allStudents = new ArrayList<>();
        try {
            allStudents = StudentCourseData.initStudentCourses();
        } catch (Exception e) {
            System.out.println("Display students by course encountered exception - " + e.getMessage());
            return;
        }
        ArrayList<Student> students = new ArrayList<>();
        try {
            students = StudentData.initStudents();
        } catch (Exception e) {
            System.out.println("Display students by course encountered exception - " + e.getMessage());
            return;
        }

        ArrayList<Course> courseList = courseIndexController.getCourseinfoDB();
        String courseID = "";
        Scanner sc = new Scanner(System.in);
        while (true)
        {
            boolean found = false;
            System.out.print("Enter course ID: ");
            courseID = sc.nextLine().toUpperCase();
            for (Course c : courseList) {
                if (c.getCourseID().equals(courseID)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                System.out.println("Course ID does not exists. Please enter again.");
            else
                break;
        }
        ArrayList<StudentCourse> studentByCourse = new ArrayList<StudentCourse>();
        for (StudentCourse student : allStudents)
            if (student.getCourseID().equals(courseID))
                studentByCourse.add(student);
        System.out.println("Course Code: " + courseID);
        for (StudentCourse studentInCourse : studentByCourse)
        {
            for (Student student : students)
                if (student.getUserid().equals(studentInCourse.getUserid())) {
                    System.out.println(studentInCourse.getUsername() + " " + student.getMatricID() + " " + student.getName());
                    break;
                }
        }

    }

    public void CreateCourse() {
        ArrayList<Course> courseList = courseIndexController.getCourseinfoDB();

        String courseCode = "";
        boolean flag;

        Scanner sc = new Scanner(System.in);

        do {
            flag = false;
            System.out.print("Enter the course ID: ");
            courseCode = sc.nextLine().toUpperCase();
            for (Course c : courseList) {
                if (c.getCourseID().equals(courseCode)) {
                    System.out.println("Course ID already exists. Please enter again.");
                    flag = true;
                    break;
                }
            }
        } while (flag);

        Course newCourse = ReadCourseHelper(courseCode, sc);

        int result = courseIndexController.addCourse(newCourse);
        if (result == 0) {
            System.out.println("Successfully added new course");
            System.out.println(newCourse.toString());
        }
        else {
            System.out.println("Encountered error while adding new course");
        }
    }

    public void CreateIndex(){
        ArrayList<Course> courseList = courseIndexController.getCourseinfoDB();

        String courseCode = "";
        boolean flag;

        IndexData indexData = new IndexData();
        ArrayList<Index> allIndexes = new ArrayList<>();
        try {
            allIndexes = IndexData.initIndex();
        } catch (Exception e) {
            System.out.println("Display students by index encountered exception - " + e.getMessage());
            return;
        }

        Scanner sc = new Scanner(System.in);
        do {
            flag = false;
            System.out.print("Enter the course ID: ");
            courseCode = sc.nextLine().toUpperCase();
            for (Course c : courseList) {
                if (c.getCourseID().equals(courseCode)) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                System.out.println("Course ID does not exist. Please try again.");
            else
                break;
        } while (!flag);

        ArrayList<Index> indexByCourse = new ArrayList<Index>();
        for (Index index : allIndexes)
            if (index.getCourseID().equals(courseCode)) {
                indexByCourse.add(index);
                System.out.println(index.getIndexID());
            }
        int indexid = 0;
        while (true)
        {
            boolean found = false;
            System.out.print("Enter index ID to add: ");
            indexid = sc.nextInt();
            for (Index i : indexByCourse) {
                if (i.getIndexID() == indexid) {
                    found = true;
                    break;
                }
            }
            if (found)
                System.out.println("Index ID exists. Please enter a different index ID.");
            else
                break;
        }
        String Sindexid = Integer.toString(indexid);
        int IcourseCode = Integer.parseInt(courseCode);
        Index index = new Index(Sindexid, IcourseCode, "TBC",0,0);
        courseIndexController.addIndex(index);
        System.out.println("Index ID successfully added!");

    }

    public void CreateStudent(){
        // allow current admin to create a new student
        int num_tries = 3;
        String try_again_message = "Please try again.";
        String failure_message = "You have made " + num_tries + " wrong inputs, cancelling operation.";

        String username = cmd.input("Enter new user's username: ");
        String password;
        if (cmd.consoleAvail()){
            char[] password_char = cmd.secretInput("Enter new user's password: ");
            password = new String(password_char);
        }
        else{
            password = cmd.input("Enter new user's password: ");
        }

        UserAcc.acc_info acc_info = new UserAcc.acc_info(username, password);
        UserAcc userAcc = userController.getNewUserAcc(acc_info, "student");
        Student newStudent = studentController.getNewStudent();

        int location = 0;
        boolean complete = false;
        while (!complete) {
            if (num_tries <= 0){
                cmd.display(failure_message);
                complete = true;
                break;
            }
            try {
                switch (location){
                    case 0:
                        String name = cmd.input("Enter student name: ");
                        newStudent.setName(name);
                        location++;
                    case 1:
                        String matricID = cmd.input("Enter matric ID in form U/SXXXXXXXA-Z: ");
                        newStudent.setMatricID(matricID);
                        location++;
                    case 2:
                        String gender = cmd.input("Enter gender (m/f): ");
                        newStudent.setGender(gender);
                        location++;
                    case 3:
                        String nationality = cmd.input("Enter nationality: ");
                        newStudent.setNationality(nationality);
                        location++;
                    case 4:
                        String email = cmd.input("Enter email: ");
                        newStudent.setEmail(email);
                        location++;
                    case 5:
                        String course_of_study = cmd.input("Enter course of study: ");
                        newStudent.setCourse_of_study(course_of_study);
                        location++;
                    case 6:
                        String phone_number = cmd.input("Enter phone number: ");
                        newStudent.setPhone_number(phone_number);
                        location++;
                    case 7:
                        String date_matriculated = cmd.input("Enter date matriculated: ");
                        newStudent.setDate_matriculated(date_matriculated);
                        location++;
                    case 8:
                        String set_default = cmd.input("Set student's access period to default? y/n: ");
                        String default_period;
                        if (set_default.equals("y") && defaultAccessAvail){
                            newStudent.setAccessStart(defaultAccessStart);
                            newStudent.setAccessEnd(defaultAccessEnd);
                        }
                        else{
                            if (!defaultAccessAvail){
                                cmd.display("Default access not available.");
                            }
                            try {
                                String accessStartStr = cmd.input("Enter start of access period in dd/mm/yy hh:mm format");
                                Calendar accessStart = CalendarController.stringToCalendar(accessStartStr);
                                String accessEndStr = cmd.input("Enter start of access period in dd/mm/yy hh:mm format");
                                Calendar accessEnd = CalendarController.stringToCalendar(accessEndStr);
                                newStudent.setAccessStart(accessStart);
                                newStudent.setAccessEnd(accessEnd);
                            } catch (ParseException e) {
                                cmd.display("Could not parse the datetime you entered. Please try again");
                            }
                        }


                        cmd.display("Student created with following details:");
                        cmd.displayf("Name: {}\nMatriculation ID: {}\nGender: {}\nNationality: {}\nEmail: {}\n" +
                                        "Course of study: {}\nPhone number: {}\nDate matriculated: {}\nAccess Period: {}",
                                newStudent.getAllDetails());
                        studentController.setUser(newStudent);
                        studentController.saveStudentToDB(userAcc, newStudent);
                        System.out.println("saved!");
                        studentController.refreshInfoDB();
                        System.out.println("infodb refreshed");
                        studentController.readUserCredDB();
                        System.out.println("creddb refreshed");
                        complete = true;
                        break;
                }

            }
            catch (IllegalArgumentException e){
                num_tries --;
                cmd.display(e.getMessage());
                cmd.display(try_again_message);
            }
        }
    }
    public void editAccessPeriod(){
        // allow admin to access period of a student
        String matricID = cmd.input("Enter Student's Matriculation ID");
        String id = studentController.fetchStudentUIDFromMatricID(matricID);
        if (id==null){
            System.out.println("Student with matriculation ID " +matricID+" was not found.");
        }

        String accessPeriod = cmd.input("Enter access period in ?? format");
        try {
            studentController.editExistingStudentInDB(id, 8,accessPeriod);
        } catch (IOException e) {
            System.out.println("couldn't edit the student at this time.");
        }
        //todo fix only can be called once
        //each time you call the student_info.txt has a new line on top
        // next times you call it will add a comma which breaks readDB or some other reading function
        // todo remove comments before submission!
    }
    public void editDefaultAccessPeriod()
    {
        try{
            String accessStartStr = cmd.input("Enter start of access period in dd/mm/yy hh:mm format");
            Calendar accessStart = CalendarController.stringToCalendar(accessStartStr);
            String accessEndStr = cmd.input("Enter start of access period in dd/mm/yy hh:mm format");
            Calendar accessEnd = CalendarController.stringToCalendar(accessEndStr);
            defaultAccessStart = accessStart;
            defaultAccessEnd = accessEnd;
        }
        catch (ParseException e){
            System.out.println("Input was unable to be parsed. Please check format");
        }

    }

    public void EditStudent(String matricID, String gender, String nationality,
                            String email, String course_of_study, String phone_number, String date_matriculated,
                            Object timetable){

    }
    public Student FetchStudent(){
        return null;
    }
}
