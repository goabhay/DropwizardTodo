package com.msrit.dao;

import com.msrit.Entity.Student;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAO extends AbstractDAO<Student> {

    public StudentDAO(SessionFactory factory) {
        super(factory);
    }

    public Student findById(int id) {
        return get(id);
    }

    public Student create(Student student) {
        return persist(student);
    }

    public List<Student> getAllStudents() {
        return currentSession()
                .createQuery("FROM Student", Student.class)
                .getResultList();
    }

    public Student update(Student student, int id) {

        Student existingStudent = findById(id);
        if (existingStudent != null) {

            existingStudent.setName(student.getName());
            existingStudent.setAge(student.getAge());
            existingStudent.setGender(student.getGender());


            return existingStudent;
        } else {
            return null;
        }
    }

    public void delete(int id) {

        Student student = findById(id);
        if (student != null) {

            currentSession().delete(student);
        } else {
            throw new IllegalArgumentException("Student with id " + id + " not found.");
        }
    }
}
