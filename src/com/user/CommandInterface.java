package com.user;

import java.io.Console;
import java.util.ArrayList;

public class CommandInterface {
    private ArrayList<User> users = new ArrayList<User>();
    private static final CommandInterface instance = new CommandInterface();

    public static CommandInterface startInterface(){
        return instance;
    }
    private CommandInterface(){
        LogInHandler loginhandler = LogInHandler.startHandler();
        Console console = System.console();
        assert console != null : "Run program in cmd.";

        String username = console.readLine("Enter your username: ");
        char[] passwordArr = console.readPassword("Enter your password: ");
        String password = new String(passwordArr);

        loginhandler.login(username, password);

    }



}
