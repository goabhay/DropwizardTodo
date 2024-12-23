package com.msrit.resources;

import com.msrit.Entity.Todo;
import com.msrit.dao.TodoDAO;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

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


    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveTodo(Todo todo) {
        Todo createdTodo = todoDao.createTodo(todo);
        return Response.status(Response.Status.CREATED).entity(createdTodo).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{tid}")
    @UnitOfWork
    public Response updateTodo(@PathParam("tid") int tid, Todo todo) {
        // Fetch the existing Todo object
        Todo existingTodo = todoDao.getTodo(tid);
        if (existingTodo == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Todo not found with id: " + tid)
                    .build();
        }

        // Update fields only if provided
        if (todo.getDescription() != null && !todo.getDescription().isEmpty()) {
            existingTodo.setDescription(todo.getDescription());
        }
        if (todo.getTitle() != null && !todo.getTitle().isEmpty()) {
            existingTodo.setTitle(todo.getTitle());
        }
        if (todo.getUser() != null) {
            existingTodo.setUser(todo.getUser());
        }

        // Save the updated Todo entity
        todoDao.updateTodo(tid, existingTodo);

        return Response.status(Response.Status.OK)
                .entity(existingTodo)
                .build();
    }

    @GET
    @Path("/allTodo")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTodo() {
        List<Todo> todos = todoDao.getAllTodo();
        return Response.ok("Success").entity(todos).build();
    }

    @GET
    @Path("/toggle/{id}")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response toggleTask(@PathParam("id") int id){
        todoDao.toggleTask(id);
        return Response.status(Response.Status.OK).build();
    }




}
