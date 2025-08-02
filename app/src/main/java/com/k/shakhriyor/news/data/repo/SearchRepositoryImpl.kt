package com.k.shakhriyor.news.data.repo

import com.k.shakhriyor.news.data.api.home.SearchApi
import com.k.shakhriyor.news.domain.model.LastedNew
import com.k.shakhriyor.news.domain.model.New
import com.k.shakhriyor.news.domain.model.search_options.SearchOption
import com.k.shakhriyor.news.domain.model.search_options.SearchResponse
import com.k.shakhriyor.news.domain.repo.SearchRepository
import jakarta.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
):SearchRepository {
    override suspend fun getSearchNews(search: String): SearchResponse {
        return searchApi.getSearchNews(search)
    }

    override suspend fun getSearchNewsByFilter(filter: String, search: String): SearchResponse {
        return searchApi.getSearchNewsByFilter(filter, search)
    }

    override suspend fun getSearchNewsByCategory(category: String, search: String): SearchResponse {
        return searchApi.getSearchNewsByCategory(category, search)
    }

    override suspend fun getSearchOption(): SearchOption {
        return searchApi.getSearchOptions()
    }

    override suspend fun getFavorite(): List<LastedNew> {
        return searchApi.getFavorite()
    }
}