//package com.user;
//
//import java.io.*;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//
//public class scratch {
//    public static void main(String[] args) {
//        StudentController studentController =  StudentController.getInstance();
//        System.out.println(studentController.getUser());
//        Student student = new Student();
//
//
//    }
//    private StudentController studentController;
//    public void CreateStudent(){
//        Student student = studentController.getNewStudent();
//
//        String salt = LogInHandler.getNextSalt().toString();
//        String username = "";
//        String password = "";
//        String hashed_pw = LogInHandler.hash(password, salt);
//
//    }
//}
//
//class UserController {
//    private static int created;
//    private final Path datadir = Main.datadir;
//    private final Path userinfopath = Paths.get(datadir.toString(), "student_info.txt");
//    private User user;
//
//    //singleton design pattern
//    private static final UserController instance = new UserController();
//
//    public static UserController getInstance() {
//        return instance;
//    }
//    public static UserController getInstance(User user) {
//        instance.setUser(user);
//        return instance;
//    }
//
//    protected UserController() {}
//    protected UserController(User user) {
//        this.user = user;
//    }
//    //end singleton pattern
//
//    public void setUser(User user){
//        this.user = user;
//    }
//
//    public String getUser(){
//        if (user == null){
//            return "no user";
//        }
//        return user.getName();
//    }
//
//    ArrayList<String> readDB(Path filepath) throws FileNotFoundException {
//        File file = new File(filepath.toString());
//        Scanner sc = new Scanner(file);
//
//        ArrayList<String> userinfo = new ArrayList<>();
//        while (sc.hasNextLine()) {
//            String data = sc.nextLine();
//            userinfo.add(data);
//        }
//        return userinfo;
//    }
//
//    void pushDB(Path filepath, String[] str) throws IOException {
//        File file = new File(filepath.toString());
//        BufferedWriter writer = new BufferedWriter(new FileWriter((file)));
//        for (String item: str){
//            writer.write(item);
//        }
//    }
//}
//
//class StudentController extends UserController {
//    private Student user;
//    private static final StudentController instance = new StudentController(null);
//
//    public StudentController(Student student) {
//        super(student);
//    }
//
//    public StudentController() {
//        super();
//    }
//
//    public static StudentController getInstance(){
//        return instance;
//    }
//
//    public Student getNewStudent(){
//        Student newstudent = new Student();
//        setUser(newstudent);
//        return newstudent;
//    }
//}

package com.user;

import com.ConsoleUserInterface;

import java.io.IOException;

public class scratch {
    public static void main(String[] args) throws IOException {
        ConsoleUserInterface cmd = ConsoleUserInterface.getInstance();
        UserController userController = UserController.getInstance();
        StudentController studentController = StudentController.getInstance();

        studentController.editExistingStudentInDB("2", 8, "11-11");
        //        Scanner sc = new Scanner(Main.studentinfopath);
        for (String detail : studentController.fetchStudentDetails("2")) {
            System.out.println(detail);
        }
    }
}