package com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview

import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.DialogListener
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.setOnLoadingListener

interface IFragmentView {
    fun onSuccess(message: String, listener: DialogListener?)
    fun onFailure(message: String, listener: DialogListener?)
    fun turnOnLoading(listener : setOnLoadingListener)
    fun turnOffLoading(listener : setOnLoadingListener)
    fun closeKeyBoard()
}