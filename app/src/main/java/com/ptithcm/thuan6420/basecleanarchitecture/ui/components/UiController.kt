package com.ptithcm.thuan6420.basecleanarchitecture.ui.components

import android.app.Activity

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.ptithcm.thuan6420.basecleanarchitecture.ui.util.ConstantMessage

class UiController {
    private var mProgressBar: ProgressBar? = null

    fun initProgressDialog(context: Context?) {
        mProgressBar = ProgressBar(context)
    }

    fun closeKeyBoard(activity: Activity) {
        val view = activity.currentFocus ?: return
        val inputMethodManager = activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun turnOnLoading(context: Context?) {
        mProgressBar?.visibility = View.VISIBLE
    }

    fun turnOffLoading() {
        mProgressBar?.visibility = View.GONE
    }

    companion object{
        private var Instance : UiController? = null
        operator fun invoke() = synchronized(this) {
            if (Instance != null) {
                Instance
            }
            Instance = UiController()
        }
    }
}