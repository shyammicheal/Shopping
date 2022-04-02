package com.vajro.shopping.ui.cart
import com.vajro.shopping.room.ProductRoom

interface ICartProductListener {
    fun onAddClicked(product: ProductRoom)
    fun onRemoveClicked(product: ProductRoom)
}