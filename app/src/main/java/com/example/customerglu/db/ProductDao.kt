package com.example.customerglu.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("SELECT * FROM cart_items order by id desc")
    fun getAll(): LiveData<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg product: ProductEntity)

    @Delete
    suspend fun delete(product: ProductEntity) : Int

    @Query("DELETE FROM cart_items")
    suspend fun  deleteTable(): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg product: ProductEntity) : Void
}