package com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.LayoutErrorDialogBinding

class ErrorDialog(context: Context?, iClickOnButtonDialogListener: IClickOnButtonDialogListener?,
                  message: String?) : AlertDialog(context), View.OnClickListener{
    private var binding: LayoutErrorDialogBinding? = null
    private var listener: IClickOnButtonDialogListener? = null
    private var message: String? = null
    init {
        listener = iClickOnButtonDialogListener
        this.message = message
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutErrorDialogBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setEvent()
    }

    override fun onClick(v: View?) {
        val id = v?.id
        if (id == binding?.btnError?.id) {
            dismiss()
            if (id != null) {
                listener!!.clickOnButtonDialog(id)
            }
        }
    }

    private fun setEvent() {
        binding?.tvErrorMessage?.text = message
        binding?.btnError?.setOnClickListener(this)
        if (window != null) {
            window?.setBackgroundDrawable(ColorDrawable(0))
        }
        setCancelable(false)
    }
}