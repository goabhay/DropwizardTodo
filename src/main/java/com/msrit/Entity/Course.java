package com.msrit.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Course {

    @Id
    private int id;
    private String CourseName;
    private String CourseInstructor;

    public Course() {}
    public Course(String courseInstructor, String courseName, int id) {
        CourseInstructor = courseInstructor;
        CourseName = courseName;
        this.id = id;
    }

    public String getCourseInstructor() {
        return CourseInstructor;
    }

    public void setCourseInstructor(String courseInstructor) {
        CourseInstructor = courseInstructor;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

