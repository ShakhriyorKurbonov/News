package com.k.shakhriyor.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.k.shakhriyor.news.data.api.auth.AuthApi
import com.k.shakhriyor.news.data.api.home.HomeApi
import com.k.shakhriyor.news.data.api.home.SearchApi
import com.k.shakhriyor.news.data.api.profile.PearsonApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object ApiModule {

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit):AuthApi{
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit):HomeApi{
        return retrofit.create(HomeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit):SearchApi{
        return retrofit.create(SearchApi::class.java)
    }

    @Provides
    @Singleton
    fun providePearsonApi(retrofit: Retrofit): PearsonApi{
        return retrofit.create(PearsonApi::class.java)
    }


}