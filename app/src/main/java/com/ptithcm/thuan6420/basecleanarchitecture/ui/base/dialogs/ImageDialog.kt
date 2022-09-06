package com.ptithcm.thuan6420.basecleanarchitecture.ui.base.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.DialogProgressBinding

class ImageDialog(context: Context): AlertDialog(context) {
    private lateinit var binding: DialogProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
    }

    private fun setEvent() {
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(0))
    }
}