package com.k.shakhriyor.news.data.api.home

import com.k.shakhriyor.news.domain.model.Home
import com.k.shakhriyor.news.domain.model.New
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("home/get_news")
    suspend fun getHomeNews(): Home

}