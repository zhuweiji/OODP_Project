package com.user;

import java.io.Console;

public class CommandInterface {
    private static final CommandInterface instance = new CommandInterface();

    public static CommandInterface startInterface(){
        return instance;
    }

    private CommandInterface(){
        LogInHandler loginhandler = LogInHandler.startHandler();
        Console console = System.console();
        assert console != null;

        String username = console.readLine("Enter your username: ");
        char[] passwordArr = console.readPassword("Enter your password: ");
        String password = new String(passwordArr);

        loginhandler.login(username, password);

    }



}
