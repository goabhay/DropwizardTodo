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

    public int updateTodo(int id, String title, String description, User user) {
        Todo todo = get(id);
        if (todo == null) {
            return -1;
        }

        // Update the fields
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setUser(user);

        // Merge the updated todo object
        currentSession().merge(todo);
        return 1; // Return success status
    }

    public int deleteTodo(int id) {
        Todo todo = get(id);
        if (todo == null) {
            return -1;
        }
        // Delete the todo object
        currentSession().delete(todo);
        return 1; // Return success status
    }
}
