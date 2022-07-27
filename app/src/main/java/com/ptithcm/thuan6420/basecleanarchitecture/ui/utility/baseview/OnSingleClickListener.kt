package com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview

import android.view.View
import kotlinx.coroutines.*

interface OnSingleClickListener : View.OnClickListener {
    override fun onClick(v: View?) {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            v?.isEnabled = false
            onClicked(v)
            delay(500)
            v?.isEnabled = true
        }
    }
    suspend fun onClicked(v: View?)
}