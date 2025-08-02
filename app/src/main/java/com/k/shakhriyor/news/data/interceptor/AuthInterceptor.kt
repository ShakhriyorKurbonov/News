package com.k.shakhriyor.news.data.interceptor

import com.k.shakhriyor.news.data.store.TokenStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenStore: TokenStore):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token= runBlocking { tokenStore.getToken().firstOrNull() }
        val request=if(token!=null){
            chain.request().newBuilder()
                .addHeader("Authorization","Bearer $token")
                .build()
        }else {
            chain.request()
        }
         val response=chain.proceed(request)
        if (response.code==401){
            runBlocking {
                tokenStore.removeToken()
            }
        }
        return response
    }
}