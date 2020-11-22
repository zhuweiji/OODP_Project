package com;

import com.user.Student;
import com.user.StudentController;

public class StudentInterface {
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
        cmd.display("\n\n-------------------------------");
        cmd.display("Welcome "+ logged_on_user.getName()+ " !");
        while (true){
            cmd.display("0: Exit");
            int choice = Integer.parseInt(cmd.input("Enter your choice: "));
            switch (choice){
                case 0 -> System.exit(0);
            }
        }
    }
}
