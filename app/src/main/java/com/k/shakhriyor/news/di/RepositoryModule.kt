package com.k.shakhriyor.news.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.k.shakhriyor.news.data.repo.AuthRepositoryImpl
import com.k.shakhriyor.news.data.repo.HomeRepositoryImpl
import com.k.shakhriyor.news.data.repo.PearsonRepositoryImpl
import com.k.shakhriyor.news.data.repo.SearchRepositoryImpl
import com.k.shakhriyor.news.domain.repo.AuthRepository
import com.k.shakhriyor.news.domain.repo.HomeRepository
import com.k.shakhriyor.news.domain.repo.PearsonRepository
import com.k.shakhriyor.news.domain.repo.SearchRepository

@Module
@InstallIn(SingletonComponent::class)

 abstract class RepositoryModule {

     @Binds
     abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl):AuthRepository

     @Binds
     abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl):HomeRepository

     @Binds
     abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl):SearchRepository

     @Binds
     abstract fun bindPearsonRepository(pearsonRepositoryImpl: PearsonRepositoryImpl): PearsonRepository

}