package com.vajro.shopping.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "product_table")
data class ProductRoom(
    val id: Int? = null,
    val description: String?,
    val href: String?,
    val image: String?,
    val name: String?,
    val price: String?,
    val product_id: String?,
    var quantity: Int? = 0,
    val sku: String?,
    val special: String?,
    val thumb: String?,
    val zoom_thumb: String?,
    @PrimaryKey(autoGenerate = false)
val id_unique: Int? = null,
)