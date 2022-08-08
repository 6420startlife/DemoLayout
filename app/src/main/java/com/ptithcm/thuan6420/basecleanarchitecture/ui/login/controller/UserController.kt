package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.controller

import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model.User
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.login.ILoginView
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.register.IRegisterView
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.ConstantMessage
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview.IFragmentView

class UserController(
    private val baseView: IFragmentView,
    private val loginView: ILoginView?,
    private val registerView: IRegisterView?
) : IUserController {

    override fun isValidLogin(email: String, password: String) {
        val user = User(email, password)
        if (email.isEmpty()) {
            loginView?.showErrorEmail(ConstantMessage.MESSAGE_EMPTY_EMAIL)
        } else if (!user.isValidEmail()) {
            loginView?.showErrorEmail(ConstantMessage.MESSAGE_INVALID_EMAIL)
        } else {
            loginView?.showErrorEmail(null)
        }

        if (password.isEmpty()) {
            loginView?.showErrorPassword(ConstantMessage.MESSAGE_EMPTY_PASSWORD)
        } else if (!user.isValidPassword()) {
            loginView?.showErrorPassword(ConstantMessage.MESSAGE_INVALID_PASSWORD)
        } else {
            loginView?.showErrorPassword(null)
        }

        loginView?.isValidValueToLogin(user.isValidEmail() && user.isValidPassword())
    }

    override fun isValidRegister(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ) {

        val user = User(email, password)
        if (email.isEmpty()) {
            registerView?.showErrorEmail(ConstantMessage.MESSAGE_EMPTY_EMAIL)
        } else if (!user.isValidEmail()) {
            registerView?.showErrorEmail(ConstantMessage.MESSAGE_INVALID_EMAIL)
        } else {
            registerView?.showErrorEmail(null)
        }

        if (password.isEmpty()) {
            registerView?.showErrorPassword(ConstantMessage.MESSAGE_EMPTY_PASSWORD)
        } else if (!user.isValidPassword()) {
            registerView?.showErrorPassword(ConstantMessage.MESSAGE_INVALID_PASSWORD)
        } else {
            loginView?.showErrorPassword(null)
        }

        if (fullName.isEmpty()) {
            registerView?.showErrorFullName(ConstantMessage.MESSAGE_EMPTY_FULL_NAME)
        }

        if (phoneNumber.isEmpty()) {
            registerView?.showErrorPhoneNumber(ConstantMessage.MESSAGE_EMPTY_PHONE_NUMBER)
        } else if (!user.isValidPhoneNumber()) {
            registerView?.showErrorPhoneNumber(ConstantMessage.MESSAGE_INVALID_PHONE_NUMBER)
        } else {
            registerView?.showErrorPhoneNumber(null)
        }

        registerView?.isValidValueToRegister(
            user.isValidEmail() &&
                    user.isValidPassword() &&
                    user.isValidPhoneNumber() &&
                    user.fullName?.isEmpty() == false
        )
    }

    override suspend fun onLogin(email: String, password: String) {
//        baseView.turnOnLoading()
////        val result = UserRepository.checkUserNetwork(email, password)
//        baseView.turnOffLoading()
//        if (result) {
//            baseView.onSuccess(
//                ConstantMessage.MESSAGE_SUCCESS_LOGIN,
//                object : DialogListener {
//                    override fun onClickButtonOK() {
//                        loginView?.navigateToMain()
//                    }
//                })
//        } else {
//            baseView.onFailure(ConstantMessage.MESSAGE_ERROR_LOGIN, null)
//        }
    }

    override suspend fun onRegister(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ) {
//        baseView.turnOnLoading()
//        val result = UserRepository().createUserNetwork(email, password, fullName, phoneNumber.toLong())
//        baseView.turnOffLoading()
//        if (result?.status == 200) {
//            baseView.onSuccess(
//                ConstantMessage.MESSAGE_SUCCESS_REGISTER,
//                object : DialogListener {
//                    override fun onClickButtonOK() {
//                        registerView?.navigateToLogin()
//                    }
//                })
//        } else {
//            baseView.onFailure(result?.message.toString(), null)
//        }
    }
}