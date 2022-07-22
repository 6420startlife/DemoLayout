package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

import android.view.View

interface RegisterView {
    fun getRootView() : View
    fun setListener(listener: RegisterListener)
}