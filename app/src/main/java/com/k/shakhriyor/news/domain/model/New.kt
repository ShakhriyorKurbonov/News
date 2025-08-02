package com.k.shakhriyor.news.domain.model


import com.google.gson.annotations.SerializedName

data class New(
    @SerializedName("author")
    val author: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("like")
    val like: Boolean,
    @SerializedName("postedDate")
    val postedDate: String,
    @SerializedName("title")
    val title: String
)