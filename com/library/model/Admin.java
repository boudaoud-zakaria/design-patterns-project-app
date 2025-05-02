package com.library.model;

public class Admin extends User {
    public Admin(String id, String name, String email, String password) {
        super(id, name, email, password, "ADMIN");
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
}