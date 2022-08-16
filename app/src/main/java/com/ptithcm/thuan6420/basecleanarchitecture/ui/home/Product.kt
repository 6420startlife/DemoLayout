package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "id_product")
    var id : Int,
    @ColumnInfo(name = "image_product")
    var image : String,
    @ColumnInfo(name = "name_product")
    var name : String,
    @ColumnInfo(name = "price_product")
    var price : Int)

