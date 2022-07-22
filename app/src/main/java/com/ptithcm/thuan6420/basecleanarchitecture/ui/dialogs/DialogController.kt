package com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs

import android.content.Context
import com.ptithcm.thuan6420.basecleanarchitecture.ui.components.UiController

class DialogController {
    private var mSuccessDialog: SuccessDialog? = null
    private var mErrorDialog: ErrorDialog? = null

    fun getInstance(): DialogController? {
        return if (Instance != null) {
            Instance
        } else DialogController()
    }

    fun showSuccessDialog(
        context: Context?, listener: IClickOnButtonDialogListener?, message: String?
    ) {
        mSuccessDialog = SuccessDialog(context, listener, message)
        mSuccessDialog!!.show()
    }

    fun showErrorDialog(
        context: Context?, listener: IClickOnButtonDialogListener?, message: String?
    ) {
        mErrorDialog = ErrorDialog(context, listener, message)
        mErrorDialog!!.show()
    }

    companion object{
        private var Instance : DialogController? = null
        operator fun invoke() = synchronized(this) {
            if (Instance != null) {
                Instance
            }
            Instance = DialogController()
        }
    }
}