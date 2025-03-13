package com.luiswiederhold.backend.user;

import jakarta.persistence.*;

@Entity
@Table(name = "usertable")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private User() {}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
