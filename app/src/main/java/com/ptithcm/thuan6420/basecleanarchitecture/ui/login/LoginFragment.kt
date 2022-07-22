package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.ptithcm.thuan6420.basecleanarchitecture.R
import com.ptithcm.thuan6420.basecleanarchitecture.ui.components.UiController
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.DialogController
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.IClickOnButtonDialogListener

class LoginFragment : Fragment(), LoginListener, IClickOnButtonDialogListener {
    private lateinit var mLoginView: LoginView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mLoginView = LoginViewImp(inflater, container)
        return mLoginView.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoginView.setListener(this)
    }

    private fun navigateToMainActivity() {
        NavHostFragment.findNavController(this@LoginFragment)
            .navigate(R.id.action_loginFragment_to_mainActivity)
    }

    private fun navigateToRegisterFragment() {
        NavHostFragment.findNavController(this@LoginFragment)
            .navigate(R.id.action_loginFragment_to_registerFragment)
    }

    override fun onClickLayout() {
        UiController().closeKeyBoard(this.requireActivity())
    }

    override fun onClickButtonLogin(
        invalidFields: List<InvalidField>?,
        email: String,
        password: String
    ) {
        var countErrorField: Byte = 0
        for (invalidField in invalidFields!!) {
            if (invalidField.message != null) {
                countErrorField++
            }
        }
        if (countErrorField == UserController().NO_FIELD) {
            val responseUI = UserController().isLoginSuccess(email, password)

            if (responseUI.success) {
                DialogController().showSuccessDialog(this.context, this, responseUI.message)
            } else {
                DialogController().showErrorDialog(this.context, this, responseUI.message)
            }
        }
    }

    override fun onClickTvToRegister() {
        navigateToRegisterFragment()
    }

    override fun findInvalidFields(email: String, password: String) =
        UserController().findInvalidLoginFields(email, password)

    override fun clickOnButtonDialog(id: Int) {
        if (id == R.id.btnSuccess) {
            navigateToMainActivity()
        }
    }
}