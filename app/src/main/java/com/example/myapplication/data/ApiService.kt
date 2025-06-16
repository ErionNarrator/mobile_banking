package com.example.myapplication.data


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiService {
    @POST("auth/register/")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login/")
    suspend fun login(@Body request: LoginRequest): Response<ProfileLoginResponse>

    @POST("auth/token/refresh/")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>

    @GET("profile/")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UserProfileResponse>
}