package com.user;

import com.Main;

import com.course.Course;
import com.course.Index;
import com.course.CourseData;
import com.course.IndexData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.ArrayList;

public class CourseIndexController {

    private final Path courseinfopath = Main.courseinfopath;
    private final Path indexinfopath = Main.indexinfopath;
    private HashMap<String, Course> courseinfoDB = new HashMap<>();
    private HashMap<Integer, Index> indexinfoDB = null;

    //Singleton
    private static final CourseIndexController instance = new CourseIndexController();

    public static CourseIndexController getInstance(){
        return instance;
    }

    public CourseIndexController() {
        refreshCourseDB();
    }

    public void pushDB(Path filepath, String[] data, String delimiter, boolean append) throws IOException {
        // appends line
        File file = new File(filepath.toString());
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
        writer.newLine();
        int count = 0;
        for (String item : data) {
            count++;
            writer.write(item);
            if (count != data.length) {
                writer.write(delimiter);
            }
        }
        writer.close();
    }

    public int addCourse(Course newCourse) {
        // Refresh database
        refreshCourseDB();
        if (courseinfoDB.containsKey(newCourse.getCourseID()))
        {
            System.out.println("Course ID " + newCourse.getCourseID() + " already exists!");
            return 1;
        }
        // Save to container
        courseinfoDB.put(newCourse.getCourseID(), newCourse);
        // Update database
        String[] data = newCourse.getAllDetails();
        try {
            pushDB(courseinfopath, data, "|", true);
        } catch (Exception e) {
            System.out.println("Add Course encountered exception - " + e.getMessage());
            return -1;
        }
        return 0;
    }

    public int updateCourse(Course course, String courseID) {
        // Refresh database
        refreshCourseDB();
        if (courseinfoDB.get(courseID) == null)
        {
            System.out.println("Unable to find course ID in existing database.");
            return 1;
        }
        // In-place update
        courseinfoDB.put(courseID, course);

        // Update database
        ArrayList<Course> arrayData = new ArrayList<Course>(courseinfoDB.values());
        try {
            CourseData.saveCourses(arrayData);
        } catch (Exception e) {
            System.out.println("Update Course encountered exception - " + e.getMessage());
            return -1;
        }
        return 0;
    }

    public int removeCourse(String courseID) {
        // Refresh database
        refreshCourseDB();
        if (courseinfoDB.get(courseID) == null)
        {
            System.out.println("Unable to find course ID in existing database.");
            return 1;
        }
        // Remove
        courseinfoDB.remove(courseID);

        // Update database
        ArrayList<Course> arrayData = new ArrayList<Course>(courseinfoDB.values());
        try {
            CourseData.saveCourses(arrayData);
        } catch (Exception e) {
            System.out.println("Remove Course encountered exception - " + e.getMessage());
            return -1;
        }
        return 0;
    }

    public void refreshCourseDB() {
        ArrayList<Course> newData = null;
        try {
            newData = CourseData.initCourses();
        } catch (Exception e) {
            System.out.println("Refresh course database encountered exception - " + e.getMessage());
            return;
        }
        // Convert ArrayList -> HashMap
        HashMap<String, Course> newCourseinfoDB = new HashMap<String, Course>();
        for (Course course : newData)
            newCourseinfoDB.put(course.getCourseID(), course);
        // Replace our existing HashMap
        courseinfoDB = newCourseinfoDB;
    }

    public ArrayList<Course> getCourseinfoDB() {
        refreshCourseDB();
        return new ArrayList<Course>(courseinfoDB.values());
    }

    public ArrayList<Index> getIndexinfoDB() {
        refreshIndexDB();
        return new ArrayList<Index>(indexinfoDB.values());
    }

    public void refreshIndexDB() {
        ArrayList<Index> newIndex = null;
        try {
            newIndex = IndexData.initIndex();
        } catch (Exception e) {
            System.out.println("Refresh index database encountered exception - " + e.getMessage());
            return;
        }
        // Convert ArrayList -> HashMap
        HashMap<Integer, Index> newIndexinfoDB = new HashMap<>();
        for (Index index : newIndex)
            newIndexinfoDB.put(index.getIndexID(), index);
        // Replace our existing HashMap
        indexinfoDB = newIndexinfoDB;
    }

    public int addIndex(Index newIndex) {
        // Refresh database
        refreshIndexDB();
        if (indexinfoDB.containsKey(newIndex.getCourseID()))
        {
            System.out.println("Index ID " + newIndex.getCourseID() + " already exists!");
            return 1;
        }
        // Save to container
        indexinfoDB.put(newIndex.getIndexID(), newIndex);
        // Update database
        String[] data = newIndex.getAllDetails();
        try {
            pushDB(indexinfopath, data, "|", true);
        } catch (Exception e) {
            System.out.println("Add Index encountered exception - " + e.getMessage());
            return -1;
        }
        return 0;
    }

    public int updateIndex(Index index, int indexID) {
        // Refresh database
        refreshIndexDB();
        if (indexinfoDB.get(indexID) == null)
        {
            System.out.println("Unable to find course ID in existing database.");
            return 1;
        }
        // In-place update
        indexinfoDB.put(indexID, index);

        // Update database
        ArrayList<Index> arrayData = new ArrayList<Index>(indexinfoDB.values());
        try {
            IndexData.saveIndexes(arrayData);
        } catch (Exception e) {
            System.out.println("Update Index encountered exception - " + e.getMessage());
            return -1;
        }
        return 0;
    }

    public int removeIndex(int indexID) {
        // Refresh database
        refreshIndexDB();
        if (indexinfoDB.get(indexID) == null) {
            System.out.println("Unable to find index ID in existing database.");
            return 1;
        }
        // Remove
        indexinfoDB.remove(indexID);

        // Update database
        ArrayList<Index> arrayData = new ArrayList<Index>(indexinfoDB.values());
        try {
            IndexData.saveIndexes(arrayData);
        } catch (Exception e) {
            System.out.println("Remove Index encountered exception - " + e.getMessage());
            return -1;
        }
        return 0;
    }

}
