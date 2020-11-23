package com.user;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

import com.Main;
import com.course.Course;
import com.course.CourseData;
import com.course.Index;
import com.course.IndexData;
import com.course.StudentCourse;
import com.course.StudentCourseData;

public class UserController {
    private final Path datadir = Main.datadir;
    private final Path studentinfopath = Main.studentinfopath;
    private final Path usercredpath = Main.usercredpath;
    private final Path admininfopath = Main.admininfopath;
    private LinkedHashMap<String, String[]> usercredDB = readUserCredDB();
    private UserAcc user;
    public String useridCount; // might have state change if multiple controllers saving to db at once
    public static ArrayList<StudentCourse> studentCourseList = StudentCourseData.studentCourseList;
    public static ArrayList<Course> courseList = CourseData.courseList;
	public static ArrayList<Index> indexList = IndexData.indexList;
	//StudentCourse
	public static ArrayList<StudentCourse> getStudentCourses(){ return studentCourseList; }
	// Course
	public static ArrayList<Course> getCourse(){ return courseList; }
			
	// Index
	public static ArrayList<Index> getIndex(){ return indexList; }

    //singleton design pattern
    private static final UserController instance = new UserController();

    public static UserController getInstance() {
        instance.useridCount = instance.getUserCountFromDB();
        return instance;
    }

    protected UserController() {

    }
    //end singleton pattern

    public void setUser(UserAcc user){
        this.user = user;
    }

    public UserAcc getUser(){
        if (user == null){
            return null;
        }
        return user;
    }

    public UserAcc getNewUserAcc(UserAcc.acc_info acc_info, String permissions){
        return new UserAcc(acc_info, instance.useridCount, permissions);
    }

    public Path getStudentinfopath(){
        return studentinfopath;
    }

    public Path getUsercredpath(){
        return usercredpath;
    }

    public Path getAdmininfopath(){return admininfopath;}

    public void refreshCredDB(){
        usercredDB = readUserCredDB();
    }

    public Path getDatadir() {
        return datadir;
    }

    public String getUseridCount() {
        return useridCount;
    }

    public String getUserCountFromDB(){
        int count = 0;
        try {
            Scanner sc = new Scanner(usercredpath);
            while (sc.hasNextLine()){
                sc.nextLine();
                count++;
            }
            return Integer.toString(count);
        } catch (IOException e) {
            System.out.println("Print user credentials file not found");
            e.printStackTrace();
        }
        return null;
    }


    HashMap<String, String> readUserInfoDB(Path filepath) {
        File file = new File(filepath.toString());
        HashMap<String, String> userinfo = new HashMap<>();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] id_data = data.split(",");
                String[] user_data = Arrays.copyOfRange(id_data, 1, id_data.length);
                String userdata = String.join(",", user_data);
                userinfo.put(id_data[0], userdata);

            }
            return userinfo;
        } catch (FileNotFoundException e) {
            System.out.println(filepath + " file not found in data dir");
            try {
                if (file.createNewFile()) {
                    System.out.println(filepath + " file created");
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
    public LinkedHashMap<String, String[]> readUserCredDB(){
        return LogInHandler.readDB(usercredpath);
    }

    public void pushDB(Path filepath, String[] str, String delimiter, boolean append) throws IOException {
        // appends line
        File file = new File(filepath.toString());
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
        writer.newLine();
        int count=0;
        for (String item: str){
            count++;
            writer.write(item);
            if (count != str.length){
                writer.write(delimiter);
            }

        }
//        writer.newLine();
        writer.close();
    }
    public void saveUserCredentials(UserAcc user){
        int count = Integer.parseInt(useridCount);
        count++;
        useridCount=Integer.toString(count);
        String[] useracc_details = {user.getUser_id(),user.getUsername(),user.getSalt(),user.getPassword(),
                user.getHashedPermissions()};
        try {
            pushDB(usercredpath, useracc_details, ",",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] fetchUserAccDetails(String id){
        String username;
        String[] salt_pw_perm_id;
        for (HashMap.Entry<String, String[]> entry : usercredDB.entrySet()) {
            if (entry.getValue()[0].equals(id)) {
                username = entry.getKey();
                salt_pw_perm_id = entry.getValue();
                String[] result = new String[6];
                result[0] = username;
                System.arraycopy(salt_pw_perm_id, 0, result, 1, salt_pw_perm_id.length); // todo length of array is 5??
                return result;
            }

        }
        return null;
    }

}

// -------------------------------------------------------------------------------------------------------

