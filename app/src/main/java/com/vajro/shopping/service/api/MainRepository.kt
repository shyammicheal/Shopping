package com.vajro.shopping.service.api

import com.mindorks.retrofit.coroutines.data.api.ApiHelper

class MainRepository constructor(private val retrofitService: ApiHelper) {

    suspend fun getProductList() = retrofitService.getProductList()
}