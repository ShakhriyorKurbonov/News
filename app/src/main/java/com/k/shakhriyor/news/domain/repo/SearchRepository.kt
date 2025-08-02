package com.k.shakhriyor.news.domain.repo

import com.k.shakhriyor.news.domain.model.LastedNew
import com.k.shakhriyor.news.domain.model.New
import com.k.shakhriyor.news.domain.model.search_options.SearchOption
import com.k.shakhriyor.news.domain.model.search_options.SearchResponse


interface SearchRepository {
    suspend fun getSearchNews(search:String): SearchResponse
    suspend fun getSearchNewsByFilter(filter:String,search:String): SearchResponse
    suspend fun getSearchNewsByCategory(category:String,search:String): SearchResponse
    suspend fun getSearchOption(): SearchOption
    suspend fun getFavorite(): List<LastedNew>
}