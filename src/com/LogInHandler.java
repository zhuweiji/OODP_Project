package com;

import com.course.AccountData;
import com.course.CalendarController;
import com.user.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Scanner;
import java.util.*;
import java.util.concurrent.TimeUnit;

enum permissions{
    student,
    admin,
    ;
}

public class LogInHandler{

    public static ArrayList<UserAcc> accountList = AccountData.accountList;
    // Account
    public static ArrayList<UserAcc> getAccounts(){ return accountList; }
    private final Path usercredpath = Main.usercredpath;
    private static final LogInHandler instance = new LogInHandler();
    private static final StudentController studentController = StudentController.getInstance();
    private static final AdminController adminController = AdminController.getInstance();
    public static final ConsoleUserInterface cmd = ConsoleUserInterface.getInstance();
    private UserAcc[] logged_in_users;

    private LogInHandler(){ }

    public static LogInHandler startHandler(){
        return instance;
    }

    public void run(){
        String[] usercredentials;

        int attempts = 5;
        do{
            if (attempts <= 0){
                cmd.display_colored("You have entered too many wrong attempts. The program will now exit",
                        ConsoleUserInterface.ConsoleColors.RED);
                System.exit(-1);
            }

            String username = cmd.input("Enter your username: ");
            String password;
            if (cmd.consoleAvail()){
                char[] passwordArr = cmd.secretInput("Enter your password: ");
                password = new String(passwordArr);
            }
            else{
                password = cmd.input("Enter your password: ");
            }
            usercredentials = login(username,password);

            if (usercredentials != null){
                startAccessInterface(usercredentials);
            }
            else{
                cmd.display_colored("Username/Password incorrect.",
                        ConsoleUserInterface.ConsoleColors.RED);
                attempts--;
                cmd.display("");
                sleep(1);
                if (attempts < 3){
                    cmd.display("You have "+ attempts+" attempts left.");
                }

            }

        }while(usercredentials == null);

        cmd.display_colored("\nThank you for using mySTARS!", ConsoleUserInterface.ConsoleColors.GREEN_BOLD);
    }

    public String[] login(String username, String password){
        HashMap<String, String[]> userinfo = readDB(usercredpath);
        String[] id_salt_pw_perm = userinfo.get(username);

        if (id_salt_pw_perm == null){
//            System.out.println("Username was incorrect");
            return null;
        }

        String hashed_pw = hash(password, id_salt_pw_perm[1]);
        if (hashed_pw == null){
            System.out.println("No such algorithm error thrown on hash function");
            return null;
        }

        if (hashed_pw.equals(id_salt_pw_perm[2])) {
            if (checkPermissions(id_salt_pw_perm[3], id_salt_pw_perm[1]).equals(permissions.student.name()) ||
                    checkPermissions(id_salt_pw_perm[3], id_salt_pw_perm[1]).equals(permissions.admin.name())) {
                return new String[]{id_salt_pw_perm[0],
                                    username,
                                    id_salt_pw_perm[1],
                                    id_salt_pw_perm[2],
                                    id_salt_pw_perm[3]};

            }
            else {
                System.out.println("Permissions are wrong");
                return null;
            }
        }
        return null;

    }

    private void startAccessInterface(String[] usercredentials){
        String id = usercredentials[0];
        String username = usercredentials[1];
        String salt = usercredentials[2];
        String hashed_pw = usercredentials[3];
        String hashed_permission = usercredentials[4];

        String permission = checkPermissions(hashed_permission, salt);

        if (permission.equals(permissions.student.name())){
            Student logged_in_user = studentController.getExistingStudent(username, hashed_pw, salt, id);

            Calendar starttime = logged_in_user.getAccessStart();
            Calendar endtime = logged_in_user.getAccessEnd();

            Timer time = new Timer();
            TAccessPeriodHandler AP = new TAccessPeriodHandler(starttime, endtime);
            time.schedule(AP, 0,20000);

            StudentInterface studentInterface = StudentInterface.getInstance(username, hashed_pw, salt, id);

            studentInterface.run();
        }
        else if (permission.equals(permissions.admin.name())){
            AdminInterface adminInterface = AdminInterface.getInstance(username, hashed_pw, salt, id);
            adminInterface.run();
        }

    }

