package com.k.shakhriyor.news.domain.repo

import com.k.shakhriyor.news.domain.model.Home
import com.k.shakhriyor.news.domain.model.New

interface HomeRepository {
    suspend fun getHomeNews(): Home
}