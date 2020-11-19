package com.user;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class UserController {
    private final Path datadir = Main.datadir;
    private final Path userinfopath = Main.userinfopath;
    private final Path usercredpath = Main.usercredpath;

    private User user;

    //singleton design pattern
    private static final UserController instance = new UserController(null);

    public static UserController getInstance(User user) {
        instance.setUser(user);
        return instance;
    }

    protected UserController(User user) {
        this.user = user;
    }
    //end singleton pattern

    public void setUser(User user){
        this.user = user;
    }

    public String getUser(){
        if (user == null){
            return "no user";
        }
        return user.getName();
    }

    public Path getUserinfopath(){
        return userinfopath;
    }

    public Path getUsercredpath(){
        return usercredpath;
    }

    HashMap<String, String> readUserInfoDB(Path filepath) {
        File file = new File(filepath.toString());
        HashMap<String, String> userinfo = new HashMap<>();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] username_data = data.split(":");
                userinfo.put(username_data[0], username_data[1]);
            }
            return userinfo;
        } catch (FileNotFoundException e) {
            System.out.println("user_info file not found in data dir");
            try {
                if (file.createNewFile()) {
                    System.out.println("user_info file created");
                    return null;
                } else {
                    System.out.println("File was unable to be created");
                    throw new IOException();
                }
            } catch (IOException f) {
                System.out.println("Fatal IO exception occured while creating file");
                f.printStackTrace();
            }
        }
        return null;
    }
    LinkedHashMap<String, String[]> readUserCredDB(){
        return LogInHandler.readDB(usercredpath);
    }

    void pushDB(Path filepath, String[] str) throws IOException {
        File file = new File(filepath.toString());
        BufferedWriter writer = new BufferedWriter(new FileWriter((file)));
        for (String item: str){
            writer.write(item);
        }
    }
}

class StudentController extends UserController {
    private Student user;
    private final Path userinfopath = getUserinfopath();
    private final Path usercredpath = getUsercredpath();

    private HashMap<String, String> userinfoDB;
    private LinkedHashMap<String, String[]> usercredDB;
    private Calendar defaultAccessTime;

    private static final StudentController instance = new StudentController(null);

    public StudentController(Student student) {
        super(student);
        userinfoDB = readUserInfoDB(userinfopath);
        usercredDB = readUserCredDB();
    }

    public static StudentController getInstance(){
        return instance;
    }

    public Student getNewStudent(){
        Student newstudent = new Student();
        setUser(newstudent);
        return newstudent;
    }


    public Student fetchStudent(String studentname) {
        String[] details = userinfoDB.get(studentname).split(",");
        int userid = Integer.parseInt(details[0]);
        String name = details[1];
        String matricID = details[2];
        String gender = details[3];
        String nationality = details[4];
        String email = details[5];
        String phone_number = details[6];
        String course_of_study = details[7];
        String date_matriculated = details[8];
        //todo Timetable?

        Student.acc_info acc_details = null;
        for (HashMap.Entry<String, String[]> entry : usercredDB.entrySet()) {
            if (Integer.parseInt(entry.getValue()[4]) == userid) {
                String username = entry.getKey();
                String[] salt_pw_perm_id = entry.getValue();
                acc_details = new Student.acc_info(name, username, salt_pw_perm_id[1]);
            } else {
                return null;
            }
        }
        if (acc_details == null){
            return null;
        }
        return new Student(acc_details, matricID, gender, nationality, email, course_of_study, phone_number,
                date_matriculated, null);
    }

    public void refreshInfoDB(){
        userinfoDB = readUserInfoDB(userinfopath);
    }

    public void refreshCredDB(){
        usercredDB = readUserCredDB();
    }

    public void setDefaultAccessTime(Calendar accessTime){
        defaultAccessTime = accessTime;
    }

    public Calendar getDefaultAccessTime(){
        return defaultAccessTime;
    }
}