package com.user;


import com.course.CalendarController;

import java.util.Calendar;

public class Student {
    private String userName;
    private String userid;
    private String name;
    private String matricID;
    private String gender;
    private String nationality;
    private String email;
    private String course_of_study;
    private String phone_number;
    private String date_matriculated; // todo change to date format
    private Calendar accessStart;
    private Calendar accessEnd;
    private String notiMode;

    public Student(String user_id,String userName,String name, String matricID, String gender, String nationality,
                   String email, String course_of_study, String phone_number, String date_matriculated,
                   Calendar accessStart, Calendar accessEnd, String notiMode) {

        this.userid = user_id;
        this.setUserName(userName);
        this.setName(name);
        this.setMatricID(matricID);
        this.setGender(gender);
        this.setNationality(nationality);
        this.setEmail(email);
        this.setCourse_of_study(course_of_study);
        this.setPhone_number(phone_number);
        this.setDate_matriculated(date_matriculated);
        this.setAccessStart(accessStart);
        this.setAccessEnd(accessEnd);
        this.setNotiMode(notiMode);

    }

    public Student(String user_id, String string, String string2, String string3, String string4, String string5, String string6, Calendar calendar, Calendar calendar2, int choice){
        this.userid = user_id;
    }

    public String getUserid() {
        return userid;
    }

    public String[] getAllDetails(){
        return new String[]{userid, name, matricID, gender, nationality, email, course_of_study,
                phone_number, date_matriculated, };
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getMatricID() {
        return matricID;
    }

    public void setMatricID(String matricID) {
//        final String regex = "^[U|S][0-9]{7}[A-Z]"; // U XXXXXXX G todo TEST
//        if (matricID.matches(regex)){
//            this.matricID = matricID;
//        }
//        else{
//            throw new IllegalArgumentException("Matriculation ID does not match format");
//        }
        this.matricID = matricID;

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
//        gender = gender.toLowerCase();
//        switch (gender) {
//            case "male", "female" -> this.gender = gender;
//            case "m" -> this.gender = "male";
//            case "f" -> this.gender = "female";
//            default -> throw new IllegalArgumentException("gender must be male or female");
//        }
        this.gender = gender;
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
//        final String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
//        if (email.matches(regex)){
//            this.email = email;
//        }
//        else{
//            throw new IllegalArgumentException("Entry was not an email format");
//        }
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
//        final String regex = "^[0-9]{8}$|^[+][0-9]{10,11}"; // 8digit number or +countrycode phone number
//        if (phone_number.matches(regex)){
//            this.phone_number = phone_number;
//        }
//        else{
//            throw new IllegalArgumentException("Phone number must be 8 digits long or have country code");
//        }

        this.phone_number = phone_number;

    }

    public String getDate_matriculated() {
        return date_matriculated;
    }

    public void setDate_matriculated(String date_matriculated) {
        this.date_matriculated = date_matriculated;
    }

    public Calendar getAccessStart() {
        return accessStart;
    }

    public String getAccessStartStr(){
        return CalendarController.caltoString(accessStart);
    }

    public void setAccessStart(Calendar accessStart) {
        if (accessEnd != null) {
            if (accessStart.compareTo(accessEnd) > 0) {
                throw new IllegalArgumentException("Access start date cannot be later than end date");
            }
        }
        this.accessStart = accessStart;
    }

    public Calendar getAccessEnd() {
        return accessEnd;
    }

    public String getAccessEndStr(){
        return CalendarController.caltoString(accessEnd);
    }

    public void setAccessEnd(Calendar accessEnd) {
        if (accessStart != null) {
            if (accessEnd.compareTo(accessStart) < 0) {
                throw new IllegalArgumentException("Access end date cannot be earlier than start date");
            }
        }
        this.accessEnd = accessEnd;
    }

    public String getNotiMode() {
        return notiMode;
    }
    
    public void setNotiMode(String notiMode) {
        this.notiMode = notiMode;
    }


}