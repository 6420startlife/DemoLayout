package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    @SerializedName("id") var id: Int? = null,
    @ColumnInfo(name = "user_email")
    @SerializedName("email") var email: String,
    @ColumnInfo(name = "user_password")
    @SerializedName("password") var password: String? = "",
    @ColumnInfo(name = "user_full_name")
    @SerializedName("name") var fullName: String? = "",
    @ColumnInfo(name = "user_phone_number")
    @SerializedName("phone_number") var phoneNumber: String? = ""
)
