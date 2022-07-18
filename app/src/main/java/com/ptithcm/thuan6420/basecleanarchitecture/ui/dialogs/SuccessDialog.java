package com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.ptithcm.thuan6420.basecleanarchitecture.R;
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.LayoutSuccessDialogBinding;

public class SuccessDialog extends AlertDialog implements View.OnClickListener {
    private LayoutSuccessDialogBinding binding;
    private final IClickOnButtonDialogListener listener;
    private final String message;

    public SuccessDialog(Context context, IClickOnButtonDialogListener iClickOnButtonDialogListener
            , String message) {
        super(context, R.style.AlertDialogTheme);
        this.listener = iClickOnButtonDialogListener;
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutSuccessDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setEvent();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == binding.btnSuccess.getId()) {
            dismiss();
            listener.clickOnButtonDialog(id);
        }
    }

    private void setEvent() {
        binding.tvSuccessMessage.setText(message);
        binding.btnSuccess.setOnClickListener(this);

        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        setCancelable(false);
    }
}
