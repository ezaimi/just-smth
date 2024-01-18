package com.example.kthimi.Model;

public class AuthenticationModel {
    private static final String LIBRARIAN_USERNAME = "1";
    private static final String LIBRARIAN_PASSWORD = "11";
    private static final String MANAGER_USERNAME = "2";
    private static final String MANAGER_PASSWORD = "22";
    private static final String ADMIN_USERNAME = "3";
    private static final String ADMIN_PASSWORD = "33";

    public boolean authenticateLibrarian(String username, String password) {
        return LIBRARIAN_USERNAME.equals(username) && LIBRARIAN_PASSWORD.equals(password);
    }

    public boolean authenticateManager(String username, String password) {
        return MANAGER_USERNAME.equals(username) && MANAGER_PASSWORD.equals(password);
    }

    public boolean authenticateAdmin(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
}