    public static UserAcc compareUserPass(String username, String passwordToBeHash)
    {
        String salt;
        ArrayList<UserAcc> accountList = getAccounts();
        String securePassword;

        for (UserAcc userAcc : accountList) {

            //create user object to iterate
            UserAcc user = (UserAcc) userAcc;

            //retrieve salt from text data
            salt = user.getSalt();

            //hash user password input with salt
            securePassword = hash(passwordToBeHash, salt);

            //compare user input hash with hash retrieved from text data
            if (username.toLowerCase().equals(user.getUsername().toLowerCase()) && securePassword.equals(user.getPassword())) {
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
        return "none";
    }


    public static LinkedHashMap<String, String[]> readDB(Path filepath){
        String delimiter = ",";
        LinkedHashMap<String, String[]> userinfo = new LinkedHashMap<>();

        File file = new File(filepath.toString());
        if (file.length() == 0) {
            return new LinkedHashMap<>();
        }
        try{
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()){
                String data = sc.nextLine();
                if (data.trim().isEmpty()){
                    break;
                }
                String[] data_split = data.split(delimiter);
                String userid = data_split[0];
                String username = data_split[1];
                String[] id_salt_pw_perm = {userid, data_split[2], data_split[3], data_split[4]};
                userinfo.put(username, id_salt_pw_perm);
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
            return new LinkedHashMap<>();
        }
    }


    public static String hash(String str, String salt){
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
        byte[] salt = new byte[16];
        Random RANDOM = new SecureRandom();
        RANDOM.nextBytes(salt);
        return salt;
    }
    private void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class TAccessPeriodHandler extends TimerTask {
        Calendar accessStart;
        Calendar accessEnd;
        ConsoleUserInterface cmd = ConsoleUserInterface.getInstance();

        TAccessPeriodHandler(Calendar accessStart, Calendar accessEnd) {
            cmd.display("");
            cmd.display_colored("Access Period Poller running (for demonstration purposes)", ConsoleUserInterface.ConsoleColors.BLACK_BOLD);
            try {
                cmd.display("This student's access period is from "+accessStart.getTime());
                cmd.display("to "+accessEnd.getTime());
                this.setAccessStart(accessStart);
                this.setAccessEnd(accessEnd);
            } catch (IllegalArgumentException e) {
                cmd.display("Access period for this student has error");
                cmd.display("Please contact administrator/ Check database");
                e.printStackTrace();
            }
        }

        public void run() {
            check();
        }

        public void check() {
            System.out.println();
            cmd.display_colored("-------------------------------------------------\n" +
                    "Checking access time (For demonstration purposes)\n" +
                    "--------------------------------------------------\r", ConsoleUserInterface.ConsoleColors.WHITE_BOLD);
            System.out.println("");
            Calendar currentTime = Calendar.getInstance();
            if (accessStart.compareTo(currentTime) > 0) {
                System.out.println("You are trying to access the program before your access period");
                cmd.displayf("Your access period is from {} to {}", new String[]{CalendarController.caltoString(accessStart),
                        CalendarController.caltoString(accessEnd)});
                System.exit(0);
            } else if (accessEnd.compareTo(currentTime) < 0) {
                System.out.println("You are trying to access the program after your access period");
                cmd.displayf("Your access period is from {} to {}", new String[]{CalendarController.caltoString(accessStart),
                        CalendarController.caltoString(accessEnd)});
                System.exit(0);
            }

        }


        public Calendar getAccessStart() {
            return accessStart;
        }

        public String getAccessStartStr() {
            return CalendarController.caltoString(accessStart);
        }

        public void setAccessStart(Calendar accessStart) {
            if (accessEnd != null) {
                if (accessStart.compareTo(accessEnd) > 0) {
                    throw new IllegalArgumentException("Access start date cannot be later than end date");
                }
            }
            this.accessStart = accessStart;
        }

        public Calendar getAccessEnd() {
            return accessEnd;
        }

        public String getAccessEndStr() {
            return CalendarController.caltoString(accessEnd);
        }

        public void setAccessEnd(Calendar accessEnd) {
            if (accessStart != null) {
                if (accessEnd.compareTo(accessStart) < 0) {
                    throw new IllegalArgumentException("Access end date cannot be earlier than start date");
                }
            }
            this.accessEnd = accessEnd;
        }
    }
}
