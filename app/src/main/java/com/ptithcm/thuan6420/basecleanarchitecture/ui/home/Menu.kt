package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.dto.home.ResponseProduct

@Entity(tableName = "menu_table")
data class Menu(
    @ColumnInfo(name = "id_menu")
    val id: Int,
    @ColumnInfo(name = "id_menu")
    val products: List<ResponseProduct>,
    @ColumnInfo(name = "id_menu")
    val status: String,
    @ColumnInfo(name = "id_menu")
    val userId: Int,
    @ColumnInfo(name = "id_menu")
    val userName: String)
