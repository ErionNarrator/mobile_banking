package com.example.myapplication.data


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiService {
    @POST("auth/register/")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/token/")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @GET("profile/")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UserProfileResponse>
}