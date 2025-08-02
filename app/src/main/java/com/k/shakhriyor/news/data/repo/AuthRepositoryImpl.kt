package com.k.shakhriyor.news.data.repo

import com.k.shakhriyor.news.data.api.auth.AuthApi
import com.k.shakhriyor.news.data.store.TokenStore
import com.k.shakhriyor.news.domain.model.AuthResponse
import com.k.shakhriyor.news.domain.model.SignInRequest
import com.k.shakhriyor.news.domain.model.SignUpRequest
import com.k.shakhriyor.news.domain.repo.AuthRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStore: TokenStore
) :AuthRepository {
    override suspend fun signIn(email: String, password: String): Boolean {
        val response=authApi.signIn(SignInRequest(email,password))
        tokenStore.addToken(response.token)
        val token=tokenStore.getToken().firstOrNull()
        return !token.isNullOrBlank()||!token.isNullOrEmpty()
    }


    override suspend fun signUp(
        email: String,
        fullName: String,
        password: String
    ): Boolean {
        val response=authApi.signUp(SignUpRequest(email, fullName, password))
        tokenStore.addToken(response.token)
        val token=tokenStore.getToken().firstOrNull()
        return !token.isNullOrBlank()||!token.isNullOrEmpty()
    }
}