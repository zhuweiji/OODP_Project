package com;

import com.user.*;

import java.io.IOException;
/** Utilises ConsoleUI for UI functions
 * @version 1
 * @author zhuweiji,
 */
public class AdminInterface {
    /**
     * UI layer that gates access to Admins to do whatever their permissions allow
     * All UI actions that require user input are performed here
     * Contains user-type controllers to allow editing of database entity objects
     * All controllers are singletons
     * @param userController - UserController class to allow creation of new UserAccs in db
     * @param studentController - StudentController class to allow creation of new Students in db
     * @param adminController - AdminController class to allow creation of new Admins in db
     * @param ConsoleUserInterface - UI class that contains all UI functionality
     * @param instance - stores the one instance of this class
     */
    private Admin logged_on_user;
    private String defaultAccessPeriod; //todo change to Calendar format
    private static final UserController userController = UserController.getInstance();
    private static final StudentController studentController = StudentController.getInstance();
    private static final AdminController adminController = AdminController.getInstance();
    private final ConsoleUserInterface cmd = ConsoleUserInterface.getInstance();
    private static final AdminInterface instance = new AdminInterface();

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
        /**
         * entry into admin functionality
         * Admins can perform all functions in here
         */
        cmd.display("\n\n-------------------------------");
        cmd.display("Welcome "+ logged_on_user.getName()+ " !");
        while (true){
            cmd.display("Here are your available options: ");
            cmd.display("0: Edit Student's access period"); //todo implement change all student's access period
            cmd.display("1: Create new Admin user");
            cmd.display("2: Create new Student user");
            cmd.display("3: Update a course");
            cmd.display("4: Add a new course");
            cmd.display("5: Check available vacancy for an index number");
            cmd.display("6: Print student list by index number");
            cmd.display("7: Print student list by course");
            cmd.display("-1: Exit");
            int choice = Integer.parseInt(cmd.input("Enter your choice: "));
            switch (choice){
                case 0 -> editAccessPeriod();
                case 1 -> CreateAdmin();
                case 2 -> CreateStudent();
                case -1 -> System.exit(0);
            }
        }

    }

    public Admin CreateAdmin(){
        //todo fill in
        return null;
    }

    public void CreateStudent(){
        /**
         * allows admins to create a new student object in memory and in db from the UI
         * when run from console passwords are hidden from view
         * if not run from console (IDE) then console class throws exception so passwords are shown (extended functionality)
         * contains all relevant controllers to create Student
         * Student is created using Student setters - no checking is done in UI layer
         * @param num_tries - number of times user can make wrong inputs before function terminates
         */

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
        userController.getNewUserAcc(acc_info, "student");
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
                        if (set_default == "y"){
                            default_period = studentController.getDefaultStringAccessPeriod();
                        }
                        else{
                            default_period = cmd.input("Enter access period");
                        }
                        newStudent.setAccessPeriod(default_period);

                        cmd.display("Student created with following details:");
                        cmd.displayf("Name: {}\nMatriculation ID: {}\nGender: {}\nNationality: {}\nEmail: {}\n" +
                                        "Course of study: {}\nPhone number: {}\nDate matriculated: {}\nAccess Period: {}",
                                newStudent.getAllDetails());
                        studentController.setUser(newStudent);
                        studentController.saveStudentToDB(newStudent);
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
        /**
         * allows admin to change the access period of a specific student
         * Implementation: get student details from db and edit then saving back in db
         */
        String matricID = cmd.input("Enter Student's Matriculation ID");
        String id = studentController.fetchStudentUIDFromMatricID(matricID);
        if (id==null){
            System.out.println("Student with matriculation ID " +matricID+" was not found.");
        }

        String accessPeriod = cmd.input("Enter access period in ?? format");
        try {
            studentController.editExistingStudentInDB(id, 8,accessPeriod); //index 8 is accessperiod from order in db
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
        //todo fill in
        String accessPeriod = cmd.input("Enter access period in ?? format");
        studentController.getStudent().setAccessPeriod(accessPeriod);
    }

    public void EditStudent(String matricID, String gender, String nationality,
                            String email, String course_of_study, String phone_number, String date_matriculated,
                            Object timetable){

    }
    public Student FetchStudent(){
        return null;
    }
}
