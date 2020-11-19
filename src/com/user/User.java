package com.user;

enum gender{male, female}

public class User {
    private static int count = 0;
    private String name;
    private String username;
    private String password; // hashed password and salt
    private String salt;
    private int user_id;

    public User(String name,String username,String password){
        count++;

        this.name = name;
        this.username = username;
        this.user_id = count;
        this.salt = LogInHandler.getNextSalt().toString();
        setPassword(password);

    }

    public User(){

    }

    public String getUsername(){
        return username;
    }

    public void sendEmail(){
        //todo fill in
    }

    public static int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = LogInHandler.hash(password, salt);
    }

    public int getUser_id(){ return user_id; }
}

class Student extends User{
    static class acc_info{
        String name;
        String username;
        String password;
        String permissions = "student";

        public acc_info(String name, String username, String password){
            this.name = name;
            this.username = username;
            this.password = password;
        }
    }

    private String matricID;
    private String gender;
    private String nationality;
    private String email;
    private String phone_number;
    private String course_of_study;
    private String date_matriculated;
    private Object Timetable;

    public Student(acc_info acc_details, String matricID, String gender, String nationality,
                   String email, String course_of_study, String phone_number, String date_matriculated,
                   Object timetable) {

        super(acc_details.name, acc_details.username, acc_details.password);
        this.matricID = matricID;
        this.gender = gender;
        this.nationality = nationality;
        this.email = email;
        this.course_of_study = course_of_study;
        this.phone_number = phone_number;
        this.date_matriculated = date_matriculated;
        Timetable = timetable;

    }

    public Student(){
    }



    public String getMatricID() {
        return matricID;
    }

    public void setMatricID(String matricID) {
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
        final String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        if (email.matches(regex)){
            this.email = email;
        }
        else{
            throw new IllegalArgumentException();
        }
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
        final String regex = "^[0-9]{8}$|^[+][0-9]{10,11}"; // 8digit number or +countrycode phone number
        if (phone_number.matches(regex)){
            this.phone_number = phone_number;
        }
        else{
            throw new IllegalArgumentException("Phone number must be 8 digits long or have country code");
        }

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

