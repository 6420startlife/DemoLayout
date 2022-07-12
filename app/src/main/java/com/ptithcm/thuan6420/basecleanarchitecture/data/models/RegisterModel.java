package com.ptithcm.thuan6420.basecleanarchitecture.data.models;

import static com.ptithcm.thuan6420.basecleanarchitecture.ui.stateholders.util.Credentials.REGEX_PHONE_NUMBER;

import android.util.Patterns;

import java.util.regex.Pattern;

public class RegisterModel {
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;

    public RegisterModel(String email, String password, String fullName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
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

    public boolean isValidEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPassword() {
        return password.length() >= 9 ? true : false;
    }

    public boolean isValidPhoneNumber() {
        return Pattern.compile(REGEX_PHONE_NUMBER).matcher(phoneNumber).matches();
    }
}
