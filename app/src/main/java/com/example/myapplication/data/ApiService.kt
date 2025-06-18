package com.example.myapplication.data


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @POST("auth/register/")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login/")
    suspend fun login(@Body request: LoginRequest): Response<ProfileLoginResponse>

    @POST("auth/token/refresh/")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>

    @GET("profile/")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UserProfileResponse>

    @POST("transfer/")
    suspend fun transfer(@Header("Authorization") token: String, @Body request: TransferRequest): Response<TransferResponse>

    @GET("accounts/search/")
    suspend fun searchAccounts(@Header("Authorization") token: String, @Query("query") query: String): Response<List<AccountSearchResult>>
}