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

class RegisterFragment : Fragment(), RegisterListener, IClickOnButtonDialogListener {
    private lateinit var mRegisterView: RegisterView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mRegisterView = RegisterViewImp(inflater, container)
        return mRegisterView.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRegisterView.setListener(this)
    }

    fun navigateToLoginFragment() {
        NavHostFragment.findNavController(this@RegisterFragment)
            .navigate(R.id.action_registerFragment_to_loginFragment)
    }

    override fun clickOnButtonDialog(id: Int) {
        if (id == R.id.btnSuccess) {
            navigateToLoginFragment()
        }
    }

    override fun onClickLayout() {
        UiController().closeKeyBoard(this.requireActivity())
    }

    override fun onClickButtonRegister(
        invalidFields: List<InvalidField>?,
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ) {
        var countErrorField: Byte = 0
        for (invalidField in invalidFields!!) {
            if (invalidField.message != null) {
                countErrorField++
            }
        }
        if (countErrorField == UserController().NO_FIELD) {
            val responseUI =
                UserController().isRegisterSuccess(email, password, fullName, phoneNumber)

            if (responseUI.success) {
                DialogController().showSuccessDialog(this.context, this, responseUI.message)
            } else {
                DialogController().showErrorDialog(this.context, this, responseUI.message)
            }
        }
    }

    override fun onClickTvToRegister() {
        navigateToLoginFragment()
    }

    override fun findInvalidFields(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ): List<InvalidField> =
        UserController().findInvalidRegisterFields(email, password, fullName, phoneNumber)
}