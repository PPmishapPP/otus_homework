package ru.otus.services;

public class AdminAuthServiceImpl implements AdminAuthService {

    @Override
    public boolean authenticate(String login, String password) {
        return login.equals("admin") && password.equals("password");
    }

}
