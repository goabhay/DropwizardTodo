package com.msrit.resources;

import com.msrit.Entity.Student;
import com.msrit.dao.StudentDAO;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/student")
public class StudentResource {

    private final StudentDAO studentDao;

    // Constructor
    public StudentResource(StudentDAO studentDAO) {
        this.studentDao = studentDAO;
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response getStudentById(@PathParam("id") int id) {
        Student stu = studentDao.findById(id);
        if (stu == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(stu).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response createStudent(Student student) {
        Student stu = studentDao.create(student);
        return Response.status(Response.Status.CREATED).entity(stu).build(); // Return the created student
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getAllStudents() {
        List<Student> allStudents = studentDao.getAllStudents();
        if (allStudents.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No students found.")
                    .build();
        }
        return Response.ok(allStudents).build();
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStudent(Student student, @PathParam("id") int id) {
        Student existingStudent = studentDao.findById(id);
        if (existingStudent == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Student not found.")
                    .build();
        }
        Student updatedStudent = studentDao.update(student, id);
        return Response.ok(updatedStudent).build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response deleteStudent(@PathParam("id") int id) {
        Student stu = studentDao.findById(id);
        if (stu == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        studentDao.delete(id);
        return Response.noContent().build();  // Use noContent for successful delete
    }
}
