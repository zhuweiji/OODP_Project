package com.user;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.nio.file.Paths;


public class scratch {
    public static void main(String[] args) throws IOException {
        Interface i = new Interface();
        Child child = (Child) i.create();
        child.shout();
        System.out.println(child.getName());

    }
}

class Interface{
    public Parent create(){
        return new Child(2, "thomas");
    }

}

abstract class Parent{
    private int var;
    public Parent(int var){
        this.var = var;
    }
    public int getvar(){ return var; }
}

class Child extends Parent{

    private String name;

    public Child(int var, String name) {
        super(var);
        this.name = name;
    }
    public void shout(){
        System.out.println("AHH");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
