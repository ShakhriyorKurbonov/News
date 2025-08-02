package com.k.shakhriyor.news.domain.model.search_options

import com.google.gson.annotations.SerializedName
import com.k.shakhriyor.news.domain.model.New

data class SearchResponse(
    @SerializedName("results")
    val results: String,
    @SerializedName("news")
    val news: List<New>
)
