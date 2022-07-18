package com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs;

import android.content.Context;

public class DialogController {
    public static DialogController Instance;
    private static SuccessDialog mSuccessDialog;
    private static ErrorDialog mErrorDialog;

    public static DialogController getInstance() {
        if (Instance != null) {
            return Instance;
        }
        return new DialogController();
    }

    public void showSuccessDialog(Context context, IClickOnButtonDialogListener listener
            ,String message) {
        mSuccessDialog = new SuccessDialog(context, listener, message);
        mSuccessDialog.show();
    }

    public void showErrorDialog(Context context, IClickOnButtonDialogListener listener
            ,String message) {
        mErrorDialog = new ErrorDialog(context, listener, message);
        mErrorDialog.show();
    }
}
