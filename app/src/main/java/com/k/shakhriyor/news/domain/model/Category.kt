package com.k.shakhriyor.news.domain.model


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("categoryType")
    val categoryType: String,
    @SerializedName("news")
    val news: List<New>
)