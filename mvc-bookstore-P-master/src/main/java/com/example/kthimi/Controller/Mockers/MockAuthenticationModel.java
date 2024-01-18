package com.example.kthimi.Controller.Mockers;

import com.example.kthimi.Model.AuthenticationModel;

public class MockAuthenticationModel extends AuthenticationModel {
    @Override
    public boolean authenticateLibrarian(String username, String password) {

        return "1".equals(username) && "11".equals(password);
    }

    @Override
    public boolean authenticateManager(String username, String password) {

        return "2".equals(username) && "22".equals(password);
    }

    @Override
    public boolean authenticateAdmin(String username, String password) {

        return "3".equals(username) && "33".equals(password);
    }
}
