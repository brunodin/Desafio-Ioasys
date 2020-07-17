package com.example.ioasys.service.models

import com.google.gson.annotations.SerializedName

data class LoginParameters(

    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String

)