package com.msrit.dao;

import com.msrit.Entity.Todo;
import com.msrit.Entity.User;
import io.dropwizard.hibernate.AbstractDAO;
import jakarta.inject.Singleton;
import jakarta.persistence.Query;
import org.hibernate.SessionFactory;

public class TodoDAO extends AbstractDAO<Todo> {

    public TodoDAO(SessionFactory session) {
        super(session);
    }

    public Todo getTodo(int id) {
        return get(id);
    }

    public Todo createTodo(Todo todo) {
        persist(todo);
        return todo;
    }

    public int updateTodo(int id, Todo newTodoData) {
        Todo existingTodo = get(id);
        if (existingTodo == null) {
            return -1; // Indicate that the Todo was not found
        }

        // Update the fields with the new data
        existingTodo.setTitle(newTodoData.getTitle());
        existingTodo.setDescription(newTodoData.getDescription());
        existingTodo.setUser(newTodoData.getUser());

        // Merge the updated Todo object to persist changes
        currentSession().merge(existingTodo);
        return 1; // Indicate success
    }


    public int deleteTodo(int id) {
        Todo todo = get(id);
        if (todo == null) {
            return -1;
        }
        // Delete the todo object
        currentSession().delete(todo);
        return 1;
    }


}
