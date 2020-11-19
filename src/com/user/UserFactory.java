package com.user;

import java.nio.file.Path;
import java.nio.file.Paths;

public class UserFactory {
    private final Path datadir = Main.datadir;
    private final Path userinfopath = Paths.get(datadir.toString(), "user_cred");
    public User getUser(String username, String userPermissions){
        String[] user_info = readDB(username, userinfopath);
        if (userPermissions.equalsIgnoreCase("admin")){
//            return new Admin(user_info);
            return null;
        }
        else if (userPermissions.equalsIgnoreCase("student")){
//            return new Student(user_info);
            return null;
        }
        return null;
    }
    private String[] readDB(String username, Path filepath){
        //todo fill in
        return null;
    }
}
