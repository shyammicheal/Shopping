package com.vajro.shopping.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vajro.shopping.service.api.MainRepository
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.liveData
import com.vajro.shopping.model.Product
import com.vajro.shopping.model.ProductListResponse
import com.vajro.shopping.utils.Resource
import okhttp3.ResponseBody
import org.json.JSONObject

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {
var productListResponse= MutableLiveData<ProductListResponse>()
    fun getProductList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getProductList()))
        } catch (exception: Throwable) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
