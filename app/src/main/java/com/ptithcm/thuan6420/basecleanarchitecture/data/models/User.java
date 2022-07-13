package com.ptithcm.thuan6420.basecleanarchitecture.data.models;

import static com.ptithcm.thuan6420.basecleanarchitecture.ui.stateholders.util.Credentials.REGEX_PHONE_NUMBER;

import android.util.Patterns;

import java.util.regex.Pattern;

public class User {
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;

    public User(String email, String password, String fullName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.fullName = "";
        this.phoneNumber = "";
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isValidEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPassword() {
        return password.length() >= 9 ? true : false;
    }

    public boolean isValidPhoneNumber() {
        return Pattern.compile(REGEX_PHONE_NUMBER).matcher(phoneNumber).matches();
    }

    public boolean isValidUser() {
        return !email.isEmpty() && isValidEmail()
                && !password.isEmpty() && isValidPassword()
                && !fullName.isEmpty()
                && !phoneNumber.isEmpty() && isValidPhoneNumber();
    }

    public boolean isValidLoginUser() {
        return !email.isEmpty() && isValidEmail()
                && !password.isEmpty() && isValidPassword();
    }
}
