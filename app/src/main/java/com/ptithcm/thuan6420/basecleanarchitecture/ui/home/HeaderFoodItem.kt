package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import android.view.View
import com.ptithcm.thuan6420.basecleanarchitecture.R
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.ItemHeaderFoodBinding
import com.xwray.groupie.viewbinding.BindableItem

class HeaderFoodItem(username : String) : BindableItem<ItemHeaderFoodBinding>() {
    private val name = "Create by $username"
    override fun bind(viewBinding: ItemHeaderFoodBinding, position: Int) {
        viewBinding.tvUsername.text = name
    }

    override fun getLayout() = R.layout.item_header_food

    override fun initializeViewBinding(view: View): ItemHeaderFoodBinding = ItemHeaderFoodBinding.bind(view)
}