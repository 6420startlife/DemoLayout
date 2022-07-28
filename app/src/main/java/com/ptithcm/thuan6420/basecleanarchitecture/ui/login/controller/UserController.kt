package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.controller

import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.DialogListener
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model.User
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.login.ILoginView
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.register.IRegisterView
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.ConstantMessage
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.ConstantMessage.ID_EMAIL_FIELD
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.ConstantMessage.ID_FULL_NAME_FIELD
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.ConstantMessage.ID_PASSWORD_FIELD
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.ConstantMessage.ID_PHONE_NUMBER_FIELD
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview.IFragmentView
import kotlinx.coroutines.*

class UserController(
    baseView: IFragmentView,
    loginView: ILoginView?,
    registerView: IRegisterView?
) : IUserController {
    private var baseView: IFragmentView
    private var loginView: ILoginView?
    private var registerView: IRegisterView?
    private val logicScope = CoroutineScope(Job() + Dispatchers.Default)
    private val mainDispatcher = Dispatchers.Main
    private val ioDispatcher = Dispatchers.IO

    init {
        this.baseView = baseView
        this.loginView = loginView
        this.registerView = registerView
    }

    override fun onLogin(email: String, password: String) {
        logicScope.launch {
            val user = User(email, password)
            val invalidFields = user.isValidLogin()
            var countErrorField = 0
            for (invalidField in invalidFields) {
                when (invalidField.idField) {
                    ID_EMAIL_FIELD -> withContext(mainDispatcher) {
                        loginView?.showErrorEmail(invalidField.message)
                    }
                    ID_PASSWORD_FIELD -> withContext(mainDispatcher) {
                        loginView?.showErrorPassword(invalidField.message)
                    }
                }
                if (invalidField.message != null) {
                    countErrorField++
                }
            }
<<<<<<< Updated upstream
        }
        if (countErrorField > 0) {
            return
        }
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val isSuccess = UserRepository().checkUserNetwork(email, password)
            withContext(Dispatchers.Main) {
                if (isSuccess) {
                    baseView.onSuccess(
                        ConstantMessage.MESSAGE_SUCCESS_LOGIN,
                        object : DialogListener {
                            override fun onClickButtonOK() {
                                loginView?.navigateToMain()
                            }
                        })
                } else {
                    baseView.onFailure(ConstantMessage.MESSAGE_ERROR_LOGIN, null)
=======
            if (countErrorField == 0) {
                withContext(mainDispatcher) {
                    baseView.turnOnLoading()
                }
                withContext(ioDispatcher) {
                    val isSuccess = UserRepository().checkUserNetwork(email, password)
                    withContext(Dispatchers.Main) {
                        baseView.turnOffLoading()
                        if (isSuccess) {
                            baseView.onSuccess(
                                ConstantMessage.MESSAGE_SUCCESS_LOGIN,
                                object : DialogListener {
                                    override fun onClickButtonOK() {
                                        loginView?.navigateToMain()
                                    }
                                })
                        } else {
                            baseView.onFailure(ConstantMessage.MESSAGE_ERROR_LOGIN, null)
                        }
                    }
>>>>>>> Stashed changes
                }
            }
        }
    }
<<<<<<< Updated upstream
    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {
        val isSuccess = UserRepository().createUserNetwork(email, password, fullName, phoneNumber.toLong())
        withContext(Dispatchers.Main) {
            if (isSuccess) {
                baseView.onSuccess(
                    ConstantMessage.MESSAGE_SUCCESS_REGISTER,
                    object : DialogListener {
                        override fun onClickButtonOK() {
                            registerView?.navigateToLogin()
=======

    override fun onRegister(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ) {
        logicScope.launch {
            val user = User(
                email,
                password,
                fullName,
                phoneNumber
            )
            val invalidFields = user.isValidRegister()
            var countErrorField = 0
            for (invalidField in invalidFields) {
                when (invalidField.idField) {
                    ID_EMAIL_FIELD -> withContext(mainDispatcher) {
                        registerView?.showErrorEmail(invalidField.message)
                    }
                    ID_PASSWORD_FIELD -> withContext(mainDispatcher) {
                        registerView?.showErrorPassword(invalidField.message)
                    }
                    ID_FULL_NAME_FIELD -> withContext(mainDispatcher) {
                        registerView?.showErrorFullName(invalidField.message)
                    }
                    ID_PHONE_NUMBER_FIELD -> withContext(mainDispatcher) {
                        registerView?.showErrorPhoneNumber(invalidField.message)
                    }
                }
                if (invalidField.message != null) {
                    countErrorField++
                }
            }
            if (countErrorField == 0) {
                withContext(mainDispatcher) {
                    baseView.turnOnLoading()
                }
                withContext(ioDispatcher) {
                    val state = UserRepository().createUserNetwork(
                        email,
                        password,
                        fullName,
                        phoneNumber.toLong()
                    )
                    if (state == null) cancel()
                    withContext(mainDispatcher) {
                        baseView.turnOffLoading()
                        if (state?.status == 200) {
                            baseView.onSuccess(
                                ConstantMessage.MESSAGE_SUCCESS_REGISTER,
                                object : DialogListener {
                                    override fun onClickButtonOK() {
                                        registerView?.navigateToLogin()
                                    }
                                })
                        } else {
                            baseView.onFailure(state?.message.toString(), null)
>>>>>>> Stashed changes
                        }
                    }
                }
            }
        }
    }
}