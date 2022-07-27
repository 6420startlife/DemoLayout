package com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.LayoutErrorDialogBinding

class ErrorDialog(context: Context?, listener: DialogListener?,
                  message: String?) : AlertDialog(context), View.OnClickListener{
    private lateinit var binding: LayoutErrorDialogBinding
    private var listener: DialogListener?
    private var message: String? = null
    init {
        this.listener = listener
        this.message = message
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutErrorDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
    }

    override fun onClick(v: View?) {
        val id = v?.id
        if (id == binding.btnError.id) {
            dismiss()
            listener?.onClickButtonOK()
        }
    }

    private fun setEvent() {
        binding.tvErrorMessage.text = message
        binding.btnError.setOnClickListener(this)
        window?.setBackgroundDrawable(ColorDrawable(0))
        setCancelable(false)
    }
}