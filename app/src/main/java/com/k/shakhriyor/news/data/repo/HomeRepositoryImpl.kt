package com.k.shakhriyor.news.data.repo

import com.k.shakhriyor.news.data.api.home.HomeApi
import com.k.shakhriyor.news.domain.model.Home
import com.k.shakhriyor.news.domain.model.New
import com.k.shakhriyor.news.domain.repo.HomeRepository
import jakarta.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
):HomeRepository {
    override suspend fun getHomeNews(): Home {
        val homePageNews=homeApi.getHomeNews()
        return homePageNews
    }


}