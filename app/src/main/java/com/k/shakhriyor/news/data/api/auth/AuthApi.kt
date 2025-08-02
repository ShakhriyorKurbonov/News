package com.k.shakhriyor.news.data.api.auth

import com.k.shakhriyor.news.domain.model.AuthResponse
import com.k.shakhriyor.news.domain.model.SignInRequest
import com.k.shakhriyor.news.domain.model.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/sign_in")
    suspend fun signIn(@Body signInRequest: SignInRequest):AuthResponse

    @POST("auth/sign_up")
    suspend fun signUp(@Body signUpRequest: SignUpRequest):AuthResponse
}