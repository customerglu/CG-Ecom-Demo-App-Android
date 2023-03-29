package com.example.customerglu.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavItemViewModel(application: Application) : AndroidViewModel(application){

    private val repository: FavItemRepo
    val allproducts: LiveData<List<LikeProductEntity>>

    init {
        val productDao = AppDatabase.getInstance(application).FavItemDao()
        repository = FavItemRepo(productDao)
        allproducts = repository.allCartProducts
    }

    fun insert(product: LikeProductEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(product)
    }

    fun deleteCart(id: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(id)
    }

    fun updateCart(product: LikeProductEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(product)
    }
    fun isExist(id: String):Boolean {
        if (repository.isExist(id))
        {
            return true
        }
        return false
    }
}