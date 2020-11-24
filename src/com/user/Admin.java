package com.user;


public class Admin {
    private String userid;
    private String adminID;
    private String name;
    private String email;

    public Admin(String user_id, String adminID, String name, String email) {
        this.userid = user_id;
        this.adminID = adminID;
        this.name = name;
        this.email = email;
    }

    public Admin(String userid) {
        this.userid = userid; }

    public String getUserId() { return userid; }

    public void setUserId(String userID) { this.userid = userID; }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getAllDetails() {
        return new String[] {this.userid, this.adminID, this.name, this.email};
    }

}


