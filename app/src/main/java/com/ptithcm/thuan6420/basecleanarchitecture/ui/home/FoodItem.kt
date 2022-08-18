package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import android.view.View
import com.ptithcm.thuan6420.basecleanarchitecture.R
import com.ptithcm.thuan6420.basecleanarchitecture.data.dto.home.ResponseProduct
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.ItemFoodBinding
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem

class FoodItem(private val product: ResponseProduct) : BindableItem<ItemFoodBinding>() {
    override fun bind(viewBinding: ItemFoodBinding, position: Int) {
        Picasso.get().load(product.image).into(viewBinding.ivItemFood)
        viewBinding.tvNameFood.text = product.name
        viewBinding.tvPriceFood.text = product.price.toString() + " đ"
    }

    override fun getLayout() = R.layout.item_food

    override fun initializeViewBinding(view: View) = ItemFoodBinding.bind(view)

    override fun getSpanSize(spanCount: Int, position: Int) = spanCount / 2
}