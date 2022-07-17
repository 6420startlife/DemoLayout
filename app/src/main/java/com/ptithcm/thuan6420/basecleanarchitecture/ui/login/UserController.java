package com.ptithcm.thuan6420.basecleanarchitecture.ui.login;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;

import com.ptithcm.thuan6420.basecleanarchitecture.R;
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository;
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.ErrorDialog;
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.SuccessDialog;


public class UserController {
    private User model;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private ErrorDialog errorDialog;
    private SuccessDialog successDialog;

    public UserController(LoginFragment loginFragment) {
        this.model = new User();
        this.loginFragment = loginFragment;
    }

    public UserController(RegisterFragment registerFragment) {
        this.model = new User();
        this.registerFragment = registerFragment;
    }

    public void setUserEmail(String userEmail) {
        model.setEmail(userEmail);
    }

    public String getUserEmail() {
        return model.getEmail();
    }

    public void setUserPassword(String userPassword)  {
        model.setPassword(userPassword);
    }

    public String getUserPassword() {
        return model.getPassword();
    }

    public void setUserFullName(String userFullName) {
        model.setFullName(userFullName);
    }

    public String getUserFullName() {
        return model.getFullName();
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        model.setPhoneNumber(userPhoneNumber);
    }


    public void handleOnClick(View view) {
        int id = view.getId();

        if (id == R.id.tvToRegister) {
            loginFragment.navigateToRegisterFragment();
        } else if (id == R.id.btnLogin) {
            loginFragment.setEnabledButton(false);
            login();
        } else if (id == R.id.layoutLoginFragment) {
            closeKeyBoard(loginFragment.requireActivity());
        }

        if (id == R.id.tvToLogin) {
            registerFragment.navigateToLoginFragment();
        } else if (id == R.id.btnRegister) {
            register();
        } else if (id == R.id.layoutRegisterFragment) {
            closeKeyBoard(registerFragment.requireActivity());
        }
    }

    private void login() {
        closeKeyBoard(loginFragment.requireActivity());
        loginFragment.turnOnLoading();
        clearErrorMessageLogin();
        loginFragment.getValue();
        if (model.isValidLoginUser() && UserRepository.isMatchedUser(model)) {
            showSuccessAlertDialog(loginFragment, "Login successfully");
            loginFragment.turnOffLoading();
            return;
        }
        if (model.isValidLoginUser()) {
            showErrorAlertDialog(loginFragment, "Login failed. Please register.");
            loginFragment.turnOffLoading();
            return;
        }

        loginFragment.turnOffLoading();

        if (model.getEmail().isEmpty()) {
            loginFragment.showErrorEmail("Email not null");
        } else if (!model.isValidEmail()) {
            loginFragment.showErrorEmail("Email is invalid");
        } else {
            loginFragment.showErrorEmail(null);
        }

        if (model.getPassword().isEmpty()) {
            loginFragment.showErrorPassword("Password not null");
        } else if (!model.isValidPassword()) {
            loginFragment.showErrorPassword("Enter at least 9 characters");
        } else {
            loginFragment.showErrorPassword(null);
        }
        loginFragment.setEnabledButton(true);
    }

    private void clearErrorMessageLogin() {
        loginFragment.showErrorEmail(null);
        loginFragment.showErrorPassword(null);
    }

    private void clearErrorMessageRegister() {
        registerFragment.showErrorEmail(null);
        registerFragment.showErrorPassword(null);
        registerFragment.showErrorFullName(null);
        registerFragment.showErrorPhoneNumber(null);
    }

    private void register() {
        closeKeyBoard(registerFragment.requireActivity());
        registerFragment.turnOnLoading();
        clearErrorMessageRegister();
        registerFragment.getValue();
        if (UserRepository.isExistedUser(model)) {
            registerFragment.turnOffLoading();
            showErrorAlertDialog(registerFragment,"Email already exists");
            return;
        }
        if (model.isValidUser()) {
            UserRepository.createUser(model);
            registerFragment.turnOffLoading();
            showSuccessAlertDialog(registerFragment, "Register successfully");
            return;
        }
        registerFragment.turnOffLoading();

        if (model.getEmail().isEmpty()) {
            registerFragment.showErrorEmail("Email not null");
        } else if (!model.isValidEmail()) {
            registerFragment.showErrorEmail("Email is invalid");
        } else {
            registerFragment.showErrorEmail(null);
        }

        if (model.getPassword().isEmpty()) {
            registerFragment.showErrorPassword("Password not null");
        } else if (!model.isValidPassword()) {
            registerFragment.showErrorPassword("Enter at least 9 characters");
        } else {
            registerFragment.showErrorPassword(null);
        }

        if (model.getFullName().isEmpty()) {
            registerFragment.showErrorFullName("Full name not null");
        } else {
            registerFragment.showErrorFullName(null);
        }

        if (model.getPassword().isEmpty()) {
            registerFragment.showErrorPhoneNumber("Phone number not null");
        } else if (!model.isValidPhoneNumber()) {
            registerFragment.showErrorPhoneNumber("Phone number must be 10 numbers and start by 0");
        } else {
            registerFragment.showErrorPhoneNumber(null);
        }
        registerFragment.setEnabledButton(true);
    }

    private void showErrorAlertDialog(Fragment fragment, String message) {
        if (fragment instanceof LoginFragment) {
            errorDialog = new ErrorDialog(fragment.getContext(),
                    id -> {
                        loginFragment.setEnabledButton(true);
                    }, message);
        } else {
            errorDialog = new ErrorDialog(fragment.getContext(),
                    id -> {
                        registerFragment.setEnabledButton(true);
                    }, message);
        }
        errorDialog.show();
    }

    private void showSuccessAlertDialog(Fragment fragment, String message) {
        if (fragment instanceof LoginFragment) {
            successDialog= new SuccessDialog(fragment.getContext(),
                    id -> loginFragment.navigateToMainActivity(), message);
            loginFragment.setEnabledButton(true);
        } else {
            successDialog= new SuccessDialog(fragment.getContext(),
                    id -> registerFragment.navigateToLoginFragment(), message);
            registerFragment.setEnabledButton(true);
        }
        successDialog.show();
    }

    public void closeKeyBoard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
