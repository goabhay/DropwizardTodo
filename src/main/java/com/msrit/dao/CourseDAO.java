package com.msrit.dao;

import com.msrit.Entity.Course;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class CourseDAO extends AbstractDAO<Course> {

    public CourseDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Course findById(int id) {
        return get(id);
    }

    public Course save(Course course) {
        return persist(course);
    }
}
