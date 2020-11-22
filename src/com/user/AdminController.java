package com.user;


import java.nio.file.Path;
import java.util.HashMap;

public class AdminController extends UserController{
    private Admin user;
    private final Path admininfopath = getAdmininfopath();
    private HashMap<String, String> admininfoDB = readUserInfoDB(admininfopath);

    private static final AdminController instance = new AdminController();

    public AdminController() {
        super();
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
        String details = admininfoDB.get(id);
        if (details == null){
            System.out.println("couldn't find this user's details in admin details file");
            return null;
        }

        return details.split(",");
    }

    public void refreshAdminInfoDB(){
        admininfoDB = readUserInfoDB(admininfopath);
    }

}