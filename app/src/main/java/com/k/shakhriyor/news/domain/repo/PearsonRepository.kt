package com.k.shakhriyor.news.domain.repo

import com.k.shakhriyor.news.domain.model.User
import kotlinx.coroutines.flow.Flow

interface PearsonRepository {
    suspend fun getPearson(): User
     fun getUiModeType():Int
    suspend fun changeUiModeType(modeId:Int)
    suspend fun changeLanguage(langCode:String)
    fun getLanguage():String?
    fun getNotificationStatus(): Boolean
    suspend fun changeNotificationStatus(isTurnOn: Boolean)
    fun getNotificationImportanceStatus(): Boolean
    suspend fun changeNotificationImportanceStatus(isVoiced: Boolean)
}