package com.library.model;

import java.io.Serializable;

public abstract class User implements Serializable {
    protected String id;
    protected String name;
    protected String email;
    protected String password;
    protected String userType;

    public User(String id, String name, String email, String password, String userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + email + "," + password + "," + userType;
    }
}