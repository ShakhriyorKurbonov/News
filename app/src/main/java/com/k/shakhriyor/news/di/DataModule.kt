package com.k.shakhriyor.news.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.k.shakhriyor.news.data.store.LanguageStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.k.shakhriyor.news.data.store.TokenStore
import com.k.shakhriyor.news.data.store.UiModeStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("user_preferences")

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context):DataStore<Preferences>{
        return context.datastore
    }

    @Singleton
    @Provides
    fun provideTokenStore(dataStore: DataStore<Preferences>):TokenStore{
        return TokenStore(dataStore)
    }

    @Singleton
    @Provides
    fun provideUiModeStore(dataStore: DataStore<Preferences>):UiModeStore{
        return UiModeStore(dataStore)
    }

    @Singleton
    @Provides
    fun provideLanguageStore(dataStore: DataStore<Preferences>):LanguageStore{
        return LanguageStore(dataStore)
    }

}