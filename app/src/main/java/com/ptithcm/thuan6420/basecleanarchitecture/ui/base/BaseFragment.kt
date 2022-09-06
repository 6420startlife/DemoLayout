package com.ptithcm.thuan6420.basecleanarchitecture.ui.base

import android.content.Context
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.ptithcm.thuan6420.basecleanarchitecture.Constants
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.dialogs.DialogListener
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.dialogs.ErrorDialog
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.dialogs.ProgressDialog
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.dialogs.SuccessDialog
import com.ptithcm.thuan6420.basecleanarchitecture.util.Status
import com.ptithcm.thuan6420.basecleanarchitecture.util.Status.*

abstract class BaseFragment : Fragment(), View.OnClickListener, DialogListener {

    private var lastTimeClicked = 0L
    private lateinit var mSuccessDialog: SuccessDialog
    private lateinit var mErrorDialog: ErrorDialog
    private lateinit var mProgressDialog: ProgressDialog

    fun showProgressDialog() {
        mProgressDialog = ProgressDialog(this.context)
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun showSuccessDialog(message: String?) {
        mSuccessDialog = SuccessDialog(this.context, this, message)
        mSuccessDialog.show()
    }

    fun showErrorDialog(message: String?) {
        mErrorDialog = ErrorDialog(this.context, this, message)
        mErrorDialog.show()
    }

    fun closeKeyBoard() {
        val view = this.requireActivity().currentFocus ?: return
        val inputMethodManager =
            this.requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onClick(v: View?) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < Constants.DEFAULT_INTERVAL) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSingleClick(v)
    }

    fun submit(status: Status, message: String?, data: Any?) {
        when(status) {
            SUCCESS -> onSuccess(message, data)
            LOADING -> onLoading()
            ERROR -> onError(message)
        }
    }

    open fun onError(message: String?) {
        hideProgressDialog()
        showErrorDialog(message)
    }

    open fun onLoading() {
        closeKeyBoard()
        showProgressDialog()
    }

    open fun onSuccess(message: String?, data: Any?) {
        hideProgressDialog()
        showSuccessDialog(message)
    }

    override fun onClickOnSuccessDialog() {}
    override fun onClickOnErrorDialog() {}
    open fun onSingleClick(v: View?) {}
}