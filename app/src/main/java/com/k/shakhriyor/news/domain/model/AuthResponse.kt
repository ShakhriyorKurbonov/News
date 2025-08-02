package com.k.shakhriyor.news.domain.model


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token")
    val token: String
)