package com.k.shakhriyor.news.domain.model


import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("password")
    val password: String
)