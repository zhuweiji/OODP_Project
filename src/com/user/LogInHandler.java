package com.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Scanner;
import java.util.*;


public class LogInHandler {
    private final Path datadir = Main.datadir;
    private final Path usercredpath = Paths.get(datadir.toString(), "user_cred.txt");
    private static final LogInHandler instance = new LogInHandler();
    private static UserFactory userFactory;
    private User[] logged_in_users;

    private LogInHandler(){
        userFactory = new UserFactory();
    }

    public static LogInHandler startHandler(){
        return instance;
    }

    public User login(String username, String password){

        HashMap<String, String[]> userinfo = readDB(usercredpath);
        String[] salt_pw_perm = userinfo.get(username);
        if (salt_pw_perm != null) {
            String hashed_pw = hash(password, salt_pw_perm[0]);
            if (hashed_pw.equals(salt_pw_perm[1])) {
                if (checkPermissions(salt_pw_perm[2], salt_pw_perm[0]).equals("student")) {
                    return userFactory.getUser(username, salt_pw_perm[2]);
                } else if (checkPermissions(salt_pw_perm[2], salt_pw_perm[0]).equals("admin")) {
                    return userFactory.getUser(username, salt_pw_perm[2]);
                }
            } else {
                return null;
            }
        }
        return null;

    }

    public String checkPermissions(String hashed_permissions, String salt){
        if (hash("student", salt).equals(hashed_permissions)){
            return "student";
        }
        else if (hash("admin", salt).equals(hashed_permissions)){
            return "admin";
        }
        return null;
    }

    public HashMap<String, String[]> readDB(Path filepath){
        String delimiter = ":";
        HashMap<String, String[]> userinfo = new HashMap<>();
        File file = new File(filepath.toString());
        if (file.length() == 0) {
            return new HashMap<>();
        }
        try{
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()){
                String data = sc.nextLine();
                String[] data_split = data.split(delimiter);
                String[] salt_pw_perm = {data_split[1], data_split[2], data_split[3]};
                userinfo.put(data_split[0], salt_pw_perm);
                }
            sc.close();
            return userinfo;
            }
        catch (FileNotFoundException e){
            System.out.println("User credentials file not found");
            try {
                if (file.createNewFile()){
                    System.out.println("User credentials file created");
                                    }
                else{
                    System.out.println("User credentials file was unable to be created.");
                    e.printStackTrace();
                }
            }
            catch (IOException f){
                System.out.println("Userdata file was unable to be created");
                f.printStackTrace();
            }
            return new HashMap<>();
        }
    }


    private static String hash(String str, String salt){
        //https://www.baeldung.com/java-password-hashing
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            final byte[] hashbytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashbytes);
        }
         catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] hash) {
        //https://www.baeldung.com/sha-256-hashing-java
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] getNextSalt() {
        //todo unused here should will be used in admin for generating new users
        byte[] salt = new byte[16];
        Random RANDOM = new SecureRandom();
        RANDOM.nextBytes(salt);
        return salt;
    }

}
