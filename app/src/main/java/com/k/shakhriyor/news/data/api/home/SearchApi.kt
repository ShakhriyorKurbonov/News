package com.k.shakhriyor.news.data.api.home

import com.k.shakhriyor.news.domain.model.LastedNew
import com.k.shakhriyor.news.domain.model.New
import com.k.shakhriyor.news.domain.model.search_options.SearchOption
import com.k.shakhriyor.news.domain.model.search_options.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("home/search_news")
    suspend fun getSearchNews(
        @Query("search") search:String
    ): SearchResponse

    @GET("home/search_news")
    suspend fun getSearchNewsByFilter(
        @Query("filter") filter:String,
        @Query("search") search:String
    ): SearchResponse

    @GET("home/search_news")
    suspend fun getSearchNewsByCategory(
        @Query("category") category:String,
        @Query("search") search:String
    ): SearchResponse

    @GET("home/category_filter")
    suspend fun getSearchOptions(): SearchOption

    @GET("favorite/get_favorite")
    suspend fun getFavorite(): List<LastedNew>
}