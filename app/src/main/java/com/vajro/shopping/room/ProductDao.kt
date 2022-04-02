package com.vajro.shopping.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vajro.shopping.model.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: ProductRoom)

    @Update
    fun update(product: ProductRoom)

    @Query("SELECT * from PRODUCT_TABLE WHERE product_id= :id")
    fun getItemById(id: String): List<ProductRoom>

    @Query("SELECT * from PRODUCT_TABLE WHERE product_id= :id")
    fun getProductById(id: String): LiveData<List<ProductRoom>>

    @Delete
    fun delete(product: ProductRoom)

    @Query("delete from product_table")
    fun deleteAllProducts()

    @Query("select * from PRODUCT_TABLE")
    fun getAllProducts(): LiveData<List<ProductRoom>>
}
