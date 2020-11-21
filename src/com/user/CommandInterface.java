package com.user;

import java.io.Console;
import java.util.Scanner;

public class CommandInterface {
    private final Scanner sc = new Scanner(System.in);
    Console console = System.console();
    private static CommandInterface instance = new CommandInterface();

    private CommandInterface(){
    }

    public static CommandInterface getInstance(){
        return instance;
    }

    public void display(String str){
        System.out.println(str);
    }
    public void inlinedisplay(String str){
        System.out.print(str);
    }
    public void displayf(String str, String[] args){
        String output = str;
        for (String arg: args){
            output = output.replaceFirst("[{][}]", arg);
        }
        System.out.println(output);
    }

    public String input(String message){
        System.out.print(message);
        return sc.nextLine();
    }

    public String input(){
        return sc.nextLine();
    }

    public boolean consoleAvail(){
        return (console != null);
    }

    public char[] secretInput(String message){
        return console.readPassword(message);
    }


}
