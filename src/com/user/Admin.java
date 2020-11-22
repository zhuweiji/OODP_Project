package com.user;


public class Admin {
    private String adminID;
    private String name;
    private String email;

    public Admin(String user_id, String adminID, String name, String email) {
        super();
        this.adminID = adminID;
        this.name = name;
        this.email = email;
    }

    public Admin(String user_id){
        super();
    }

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

}


