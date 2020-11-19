package com.user;
import java.io.Console;
import java.io.File;  // Import the File class
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static final Path datadir = Paths.get(System.getProperty("user.dir"), "data");
    public static final Path usercredpath = Paths.get(datadir.toString(), "user_cred.txt");
    public static final Path userinfopath = Paths.get(datadir.toString(), "user_info.txt");

    public static void main(String[] args) {
        CommandInterface commandInterface = CommandInterface.startInterface();

    }
}

