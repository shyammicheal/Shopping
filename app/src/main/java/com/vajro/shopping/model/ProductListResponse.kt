package com.vajro.shopping.model

import com.vajro.shopping.room.ProductRoom
import java.io.Serializable

data class ProductListResponse(
    val products: List<Product>? = listOf()
):Serializable