package com.msrit.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;

    //@Column(nullable = false)
    private String status;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // To avoid recursion when serializing User object
    private User user;


    public Todo() {

    }

    public Todo(String description, User user, String title) {
        this.description = description;
        this.user = user;
        this.title = title;

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }


    @JsonSetter
    public void setStatus(String status) {
        if (status != null) {
            this.status = status;
        } else {
            this.status = "DONE"; // Default to DONE if status is null
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    @JsonSetter
    public void setStartDate(LocalDate startDate) {
        if (startDate != null) {
            this.startDate = startDate;
        } else {
            this.startDate = LocalDate.now();
        }
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @JsonSetter
    public void setEndDate(LocalDate endDate) {
        if (endDate != null) {
            this.endDate = endDate;
        } else {
            this.endDate = LocalDate.now().plusDays(10);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void toggleStatus() {
        if (this.status.equals("WIP")) {
            this.status = "DONE";
        }
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", user=" + user +
                '}';
    }


    @PrePersist
    protected void onCreate() {
        if (this.startDate == null) {
            this.startDate = LocalDate.now();
        }
        if (this.endDate == null) {
            this.endDate = LocalDate.now().plusDays(10);
        }
    }
}
