package com.k.shakhriyor.news.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotificationImportanceStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object{
        private val NOTIFICATION_IMPORTANCE_KEY= booleanPreferencesKey("notification_importance_key")
    }

    suspend fun changeNotificationImportance(isTurnOn: Boolean){
        dataStore.edit { preferences ->
            preferences[NOTIFICATION_IMPORTANCE_KEY]=isTurnOn
        }
    }

     fun getNotificationImportance(): Flow<Boolean>{
        return dataStore.data.map {
            it[NOTIFICATION_IMPORTANCE_KEY]?:true
        }
    }


}