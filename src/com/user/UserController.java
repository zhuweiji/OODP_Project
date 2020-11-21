package com.user;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

class UserController {
    private final Path datadir = Main.datadir;
    private final Path studentinfopath = Main.studentinfopath;
    private final Path usercredpath = Main.usercredpath;
    private final Path admininfopath = Main.admininfopath;
    private LinkedHashMap<String, String[]> usercredDB = readUserCredDB();
    private UserAcc user;
    public String useridCount;

    //singleton design pattern
    private static final UserController instance = new UserController(null);

    public static UserController getInstance(UserAcc user) {
        instance.setUser(user);
        instance.useridCount = instance.getUserCountFromDB();
        return instance;
    }

    protected UserController(UserAcc user) {
        this.user = user;
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
                String[] id_data = data.split(":");
                userinfo.put(id_data[0], id_data[1]);
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
    LinkedHashMap<String, String[]> readUserCredDB(){
        return LogInHandler.readDB(usercredpath);
    }

    public void pushDB(Path filepath, String[] str) throws IOException {
        File file = new File(filepath.toString());
        BufferedWriter writer = new BufferedWriter(new FileWriter((file)));
        for (String item: str){
            writer.write(item);
        }
    }
    public void saveUserCredentials(UserAcc user){
        int count = Integer.parseInt(useridCount);
        count++;
        useridCount=Integer.toString(count);
        String[] useracc_details = {user.getUser_id(),user.getUsername(),user.getSalt(),user.getPassword(),
                user.getPermissions()};
        try {
            pushDB(usercredpath, useracc_details);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] fetchUserAccDetails(String id){
        String username;
        String[] salt_pw_perm_id;
        for (HashMap.Entry<String, String[]> entry : usercredDB.entrySet()) {
            if (entry.getValue()[4].equals(id)) {
                username = entry.getKey();
                salt_pw_perm_id = entry.getValue();
                String[] result = new String[6];
                result[0] = username;
                System.arraycopy(salt_pw_perm_id, 0, result, 1, salt_pw_perm_id.length); // todo length of array is 5??
                return result;
            } else {
                return null;
            }
        }
        return null;
    }
}


class StudentController extends UserController {
    private Student user;
    private final Path studentinfopath = getStudentinfopath();

    private HashMap<String, String> userinfoDB = readUserInfoDB(studentinfopath);
    private Calendar defaultAccessTime;

    private static final StudentController instance = new StudentController(null);

    public StudentController(Student student) {
        super(student);
    }

    @Override
    public Student getUser() {
        return user;
    }

    public void setUser(Student user) {
        this.user = user;
    }

    public static StudentController getInstance(){
        instance.useridCount = instance.getUserCountFromDB();
        return instance;
    }

    public Student getNewStudent(UserAcc.acc_info acc_details){
        Student newstudent = new Student(acc_details, instance.getUseridCount());
        setUser(newstudent);
        return newstudent;
    }

    public Student getExistingStudent(String username, String hashed_pw, String salt,
                                      String id){
        String[] details = fetchStudentDetails(id);
        UserAcc.acc_info acc_details = new UserAcc.acc_info(username, hashed_pw);
        acc_details.setSalt(salt);
        return new Student(acc_details, id, details[0], details[1], details[2], details[3], details[4], details[5],
                details[6],details[7], details[8]);

    }

    public String[] fetchStudentDetails(String id) {
        String[] details = userinfoDB.get(id).split(",");
        String name = details[0];
        String matricID = details[1];
        String gender = details[2];
        String nationality = details[3];
        String email = details[4];
        String phone_number = details[5];
        String course_of_study = details[6];
        String date_matriculated = details[7];
        Object Timetable = details[8];

        return details;
    }


    public void saveStudentToDB(Student student){
        saveUserCredentials(student);
        try {
            pushDB(studentinfopath,student.getAllDetails());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void refreshInfoDB(){
        userinfoDB = readUserInfoDB(studentinfopath);
    }


    public void setDefaultAccessTime(Calendar accessTime){
        defaultAccessTime = accessTime;
    }

    public Calendar getDefaultAccessTime(){
        return defaultAccessTime;
    }
}
class AdminController extends UserController{
    private Admin user;
    private final Path admininfopath = getAdmininfopath();
    private HashMap<String, String> admininfoDB = readUserInfoDB(admininfopath);

    private static final AdminController instance = new AdminController(null);

    public AdminController(Admin admin) {
        super(admin);
    }

    public static AdminController getInstance(){
        instance.useridCount = instance.getUserCountFromDB();
        return instance;
    }

    @Override
    public Admin getUser() {
        return user;
    }

    public void setUser(Admin user) {
        this.user = user;
    }

    public Admin getNewAdmin(UserAcc.acc_info acc_details){
        Admin newadmin = new Admin(acc_details, instance.getUseridCount());
        setUser(newadmin);
        return newadmin;
    }

    public Admin getExistingAdmin(String username, String hashed_pw, String salt,
                                  String id){
        String[] admin_info = fetchAdminDetails(id);
        if (admin_info == null){
            return null;
        }
        UserAcc.acc_info acc_details = new UserAcc.acc_info(username, hashed_pw);
        acc_details.setSalt(salt);
        return new Admin(acc_details, id, admin_info[0], admin_info[1], admin_info[2]);
    }

    public String[] fetchAdminDetails(String id){
        String details = admininfoDB.get(id);
        if (details == null){
            System.out.println("couldn't find this user's details in admin details file");
            return null;
        }
        String[] values = details.split(",");

        return values;
    }

}