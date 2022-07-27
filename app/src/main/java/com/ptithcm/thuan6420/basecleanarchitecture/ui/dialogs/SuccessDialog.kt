package com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.LayoutSuccessDialogBinding

class SuccessDialog(
    context: Context?, listener: DialogListener?,
    message: String?
) : AlertDialog(context), View.OnClickListener {
    private lateinit var binding: LayoutSuccessDialogBinding
    private var listener: DialogListener?
    private var message: String?

    init {
        this.message = message
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutSuccessDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
    }

    override fun onClick(v: View?) {
        if (v == binding.btnSuccess) {
            dismiss()
            listener?.onClickButtonOK()
        }
    }

    private fun setEvent() {
        binding.tvSuccessMessage.text = message
        binding.btnSuccess.setOnClickListener(this)
        window?.setBackgroundDrawable(ColorDrawable(0))
        setCancelable(false)
    }
}