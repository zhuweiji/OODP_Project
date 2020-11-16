package com.user;
enum gender{male, female}

public class User {
    private static int count = 0;
    private String name;
    private String username;
    private String pw_salt; // hashed password and salt

    public User(String name,String username,String pw_salt){
        count++;
        this.name = name;
        this.username = username;
        this.pw_salt = pw_salt;
    }

    public String getUsername(){
        return username;
    }

    public void sendEmail(){
        //todo fill in
    }
}

class Student extends User{

    private int matricID;
    private String gender;
    private String nationality;
    private String email;
    private String course_of_study;
    private String phone_number;
    private String date_matriculated;
    private Object Timetable;

    public Student(String name,String username,String pw_salt, int matricID, String gender, String nationality,
                   String email, String course_of_study, String phone_number, String date_matriculated,
                   Object timetable) {

        super(name,username,pw_salt);
        this.matricID = matricID;
        this.gender = gender;
        this.nationality = nationality;
        this.email = email;
        this.course_of_study = course_of_study;
        this.phone_number = phone_number;
        this.date_matriculated = date_matriculated;
        Timetable = timetable;

    }

    public int getMatricID() {
        return matricID;
    }

    public void setMatricID(int matricID) {
        this.matricID = matricID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        gender = gender.toLowerCase();
        if (gender.equals("male") || gender.equals("female")){
            this.gender = gender;
        }
        else{
            throw new IllegalArgumentException("gender must be male or female");
        }
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // todo implement check if email is valid
        this.email = email;
    }

    public String getCourse_of_study() {
        return course_of_study;
    }

    public void setCourse_of_study(String course_of_study) {
        this.course_of_study = course_of_study;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDate_matriculated() {
        return date_matriculated;
    }

    public void setDate_matriculated(String date_matriculated) {
        this.date_matriculated = date_matriculated;
    }

    public Object getTimetable() {
        return Timetable;
    }

    public void setTimetable(Object timetable) {
        Timetable = timetable;
    }
}

class Admin extends User{
    private int adminID;
    private String email;

    public Admin(String name, String username, String pw_salt, int adminID, String email) {
        super(name, username, pw_salt);
        this.adminID = adminID;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }
}

