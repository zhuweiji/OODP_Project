package com.user;

import java.util.List;

public class Course {
    private String courseName;
    private String courseID;
    private String school;
    private String totalCapacity;
    private String acadUnits;
    private List<Index> indexes;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(String totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public String getAcadUnits() {
        return acadUnits;
    }

    public void setAcadUnits(String acadUnits) {
        this.acadUnits = acadUnits;
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }
}

class Index{

}