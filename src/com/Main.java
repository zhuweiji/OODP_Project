package com;

import com.ConsoleUserInterface;
import com.user.LogInHandler;
import com.user.TAccessPeriodHandler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.*;
import java.util.Timer;
import java.io.*;
import com.user.*;
import com.course.*;

public class Main {
    public static final Path datadir = Paths.get(System.getProperty("user.dir"), "data");
    public static final Path usercredpath = Paths.get(datadir.toString(), "user_cred.txt");
    public static final Path studentinfopath = Paths.get(datadir.toString(), "student_info.txt");
    public static final Path admininfopath = Paths.get(datadir.toString(), "admin_info.txt");
    public static final Path courseinfopath = Paths.get(datadir.toString(), "Course.txt");
    public static final Path indexinfopath = Paths.get(datadir.toString(), "Index.txt");
    public static void main(String[] args)throws ParseException, IOException {
        
        StudentData.initStudents();
		CourseData.initCourses();
		IndexData.initIndex();
		LessonData.initLessons();
		//StudentCourseData.initStudentCourses();
        //AccountData.initAccounts();
        
        LogInHandler loginhandler = LogInHandler.startHandler();

        loginhandler.run();
    }
}

