package com.ptithcm.thuan6420.basecleanarchitecture.ui.component

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ptithcm.thuan6420.basecleanarchitecture.R

class InputTextField(context: Context, attrs: AttributeSet) : TextInputLayout(context, attrs) {
    private var etInput: TextInputEditText
    private var etInputLayout: TextInputLayout
    private var hint: String
    private var drawable: Drawable
    private var typedArray: TypedArray

    init {
        inflate(context, R.layout.component_input_text_field, this)
        etInput = findViewById(R.id.etInput)
        etInputLayout = findViewById(R.id.etInputLayout)
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.InputTextField, 0, 0)
        try {
            hint = typedArray.getString(R.styleable.InputTextField_hint).toString()
            drawable = typedArray.getDrawable(R.styleable.InputTextField_drawable)?.current!!
        } finally {
            typedArray.recycle()
        }
        etInput.hint = hint
        etInputLayout.startIconDrawable = drawable
    }

    private fun isEmptyInput() = etInput.text.toString().isEmpty()

    fun isValidInput(): Boolean {
        if (isEmptyInput()) {
            etInputLayout.error = "Hãy nhập ${hint.lowercase()}"
            return false
        }
        etInputLayout.error = null
        return true
    }

    fun getText() = etInput.text.toString()
}