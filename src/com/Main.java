package com;

import com.user.LogInHandler;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static Path cwd;
    public static Path datadir;
    public static Path usercredpath;
    public static Path studentinfopath;
    public static Path admininfopath;
    public static void main(String[] args) {

        get_data_paths();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("cwd IS:");
        System.out.println(cwd.toString());
        System.out.println(datadir.toString());

        LogInHandler loginhandler = LogInHandler.startHandler();
        loginhandler.run();

    }

    /**
     * Unfortunately the cwd on IDEA is within the project folder but cwd when running from console is within bin folder
     * function just changes cwd when running from console to parent folder
     */
    private static void get_data_paths(){
        cwd = Paths.get(System.getProperty("user.dir"));
        if (ConsoleUserInterface.getInstance().consoleAvail()){
            cwd = cwd.getParent();
        }
        datadir = Paths.get(cwd.toString(), "data");
        usercredpath = Paths.get(datadir.toString(), "user_cred.txt");
        studentinfopath = Paths.get(datadir.toString(), "student_info.txt");
        admininfopath = Paths.get(datadir.toString(), "admin_info.txt");
    }
}

