package com.msrit.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//      (either use @JsonManagedRefrence and @JsonBackRefrence or use @JsonIgnore)
    // @JsonManagedReference
    private List<Todo> todos = new ArrayList<>();

    public User() {}

    public User(String name, List<Todo> todos) {
        this.name = name;
        this.todos = todos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addTodo(Todo todo) {
        todos.add(todo);
        todo.setUser(this);
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", todos=" + todos +
//                '}';
//    }
}


