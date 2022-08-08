package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model

import android.util.Patterns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.ConstantMessage
import java.util.regex.Pattern

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "user_email")
    @SerializedName("email") var email : String,
    @ColumnInfo(name = "user_password")
    @SerializedName("password") var password : String? = "",
    @ColumnInfo(name = "user_full_name")
    @SerializedName("name") var fullName : String? = "",
    @ColumnInfo(name = "user_phone_number")
    @SerializedName("phone_number") var phoneNumber : String? = "") : IUser {

    override fun isValidEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun isValidPassword(): Boolean {
        return password?.length!! >= 9
    }

    override fun isValidPhoneNumber(): Boolean {
        return Pattern.compile(ConstantMessage.REGEX_PHONE_NUMBER).matcher(phoneNumber.toString()).matches()
    }
}
