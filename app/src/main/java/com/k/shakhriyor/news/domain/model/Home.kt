package com.k.shakhriyor.news.domain.model


import com.google.gson.annotations.SerializedName

data class Home(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("lastedNews")
    val lastedNews: List<LastedNew>
)