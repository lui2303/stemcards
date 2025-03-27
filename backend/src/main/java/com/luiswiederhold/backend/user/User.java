package com.luiswiederhold.backend.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    // TODO: add all user paths to the user database and make them queriable
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String password;
    @Column
    private String surname;
    @Column
    private String lastname;

    public User(String email, String password, String surname, String lastname) {
        this.email = email;
        this.password = password;
        this.surname = surname;
        this.lastname = lastname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
