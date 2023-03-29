package com.example.customerglu.db

import androidx.lifecycle.LiveData

class FavItemRepo(private val productDao: FavItemDao) {

    val allCartProducts: LiveData<List<LikeProductEntity>> = productDao.getAll()

    suspend fun insert(product: LikeProductEntity) {
        productDao.insert(product)
    }
    suspend fun delete(id: String) {
        productDao.delete(id)
    }
    suspend fun update(product: LikeProductEntity) {
        productDao.update(product)
    }
      fun  isExist(id: String): Boolean {
       if ( productDao.isItemExist(id))
       {
           return true
       }
        return false
    }
}