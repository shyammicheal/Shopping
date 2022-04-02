package com.mindorks.retrofit.coroutines.data.api

import com.vajro.shopping.model.ProductListResponse
import retrofit2.http.GET

interface ApiService {

    @GET("v2/5def7b172f000063008e0aa2")
    suspend fun getProductList(): ProductListResponse

}