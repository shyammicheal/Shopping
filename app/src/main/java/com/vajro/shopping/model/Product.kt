package com.vajro.shopping.model

import java.io.Serializable

data class Product(
    val description: String? = "",
    val href: String? = "",
    val id: Int? = 0,
    val image: String? = "",
    val images: List<Any>? = listOf(),
    val name: String? = "",
    val options: List<Any>? = listOf(),
    val price: String? = "",
    val product_id: String? = "",
    var quantity: Int? = 0,
        var product_quantity: Int? = 0,
    val sku: String? = "",
    val special: String? = "",
    val thumb: String? = "",
    val zoom_thumb: String? = ""
):Serializable