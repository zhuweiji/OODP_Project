package com;

import com.user.Student;
import com.user.StudentController;

/** Utilises ConsoleUI for UI functions
 * @version 1
 * @author zhuweiji,
 */
public class StudentInterface {
    /**
     * Similarly to AdminInterface, this StudentInterface forms the UI layer of the program
     * UI layer that gates access to Students to do whatever their permissions allow
     * All UI actions that require user input are performed here
     * Contains user-type controllers to allow editing of database entity objects
     * All controllers are singletons
     * @param studentController - StudentController class to allow creation of new Students in db
     * @param ConsoleUserInterface - UI class that contains all UI functionality
     * @param instance - stores the one instance of this class
     */

    private Student logged_on_user;
    private static StudentController studentController = StudentController.getInstance();
    private final ConsoleUserInterface cmd = ConsoleUserInterface.getInstance();
    private static final StudentInterface instance = new StudentInterface();

    public static StudentInterface getInstance(String username,String hashed_pw,String salt,String id)
    {
        Student logged_on_user = studentController.getExistingStudent(username, hashed_pw, salt, id);
        if (logged_on_user != null){
            instance.logged_on_user = logged_on_user;
        }
        return instance;
    }

    private StudentInterface(){
        // should remain empty
    }

    public void run() {
        /**
         * entry into Student functionality
         * Student can perform all functions in here
         */

        cmd.display("\n\n-------------------------------");
        cmd.display("Welcome "+ logged_on_user.getName()+ " !");
        while (true){
            cmd.display("Here are your available options: ");
            cmd.display("0:");
            cmd.display("1:");
            cmd.display("2:");
            cmd.display("3:");
            cmd.display("4:");
            cmd.display("-1: Exit");
            int choice = Integer.parseInt(cmd.input("Enter your choice: "));
            switch (choice){
                case -1 -> System.exit(0);

            }
        }
    }
}
