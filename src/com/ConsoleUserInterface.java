package com;

import java.io.Console;
import java.util.Scanner;

public class ConsoleUserInterface {
    private final Scanner sc = new Scanner(System.in);
    Console console = System.console();
    private static ConsoleUserInterface instance = new ConsoleUserInterface();

    private ConsoleUserInterface(){
    }

    public static ConsoleUserInterface getInstance(){
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
