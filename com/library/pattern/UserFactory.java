package com.library.pattern;

import com.library.model.Admin;
import com.library.model.Reader;
import com.library.model.User;

import java.util.UUID;

public class UserFactory {
    public static User createUser(String name, String email, String password, String userType) {
        String id = UUID.randomUUID().toString();
        
        if (userType.equalsIgnoreCase("ADMIN")) {
            return new Admin(id, name, email, password);
        } else if (userType.equalsIgnoreCase("READER")) {
            return new Reader(id, name, email, password);
        }
        
        return null;
    }
}