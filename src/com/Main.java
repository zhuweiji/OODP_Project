package com;
import com.ConsoleUserInterface;
import com.user.LogInHandler;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static final Path datadir = Paths.get(System.getProperty("user.dir"), "data");
    public static final Path usercredpath = Paths.get(datadir.toString(), "user_cred.txt");
    public static final Path studentinfopath = Paths.get(datadir.toString(), "student_info.txt");
    public static final Path admininfopath = Paths.get(datadir.toString(), "admin_info.txt");
    public static void main(String[] args) {
        LogInHandler loginhandler = LogInHandler.startHandler();
        ConsoleUserInterface cmd = ConsoleUserInterface.getInstance();

        String username = cmd.input("Enter your username: ");
        String password;
        if (cmd.consoleAvail()){
            char[] passwordArr = cmd.secretInput("Enter your password: ");
            password = new String(passwordArr);
        }
        else{
            password = cmd.input("Enter your password: ");
        }
        boolean logged_in = loginhandler.login(username,password);
//        String username;
//        String password;
        int attempts = 5;
        while (!logged_in){
            if (attempts <= 0){
                cmd.display("You have entered too many wrong attempts. The program will now exit");
                System.exit(-1);
                break;
            }
            cmd.display("You have entered the wrong username or password");
            attempts--;
            cmd.display("You have "+ attempts+" attempts left.");

            username = cmd.input("Enter your username: ");
            if (cmd.consoleAvail()){
                char[] passwordArr = cmd.secretInput("Enter your password: ");
                password = new String(passwordArr);
            }
            else{
                password = cmd.input("Enter your password: ");
            }
            logged_in = loginhandler.login(username, password);
        }

            cmd.display("\nThank you for using mySTARS!");

    }
}

