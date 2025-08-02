package com.k.shakhriyor.news.domain.model.search_options


import com.google.gson.annotations.SerializedName

data class SearchOption(
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("filter")
    val filter: List<String>
)