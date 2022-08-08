package com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview

import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.DialogListener
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.ErrorDialog
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.SuccessDialog
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.setOnLoadingListener

abstract class BaseFragmentView : Fragment(), IFragmentView {
    private lateinit var mSuccessDialog: SuccessDialog
    private lateinit var mErrorDialog: ErrorDialog

    override fun onSuccess(message: String, listener : DialogListener?) {
        mSuccessDialog = SuccessDialog(this.context, listener, message)
        mSuccessDialog.show()
    }

    override fun onFailure(message: String, listener : DialogListener?) {
        mErrorDialog = ErrorDialog(this.context, listener, message)
        mErrorDialog.show()
    }

    override fun turnOnLoading(listener : setOnLoadingListener) {
        listener.setUiWhenLoading(false)
        this.requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun turnOffLoading(listener : setOnLoadingListener) {
        listener.setUiWhenLoading(true)
        this.requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun closeKeyBoard() {
        val view = this.requireActivity().currentFocus ?: return
        val inputMethodManager = this.requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}