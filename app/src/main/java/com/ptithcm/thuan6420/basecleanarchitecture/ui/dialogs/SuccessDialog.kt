package com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.LayoutSuccessDialogBinding

class SuccessDialog(context: Context?, iClickOnButtonDialogListener: IClickOnButtonDialogListener?,
                    message: String?) : AlertDialog(context), View.OnClickListener {
    private var binding: LayoutSuccessDialogBinding? = null
    private var listener: IClickOnButtonDialogListener? = null
    private var message: String? = null
    init {
        listener = iClickOnButtonDialogListener
        this.message = message
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutSuccessDialogBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setEvent()
    }

    override fun onClick(v: View?) {
        val id = v!!.id

        if (id == binding?.btnSuccess?.id) {
            dismiss()
            listener?.clickOnButtonDialog(id)
        }
    }
    private fun setEvent() {
        binding?.tvSuccessMessage?.text = message
        binding?.btnSuccess?.setOnClickListener(this)
        if (window != null) {
            window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        setCancelable(false)
    }
}