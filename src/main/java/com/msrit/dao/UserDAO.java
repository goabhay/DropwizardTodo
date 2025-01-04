package com.msrit.dao;

import com.msrit.Entity.Todo;
import com.msrit.Entity.User;
import io.dropwizard.hibernate.AbstractDAO;
import jakarta.persistence.Query;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void save(User user) {
        persist(user);
    }

    public User updateUser(User user, int id) {
        User u = get(id);
        if (u != null) {
            u.setName(user.getName());
            u.setTodos(user.getTodos());
            currentSession().saveOrUpdate(u);
            return u;
        }
        return null;
    }

    // Delete User
    public boolean deleteUser(int id) {
        User u = get(id);
        if (u != null) {
            currentSession().delete(u);
            return true;
        }
        return false;
    }

    public User getUser(int id) {
        return get(id);
    }

    public List<User> getAllUser() {
        Query q = currentSession().createQuery("from User", User.class);
        return q.getResultList();
    }

    public User addTodoToUser(User user, Todo todo) {
        user.addTodo(todo);
        save(user);  // Saving the User will also save the Todo due to cascading
        return user;
    }

    public List<Todo> pendingTodos(int uid) {
        Query q = currentSession().createQuery(
                "select t from User u join u.todos t where u.id = :uid and t.status = :status",
                Todo.class
        );
        q.setParameter("uid", uid);
        q.setParameter("status", "WIP");
        return q.getResultList();
    }


}
