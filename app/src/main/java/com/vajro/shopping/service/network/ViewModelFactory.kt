package com.vajro.shopping.service.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vajro.shopping.ui.main.MainViewModel
import com.vajro.shopping.service.api.MainRepository
import com.mindorks.retrofit.coroutines.data.api.ApiHelper

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(MainRepository(apiHelper)) as T
    }
}

