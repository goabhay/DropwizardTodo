package com.msrit.resources;

import com.msrit.Entity.Todo;
import com.msrit.dao.TodoDAO;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/todo")
public class TodoResources {

    private final TodoDAO todoDao;

    public TodoResources(TodoDAO todoDao) {
        this.todoDao = todoDao;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @UnitOfWork
    public Response getTodo(@PathParam("id") int id) {
        Todo t = todoDao.getTodo(id);
        if (t == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No todo found").build();
        }
        return Response.ok(t).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveTodo(Todo todo) {
        Todo createdTodo = todoDao.createTodo(todo);
        return Response.status(Response.Status.CREATED).entity(createdTodo).build();
    }
}
