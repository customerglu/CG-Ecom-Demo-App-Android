package com.example.customerglu.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "fav_items")

data class LikeProductEntity(
    @ColumnInfo(name = "Product_Name") var name: String,
    @ColumnInfo(name = "Product_Quantity") var qua: Int,
    @ColumnInfo(name = "Product_Price") var price: String,
    @ColumnInfo(name = "Product_ID") var pId: String,
    @ColumnInfo(name = "Product_Image") var Image: String,
    @ColumnInfo(name = "Product_From") var productFrom: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}