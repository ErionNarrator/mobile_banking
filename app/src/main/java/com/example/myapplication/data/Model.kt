package com.example.myapplication.data

import com.google.gson.annotations.SerializedName


data class RefreshTokenRequest(
    @SerializedName("refresh") val refreshToken: String
)

data class RefreshTokenResponse(
    @SerializedName("access") val accessToken: String
)

data class ProfileLoginResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("user_profile") val userProfile: UserProfileResponse?
)

data class RegisterRequest(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("password") val password: String,
    @SerializedName("password2") val confirmPassword: String
)

data class LoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)



data class UserProfileResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("user") val user: UserData,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("balance") val balance: Double,
    @SerializedName("account_number") val accountNumber: String,
    @SerializedName("default_currency") val defaultCurrency: CurrencyData,
    @SerializedName("balance_in_usd") val balanceInUsd: Double,
    @SerializedName("balance_in_eur") val balanceInEur: Double,
    @SerializedName("balance_in_rub") val balanceInRub: Double
)

data class AuthResponse(
    @SerializedName("access") val accessToken: String,
    @SerializedName("refresh") val refreshToken: String
)


data class CurrencyData(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String
)

data class UserData(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String
)