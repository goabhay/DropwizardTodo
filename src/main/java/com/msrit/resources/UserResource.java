package com.msrit.resources;

import com.msrit.Entity.Todo;
import com.msrit.Entity.User;
import com.msrit.dao.TodoDAO;
import com.msrit.dao.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.*;

@Path("/user")
public class UserResource {

    private final UserDAO userDao;
    private final TodoDAO todoDao; // Two DAO's can be passed while registering the resources


    public UserResource(UserDAO userDao, TodoDAO todoDao) {
        this.userDao = userDao;
        this.todoDao = todoDao;
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
        User reqUser = userDao.getUser(id);
        if (reqUser == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found")
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(reqUser)
                .build();
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user, @PathParam("id") int id) {
        User updatedUser = userDao.updateUser(user, id);
        if (updatedUser == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found or update failed")
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(updatedUser)
                .build();
    }

    @GET
    @Path("/allUser")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUser() {
        List<User> users = userDao.getAllUser();
        if (users.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build(); // 204 No Content if no users found
        }
        return Response.status(Response.Status.OK)
                .entity(users)
                .build();
    }

    @POST
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        userDao.save(user);
        return Response.status(Response.Status.CREATED)
                .entity(user)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") int id) {
        boolean deleted = userDao.deleteUser(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found")
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity("User successfully deleted")
                .build();
    }

    @POST
    @Path("/{userId}/todos")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTodo(@PathParam("userId") int userId, Todo todo) {
        User user = userDao.getUser(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found with id: " + userId)
                    .build();
        }
        todo.setUser(user);
        userDao.addTodoToUser(user, todo); // Save the user and cascade todo
        return Response.status(Response.Status.CREATED)
                .entity(todo) // Return the created todo
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/getAllTodo")
    @UnitOfWork
    public Response getAllTodo(@PathParam("id") int uid) {
        User u = userDao.getUser(uid);
        if (u == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found with id: " + uid)
                    .build();
        }
        List<Todo> td = u.getTodos();
        return Response.status(Response.Status.OK)
                .entity(td)
                .build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Path("/{id}/deleteTodo")
    public Response deleteTodo(@PathParam("id") int tid) {
        todoDao.deleteTodo(tid);

        return Response.status(Response.Status.OK)
                .entity("Todo successfully deleted")
                .build();
    }
}
 // i am going to add some function
