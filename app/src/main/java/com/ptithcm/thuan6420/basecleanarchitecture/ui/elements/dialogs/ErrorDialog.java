package com.ptithcm.thuan6420.basecleanarchitecture.ui.elements.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.ptithcm.thuan6420.basecleanarchitecture.R;
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.LayoutErrorDialogBinding;

public class ErrorDialog extends AlertDialog implements View.OnClickListener {
    private LayoutErrorDialogBinding binding;
    private final IClickOnButtonDialogListener listener;
    private final int message;

    public ErrorDialog(Context context, IClickOnButtonDialogListener iClickOnButtonDialogListener, int message) {
        super(context, R.style.AlertDialogTheme);
        this.listener = iClickOnButtonDialogListener;
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutErrorDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setEvent();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == binding.btnError.getId()) {
            dismiss();
            listener.clickOnButtonDialog(id);
        }
    }

    private void setEvent() {
        binding.tvErrorMessage.setText(message);
        binding.btnError.setOnClickListener(this);

        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        setCancelable(false);
    }
}
