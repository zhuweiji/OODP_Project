package com.user;
import java.io.Console;
import java.io.File;  // Import the File class
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    static Path datadir = Paths.get(System.getProperty("user.dir").toString(), "data");
    public static void main(String[] args) {
        CommandInterface commandInterface = CommandInterface.startInterface();

    }
}

