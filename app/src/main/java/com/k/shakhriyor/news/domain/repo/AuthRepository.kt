package com.k.shakhriyor.news.domain.repo

interface AuthRepository {
    suspend fun signIn(email:String,password:String):Boolean
    suspend fun signUp(email: String,fullName:String,password: String):Boolean
}