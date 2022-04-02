package com.vajro.shopping.ui.product
import com.vajro.shopping.model.Product

interface IProductListener {
    fun onProductClicked(product: Product)
    fun onAddClicked(product: Product)
    fun onRemoveClicked(product: Product)
}