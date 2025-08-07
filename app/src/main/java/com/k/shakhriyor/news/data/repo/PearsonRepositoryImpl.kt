package com.k.shakhriyor.news.data.repo

import androidx.appcompat.app.AppCompatDelegate
import com.k.shakhriyor.news.data.api.profile.PearsonApi
import com.k.shakhriyor.news.data.store.LanguageStore
import com.k.shakhriyor.news.data.store.NotificationImportanceStore
import com.k.shakhriyor.news.data.store.NotificationStore
import com.k.shakhriyor.news.data.store.UiModeStore
import com.k.shakhriyor.news.domain.model.User
import com.k.shakhriyor.news.domain.repo.PearsonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PearsonRepositoryImpl @Inject constructor(
    private val pearsonApi: PearsonApi,
    private val uiModeStore: UiModeStore,
    private val languageStore: LanguageStore,
    private val notificationStore: NotificationStore,
    private val notificationImportanceStore: NotificationImportanceStore
    ): PearsonRepository {
    override suspend fun getPearson(): User {
        return pearsonApi.getProfile()
    }

    override  fun getUiModeType(): Int {
        return runBlocking { uiModeStore.getModeType().firstOrNull()?:AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM }
    }

    override suspend fun changeUiModeType(modeId: Int) {
        uiModeStore.changeModeType(modeId)
    }

    override suspend fun changeLanguage(langCode: String) {
        languageStore.changeLanguage(langCode)
    }

    override fun getLanguage(): String? {
       return runBlocking { languageStore.getLanguage().firstOrNull() }
    }

    override fun getNotificationStatus(): Boolean {
        return runBlocking { notificationStore.getNotification().firstOrNull()?:true }
    }

    override suspend fun changeNotificationStatus(isTurnOn: Boolean) {
       notificationStore.changeNotification(isTurnOn)
    }

    override fun getNotificationImportanceStatus(): Boolean {
        return runBlocking { notificationImportanceStore.getNotificationImportance().firstOrNull()?:true }
    }

    override suspend fun changeNotificationImportanceStatus(isVoiced: Boolean) {
        notificationImportanceStore.changeNotificationImportance(isVoiced)
    }
}