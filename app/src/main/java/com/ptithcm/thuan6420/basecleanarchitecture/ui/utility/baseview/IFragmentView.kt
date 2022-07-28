package com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview

import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.DialogListener

interface IFragmentView {
    fun onSuccess(message: String, listener: DialogListener?)
    fun onFailure(message: String, listener: DialogListener?)
<<<<<<< Updated upstream
    fun turnOnLoading(button: Button, progressBar: ProgressBar)
    fun turnOffLoading(button: Button, progressBar: ProgressBar)
    fun preventSpamButton(button: Button)
=======
    fun turnOnLoading()
    fun turnOffLoading()
>>>>>>> Stashed changes
    fun closeKeyBoard()
}