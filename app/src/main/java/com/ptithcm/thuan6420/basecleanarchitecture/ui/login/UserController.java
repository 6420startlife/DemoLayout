package com.ptithcm.thuan6420.basecleanarchitecture.ui.login;

import android.view.View;

import com.ptithcm.thuan6420.basecleanarchitecture.R;
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository;
import com.ptithcm.thuan6420.basecleanarchitecture.ui.components.UiController;
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.DialogController;
import com.ptithcm.thuan6420.basecleanarchitecture.ui.util.ConstantMessage;


public class UserController {
    private User model;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

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

    public void setUserPassword(String userPassword) {
        model.setPassword(userPassword);
    }

    public void setUserFullName(String userFullName) {
        model.setFullName(userFullName);
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        model.setPhoneNumber(userPhoneNumber);
    }

    /**
     * Xử lý những thao tác sau khi nhận view.getId() từ View.onClickListener
     */
    public void handleOnClick(View view) {
        int id = view.getId();

        if (id == R.id.tvToRegister) {
            loginFragment.navigateToRegisterFragment();
        } else if (id == R.id.btnLogin) {
            loginFragment.setEnabledButton(false);
            login();
        } else if (id == R.id.layoutLoginFragment) {
            UiController.getInstance().closeKeyBoard(loginFragment.requireActivity());
        }

        if (id == R.id.tvToLogin) {
            registerFragment.navigateToLoginFragment();
        } else if (id == R.id.btnRegister) {
            register();
        } else if (id == R.id.layoutRegisterFragment) {
            UiController.getInstance().closeKeyBoard(registerFragment.requireActivity());
        }
    }

    /**
     * Lấy dữ liệu từ view và kiểm tra xem tài khoản người dùng cung cấp từ view có hợp lệ
     * hay không ? Nếu hợp lệ thì chuyển sang Trang làm việc chính, không thì hiển thị thông báo
     * đăng nhập thất bại
     */
    private void login() {
        UiController.getInstance().closeKeyBoard(loginFragment.requireActivity());
        UiController.getInstance().initProgressDialog(loginFragment.getContext());
        UiController.getInstance().turnOnLoading(loginFragment.getContext());
        clearErrorMessageLogin();
        loginFragment.getValue();
        if (model.isValidLoginUser() && UserRepository.isMatchedUser(model)) {
            DialogController.getInstance().showSuccessDialog(loginFragment.getContext(), id -> {
                loginFragment.navigateToMainActivity();
                loginFragment.setEnabledButton(true);
            }, ConstantMessage.MESSAGE_SUCCESS_LOGIN);
            UiController.getInstance().turnOffLoading();
            return;
        }
        if (model.isValidLoginUser()) {
            DialogController.getInstance().showErrorDialog(loginFragment.getContext(),
                    id -> loginFragment.setEnabledButton(true),
                    ConstantMessage.MESSAGE_ERROR_LOGIN);
            UiController.getInstance().turnOffLoading();
            return;
        }

        UiController.getInstance().turnOffLoading();

        if (model.getEmail().isEmpty()) {
            loginFragment.showErrorEmail(ConstantMessage.MESSAGE_EMPTY_EMAIL);
        } else if (!model.isValidEmail()) {
            loginFragment.showErrorEmail(ConstantMessage.MESSAGE_INVALID_EMAIL);
        } else {
            loginFragment.showErrorEmail(null);
        }

        if (model.getPassword().isEmpty()) {
            loginFragment.showErrorPassword(ConstantMessage.MESSAGE_EMPTY_PASSWORD);
        } else if (!model.isValidPassword()) {
            loginFragment.showErrorPassword(ConstantMessage.MESSAGE_INVALID_PASSWORD);
        } else {
            loginFragment.showErrorPassword(ConstantMessage.CLEAR_TEXT);
        }
        loginFragment.setEnabledButton(true);
    }

    private void clearErrorMessageLogin() {
        loginFragment.showErrorEmail(ConstantMessage.CLEAR_TEXT);
        loginFragment.showErrorPassword(ConstantMessage.CLEAR_TEXT);
    }

    private void clearErrorMessageRegister() {
        registerFragment.showErrorEmail(ConstantMessage.CLEAR_TEXT);
        registerFragment.showErrorPassword(ConstantMessage.CLEAR_TEXT);
        registerFragment.showErrorFullName(ConstantMessage.CLEAR_TEXT);
        registerFragment.showErrorPhoneNumber(ConstantMessage.CLEAR_TEXT);
    }

    /**
     * Lấy dữ liệu từ view và xử lý logic business để đăng ký tài khoản
     */
    private void register() {
        UiController.getInstance().closeKeyBoard(registerFragment.requireActivity());
        UiController.getInstance().initProgressDialog(registerFragment.getContext());
        UiController.getInstance().turnOnLoading(registerFragment.getContext());
        clearErrorMessageRegister();
        registerFragment.getValue();
        if (UserRepository.isExistedUser(model)) {
            UiController.getInstance().turnOffLoading();
            DialogController.getInstance().showErrorDialog(registerFragment.getContext(),
                    id -> registerFragment.setEnabledButton(true),
                    ConstantMessage.MESSAGE_EXISTS_EMAIL);
            return;
        }
        if (model.isValidUser()) {
            UserRepository.createUser(model);
            UiController.getInstance().turnOffLoading();
            DialogController.getInstance().showSuccessDialog(registerFragment.getContext(), id -> {
                registerFragment.navigateToLoginFragment();
                registerFragment.setEnabledButton(true);
            }, ConstantMessage.MESSAGE_SUCCESS_REGISTER);
            return;
        }
        UiController.getInstance().turnOffLoading();

        if (model.getEmail().isEmpty()) {
            registerFragment.showErrorEmail(ConstantMessage.MESSAGE_EMPTY_EMAIL);
        } else if (!model.isValidEmail()) {
            registerFragment.showErrorEmail(ConstantMessage.MESSAGE_INVALID_EMAIL);
        } else {
            registerFragment.showErrorEmail(ConstantMessage.CLEAR_TEXT);
        }

        if (model.getPassword().isEmpty()) {
            registerFragment.showErrorPassword(ConstantMessage.MESSAGE_EMPTY_PASSWORD);
        } else if (!model.isValidPassword()) {
            registerFragment.showErrorPassword(ConstantMessage.MESSAGE_INVALID_PASSWORD);
        } else {
            registerFragment.showErrorPassword(ConstantMessage.CLEAR_TEXT);
        }

        if (model.getFullName().isEmpty()) {
            registerFragment.showErrorFullName(ConstantMessage.MESSAGE_EMPTY_FULL_NAME);
        } else {
            registerFragment.showErrorFullName(ConstantMessage.CLEAR_TEXT);
        }

        if (model.getPassword().isEmpty()) {
            registerFragment.showErrorPhoneNumber(ConstantMessage.MESSAGE_EMPTY_PHONE_NUMBER);
        } else if (!model.isValidPhoneNumber()) {
            registerFragment.showErrorPhoneNumber(ConstantMessage.MESSAGE_INVALID_PHONE_NUMBER);
        } else {
            registerFragment.showErrorPhoneNumber(ConstantMessage.CLEAR_TEXT);
        }
        registerFragment.setEnabledButton(true);
    }
}
