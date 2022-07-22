package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

import android.view.View

interface LoginView {
    fun getRootView() : View
    fun setListener(listener: LoginListener)
}