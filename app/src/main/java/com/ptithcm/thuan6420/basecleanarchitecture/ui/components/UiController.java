package com.ptithcm.thuan6420.basecleanarchitecture.ui.components;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ptithcm.thuan6420.basecleanarchitecture.ui.util.ConstantMessage;

public class UiController {
    public static UiController Instance;
    private static ProgressDialog mProgressDialog;

    public void initProgressDialog(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(ConstantMessage.MESSAGE_LOADING);
    }

    public static UiController getInstance() {
        if (Instance != null) {
            return Instance;
        }
        return new UiController();
    }

    public void closeKeyBoard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void turnOnLoading(Context context) {
        mProgressDialog.show();
    }

    public void turnOffLoading() {
       mProgressDialog.dismiss();
    }

}
