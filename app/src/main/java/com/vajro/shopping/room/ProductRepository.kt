package com.vajro.shopping.room

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData


class ProductRepository(context: Context) {

    var db: ProductDao = ProductDatabase.getInstance(context)?.productDao()!!

    fun getAllProducts(): LiveData<List<ProductRoom>> {
        return db.getAllProducts()
    }

    fun getItemById(id: String): List<ProductRoom> {
        return db.getItemById(id)
    }

    fun getProductById(id: String): LiveData<List<ProductRoom>> {
        return db.getProductById(id)
    }

    fun insertProduct(product: ProductRoom) {
        insertAsyncTask(db).execute(product)
    }

    fun updateProduct(product: ProductRoom) {
        db.update(product)
    }

    fun deleteProduct(product: ProductRoom) {
        db.delete(product)
    }

    fun insertOrUpdate(product: ProductRoom, isAdd: Boolean) {
        val itemsFromDB = getItemById(product.product_id!!)
        if (itemsFromDB.isNullOrEmpty())
            insertProduct(product)
        else if (itemsFromDB.size == 1 && !isAdd && itemsFromDB.get(0).quantity==1)
            deleteProduct(itemsFromDB.get(0))
        else
            updateProduct(
                itemsFromDB.get(0)
                    .also { it.quantity = if (isAdd) it.quantity!! + 1 else it.quantity!! - 1 })
    }

    private class insertAsyncTask internal constructor(private val productDao: ProductDao) :
        AsyncTask<ProductRoom, Void, Void>() {

        override fun doInBackground(vararg params: ProductRoom): Void? {
            productDao.insert(params[0])
            return null
        }
    }
}
