package com.example.customerglu.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavItemDao {

    @Query("SELECT * FROM fav_items order by id desc")
    fun getAll(): LiveData<List<LikeProductEntity>>

    @Query("SELECT EXISTS(SELECT * FROM fav_items WHERE Product_ID = :id)")
    fun isItemExist(id : String) : Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg product: LikeProductEntity)

    @Query("DELETE FROM fav_items WHERE Product_ID = :id")
    suspend fun delete(id: String)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg product: LikeProductEntity)
}