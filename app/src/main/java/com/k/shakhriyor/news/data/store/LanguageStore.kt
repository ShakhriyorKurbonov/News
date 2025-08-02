package com.k.shakhriyor.news.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LanguageStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object{
        private val LANG_KEY= stringPreferencesKey("lang_key")
    }

    suspend fun changeLanguage(langCode:String){
        dataStore.edit { preferences->
            preferences[LANG_KEY]=langCode
        }
    }

     fun getLanguage(): Flow<String?> {
        return dataStore.data.map {
            it[LANG_KEY]
        }
    }

}