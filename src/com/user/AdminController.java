package com.user;


import com.course.Course;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import com.user.*;
import com.course.*;

public class AdminController extends UserController{
    private Admin user;
    private final Path admininfopath = getAdmininfopath();
    private HashMap<String, Admin> admininfoDB = new HashMap<>();
    private AccountData accountData = new AccountData();

    private static final AdminController instance = new AdminController();

    public AdminController() {
        super();
        refreshAdminDB();
    }

    public static AdminController getInstance(){
        instance.useridCount = instance.getUserCountFromDB();
        return instance;
    }

    public Admin getAdmin() {
        return user;
    }

    public void setUser(Admin user) {
        this.user = user;
    }

    public Admin getNewAdmin(UserAcc.acc_info acc_details){
        Admin newadmin = new Admin(instance.getUseridCount());
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
        return new Admin(id, admin_info[0], admin_info[1], admin_info[2]);
    }

    public String[] fetchAdminDetails(String id){
        Admin details = admininfoDB.get(id);
        if (details == null){
            System.out.println("couldn't find this user's details in admin details file");
            return null;
        }

        return details.getAllDetails();
    }

    public void refreshAdminDB() {
        ArrayList<Admin> newData = null;
        try {
            newData = accountData.initAdmins();
        } catch (Exception e) {
            System.out.println("Refresh course database encountered exception - " + e.getMessage());
            return;
        }
        // Convert ArrayList -> HashMap
        HashMap<String, Admin> newAdmininfoDB = new HashMap<>();
        for (Admin admin : newData)
            newAdmininfoDB.put(admin.getUserId(), admin);
        // Replace our existing HashMap
        admininfoDB = newAdmininfoDB;
    }

    public void saveAdminToDB(Admin admin){
        refreshAdminDB();
        String lastId = "";
        for (Admin ad : admininfoDB.values()) {
            if (ad.getAdminID().compareTo(lastId) > 0)
                lastId = ad.getAdminID();
        }
        admin.setAdminID(lastId + 1);
        try {
            pushDB(admininfopath, admin.getAllDetails(), ",",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}