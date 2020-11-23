package com.user;


import java.util.Calendar;

public class Student {
    private String userid;
    private String username;
    private String name;
    private String matricID;
    private String gender;
    private String nationality;
    private String email;
    private String phone_number;
    private String course_of_study;
    private String date_matriculated; // todo change to date format
    private String accessPeriod;
    private String notiMode;

    public Student(String user_id, String username,String name, String matricID, String gender, String nationality,
                   String email, String course_of_study, String phone_number, String date_matriculated,
                   String accessPeriod, String notiMode) {

    	this.userid = user_id;
    	this.username = username;
        this.name = name;
        this.matricID = matricID;
        this.gender = gender;
        this.nationality = nationality;
        this.email = email;
        this.course_of_study = course_of_study;
        this.phone_number = phone_number;
        this.date_matriculated = date_matriculated;
        this.accessPeriod = accessPeriod;
        this.notiMode = notiMode;

    }

    public Student(String user_id){
        this.userid = user_id;
    }

    public String[] getAllDetails(){
        return new String[]{userid, name, matricID, gender, nationality, email, course_of_study,
                phone_number, date_matriculated, accessPeriod};
    }

    public String getUserid() {
        return userid;
    }
    
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatricID() {
        return matricID;
    }

    public void setMatricID(String matricID) {
        final String regex = "^[U|S][0-9]{7}[A-Z]"; // U XXXXXXX G todo TEST
        if (matricID.matches(regex)){
            this.matricID = matricID;
        }
        else{
            throw new IllegalArgumentException("Matriculation ID does not match format");
        }

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        gender = gender.toLowerCase();
        switch (gender) {
            case "male", "female" -> this.gender = gender;
            case "m" -> this.gender = "male";
            case "f" -> this.gender = "female";
            default -> throw new IllegalArgumentException("gender must be male or female");
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
            throw new IllegalArgumentException("Entry was not an email format");
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

    public Calendar getAccessPeriod() {
        return null; //todo output calendar format
    }

    public void setAccessPeriod(String accessPeriod) {
        this.accessPeriod = accessPeriod; //todo convert to Calendar format and verify correctness before changing back to String
    }
    
    public String getNotiMode() {
        return notiMode;
    }
    
    public void setNotiMode(String notiMode) {
        this.notiMode = notiMode;
    }


}