package com.ptithcm.thuan6420.basecleanarchitecture.data.dto.home

import com.google.gson.annotations.SerializedName

data class ResponseListMenu(
    @SerializedName("menus")
    val menus: List<ResponseMenu>
)
