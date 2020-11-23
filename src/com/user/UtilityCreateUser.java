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


public class UtilityCreateUser {
    public static void main(String[] args) {
        String[] usernames = {"edward40hands", "coals2diamonds", "corgi", "pepsi", "Manager"};
        String[] passwords = {"correcthorseBATTERYSTAPLE", "myteamdamnrabak", "hamtar0!", "c0kE123Login", "admin"};
        String[] salts = {"[B@22d8cfe0", "[B@579bb367", "[B@1de0aca6", "[B@255316f2", "[B@41906a77"};
        String[] hashed_pw = {"df6271ddb6263f546ecca1faed0501c88ad8435f4a9c3fee22e02f775f17971e",
                "aafa978ba90e47a0e4426b66063b4ea02dcaf32f4e29aa6ba6070ba546722125",
                "b56b1b6550b1cfd4c5ee7c40bb966b8b2719ef73735df201a76606dd193555a5",
                "03905aabed07b7ce74834fc248a1f4fee121db76916ca953f34cc1a0b2344ab5",
                "35046c5b46c79bd0b2c8d21b29634dfbdde09ff85c9199349a6397b43c3d29ab"};
        String[] permissions = {"student","student","student","student","admin"};
//        for (int i = 0; i < 5; i++) {
//            String perm_hash = hash(permissions[i], salts[i]);
//            System.out.printf("%s:%s:%s:%s\n", usernames[i], salts[i], hashed_pw[i],perm_hash);
//        }
        System.out.println(hash(passwords[4], salts[4]));
        System.out.println(hash(permissions[4], salts[4]));
        String salt = LogInHandler.getNextSalt().toString();
        System.out.println("student");
        System.out.println(salt);
        System.out.println(hash("pw",salt));;
        System.out.println(hash("student", salt));

    }

    private static String hash(String str, String salt) {
        //https://www.baeldung.com/java-password-hashing
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            final byte[] hashbytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashbytes);
        } catch (NoSuchAlgorithmException e) {
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
}