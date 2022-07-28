package com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.Fragment
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.DialogListener
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.ErrorDialog
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.SuccessDialog
import kotlinx.coroutines.*

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
    
    override fun closeKeyBoard() {
        val view = this.requireActivity().currentFocus ?: return
        val inputMethodManager = this.requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}