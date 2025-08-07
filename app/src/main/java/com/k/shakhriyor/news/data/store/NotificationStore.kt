package com.k.shakhriyor.news.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotificationStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object{
        private val NOTIFICATION_KEY= booleanPreferencesKey("notification_key")
    }

    suspend fun changeNotification(isTurnOn: Boolean){
        dataStore.edit { preferences ->
            preferences[NOTIFICATION_KEY]=isTurnOn
        }
    }

     fun getNotification(): Flow<Boolean>{
        return dataStore.data.map {
            it[NOTIFICATION_KEY]?:true
        }
    }


}