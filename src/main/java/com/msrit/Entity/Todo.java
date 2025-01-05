package com.msrit.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "title cann't be null")
    private String title;
    @NotNull(message = "description can't be null")
    private String description;
    private String status;


    private LocalDate startDate = LocalDate.now();

    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // To avoid recursion when serializing User object
    private User user;

    public Todo() {

    }

//    public Todo(String description, User user, String title, String status,LocalDate st, LocalDate en) {
//        System.out.println("with all params line 37");
//        this.description = description;
//        this.user = user;
//        this.title = title;
//        this.status = status;
//        this.startDate = st;
//        this.endDate = en;
//
//        System.out.println("end date " + endDate);
//    }


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

    //@JsonSetter("status")
    public void setStatus(String status) {
        if(this.status.equals("null")){
//            this.status = "WIP";
            this.status = "TODO";
        }
        else this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }

    //@JsonSetter("endDate")
    public void setEndDate(LocalDate endDate) {
       if(endDate != null) {
           this.endDate = endDate;
       }
       else {
           this.endDate = this.startDate.plusDays(10);
       }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void toggleStatus() {
//        if ("WIP".equals(this.status)) {
//            this.status = "DONE";
//        }
        if(this.status.equals("TODO")){
            this.status = "WIP";
        }
        else if(this.status.equals("WIP")){
            this.status = "DONE";
        }
        else this.status = "DONE";
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
    void create() {
        if (this.status == null) {
            this.status = "TODO";
        }
        if (this.endDate == null) {
            this.endDate = (this.startDate != null) ? this.startDate.plusDays(10) : LocalDate.now().plusDays(10);  // Set endDate to 10 days after startDate if null
        }
    }




}
