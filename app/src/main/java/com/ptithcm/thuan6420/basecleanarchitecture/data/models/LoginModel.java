package com.ptithcm.thuan6420.basecleanarchitecture.data.models;

import android.util.Patterns;

public class LoginModel {
    private String email;
    private String password;

    public LoginModel(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isValidEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPassword() {
        return password.length() >= 9 ? true : false;
    }
}
