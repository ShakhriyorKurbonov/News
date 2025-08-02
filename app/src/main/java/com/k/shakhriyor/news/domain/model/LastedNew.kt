package com.k.shakhriyor.news.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LastedNew(
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
): Parcelable