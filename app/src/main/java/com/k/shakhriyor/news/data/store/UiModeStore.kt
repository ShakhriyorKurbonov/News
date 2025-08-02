package com.k.shakhriyor.news.data.store

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UiModeStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {


    companion object{
       private val UI_MODE_KEY= intPreferencesKey("ui_mode_key")
    }

    suspend fun changeModeType(modeId:Int){
        dataStore.edit { preferencies->
            preferencies[UI_MODE_KEY]=modeId
        }
    }

    fun getModeType():Flow<Int>{
        return dataStore.data.map {
            it[UI_MODE_KEY]?:AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
    }

}