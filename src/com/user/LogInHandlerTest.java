package com.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LogInHandlerTest {

    @BeforeEach
    void setUp() throws IOException {
        // todo save current user credentials file and replace with default values
        Path datadir = Main.datadir;
        Path userinfopath = Paths.get(datadir.toString(), "user_cred.txt");
        File usercreds = new File(userinfopath.toString());
        String[] usernames = {"edward40hands", "coals2diamonds", "corgi", "pepsi", "Manager"};
        String[] passwords = {"correcthorseBATTERYSTAPLE", "myteamdamnrabak","hamtar0!", "c0kE123Login", "admin"};
        String[] salts = {"[B@22d8cfe0", "[B@579bb367","[B@1de0aca6","[B@255316f2","[B@41906a77"};
        String[] hashed_pw = {"df6271ddb6263f546ecca1faed0501c88ad8435f4a9c3fee22e02f775f17971e",
                              "aafa978ba90e47a0e4426b66063b4ea02dcaf32f4e29aa6ba6070ba546722125",
                              "b56b1b6550b1cfd4c5ee7c40bb966b8b2719ef73735df201a76606dd193555a5",
                              "03905aabed07b7ce74834fc248a1f4fee121db76916ca953f34cc1a0b2344ab5",
                              "35046c5b46c79bd0b2c8d21b29634dfbdde09ff85c9199349a6397b43c3d29ab"};


        if (usercreds.createNewFile()){
            System.out.println("user creds file does not exist\ncreated new user creds file");
        }

        PrintWriter writer = new PrintWriter(usercreds);
        writer.print("aidnosaidabsoidbnasoidasoidnasoidoasd");
        writer.close();



    }

    @AfterEach
    void tearDown() {
        // todo retrieve saved user credentials and save
    }


    @Test
    void readDB(){

    }

    @Test
    void hash(String str, String salt){

    }

    @Test
    void login() {
        // todo test login of pre-created usernames and passwords
    }
}