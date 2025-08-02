package com.k.shakhriyor.news.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class TokenStore @Inject constructor(
    private val dataStore:DataStore<Preferences>
)  {




    companion object{
        private val TOKEN_KEY= stringPreferencesKey("auth_token")
    }

    suspend fun addToken(token:String){
        dataStore.edit { preferences->
            preferences[TOKEN_KEY]=token
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map {
            it[TOKEN_KEY] ?:""
        }
    }

    suspend fun removeToken(){
        dataStore.edit {
            it.remove(TOKEN_KEY)
        }
    }

}