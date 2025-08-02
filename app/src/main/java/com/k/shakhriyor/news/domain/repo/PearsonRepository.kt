package com.k.shakhriyor.news.domain.repo

import com.k.shakhriyor.news.domain.model.User
import kotlinx.coroutines.flow.Flow

interface PearsonRepository {
    suspend fun getPearson(): User
     fun getUiModeType():Int
    suspend fun changeUiModeType(modeId:Int)
}