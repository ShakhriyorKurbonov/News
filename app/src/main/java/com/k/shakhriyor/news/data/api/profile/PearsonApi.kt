package com.k.shakhriyor.news.data.api.profile

import com.k.shakhriyor.news.domain.model.User
import retrofit2.http.GET

interface PearsonApi {
    @GET("profile/get_profile")
    suspend fun getProfile(): User
}