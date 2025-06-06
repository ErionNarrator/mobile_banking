package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

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

// Response Models
data class AuthResponse(
    @SerializedName("access") val accessToken: String,
    @SerializedName("refresh") val refreshToken: String
)

data class UserProfileResponse(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("balance") val balance: Double,
    @SerializedName("account_number") val accountNumber: String
)