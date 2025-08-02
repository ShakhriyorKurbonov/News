package com.k.shakhriyor.news.data.repo

import androidx.appcompat.app.AppCompatDelegate
import com.k.shakhriyor.news.data.api.profile.PearsonApi
import com.k.shakhriyor.news.data.store.UiModeStore
import com.k.shakhriyor.news.domain.model.User
import com.k.shakhriyor.news.domain.repo.PearsonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PearsonRepositoryImpl @Inject constructor(
    private val pearsonApi: PearsonApi,
    private val uiModeStore: UiModeStore
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
}