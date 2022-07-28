package com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview

import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.DialogListener

interface IFragmentView {
    fun onSuccess(message: String, listener: DialogListener?)
    fun onFailure(message: String, listener: DialogListener?)
    fun turnOnLoading()
    fun turnOffLoading()
    fun closeKeyBoard()
}