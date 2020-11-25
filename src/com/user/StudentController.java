package com.user;

import com.Main;
import com.course.CalendarController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StudentController extends UserController {
    private Student user;
    private final Path studentinfopath = getStudentinfopath();

    private HashMap<String, String> studentinfoDB = readUserInfoDB(studentinfopath);

    private static final StudentController instance = new StudentController();

    public StudentController() {
        super();
    }

    public static StudentController getInstance(){

        instance.useridCount = instance.getUserCountFromDB();
        return instance;
    }

    public Student getStudent() {
        return user;
    }

    public void setUser(Student user) {
        this.user = user;
    }



    public Student getNewStudent(){
        Student newstudent = new Student(instance.getUseridCount());
        setUser(newstudent);
        return newstudent;
    }

    public Student getExistingStudent(String username, String hashed_pw, String salt,
                                      String userid) {
        String[] details = fetchStudentDetails(userid);
        UserAcc.acc_info acc_details = new UserAcc.acc_info(username, hashed_pw);
        acc_details.setSalt(salt);
        {
            Calendar accessStart;
            Calendar accessEnd;
            try {
                accessStart = CalendarController.stringToCalendar(details[9]);
                accessEnd = CalendarController.stringToCalendar(details[10]);
                return new Student(userid, details[0], details[1], details[2], details[3], details[4], details[5],
                        details[6], details[7], details[8], accessStart, accessEnd, details[11]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String[] fetchStudentDetails(String id) {
        String foo = studentinfoDB.get(id);
        if (foo == null){
            return null;
        }
        String[] details = foo.split(",");
        String name = details[0];
        String matricID = details[1];
        String gender = details[2];
        String nationality = details[3];
        String email = details[4];
        String phone_number = details[5];
        String course_of_study = details[6];
        String date_matriculated = details[7];
        String access_start = details[8];
        String access_end = details[9];
        String notiMode = details[10];

        return details;
    }

    public String fetchStudentUIDFromMatricID(String matricID){
        HashMap<String, String> db = studentinfoDB;
        String[] details;
        for (Map.Entry<String, String> entry: db.entrySet()){
            details = entry.getValue().split(",");
            if (details[1].equals(matricID)){
                return entry.getKey();
            }
        }
        return null;
    }

    public String[] fetchStudentDetailsFromMatricID(String matricID){
        HashMap<String, String> db = studentinfoDB;
        String[] details;
        for (Map.Entry<String, String> entry: db.entrySet()){
            details = entry.getValue().split(",");
            if (details[1].equals(matricID)){
                return details;
            }
        }
        return null;
    }


    public void saveStudentToDB(UserAcc userAcc, Student student){
        saveUserCredentials(userAcc);
        try {
            pushDB(studentinfopath,student.getAllDetails(), ",",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editExistingStudentInDB(String id, int index, String value) throws IOException {
        String[] details = fetchStudentDetails(id);
        details[index] = value;
        StringBuffer sb = new StringBuffer();
        int count=0;
        for (String s : details) {
            sb.append(s);
            if (count< details.length){
                sb.append(",");
            }
            count++;
        }
        studentinfoDB.put(id,sb.toString());
        Files.write(Main.studentinfopath, "".getBytes());
        boolean not_first_line = false;
        for (String key: studentinfoDB.keySet()){
            String[] output = new String[]{key,studentinfoDB.get(key)};
            pushDB(studentinfopath,output , ",",not_first_line);
            not_first_line = true;
        }

    }

    public void refreshInfoDB(){
        studentinfoDB = readUserInfoDB(studentinfopath);
    }

    public Calendar getDefaultAccessPeriod(){
        return null;
    }

}
