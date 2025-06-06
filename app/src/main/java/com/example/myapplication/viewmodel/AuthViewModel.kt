package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.ApiClient
import com.example.myapplication.data.AuthResponse
import com.example.myapplication.data.LoginRequest
import com.example.myapplication.data.RegisterRequest
import com.example.myapplication.data.UserProfileResponse
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _userProfile = MutableStateFlow<UserProfileResponse?>(null)
    val userProfile: StateFlow<UserProfileResponse?> = _userProfile

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val tokens: AuthResponse) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    fun register(
        username: String,
        email: String,
        phoneNumber: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = ApiClient.apiService.register(
                    RegisterRequest(
                        username = username,
                        email = email,
                        phoneNumber = phoneNumber,
                        password = password,
                        confirmPassword = confirmPassword
                    )
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        _authState.value = AuthState.Success(it)
                        fetchUserProfile(it.accessToken)
                    } ?: run {
                        _authState.value = AuthState.Error("Empty response")
                    }
                } else {
                    val error = response.errorBody()?.string() ?: "Unknown error"
                    _authState.value = AuthState.Error(error)
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Network error")
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = ApiClient.apiService.login(LoginRequest(username, password))

                if (response.isSuccessful) {
                    response.body()?.let {
                        _authState.value = AuthState.Success(it)
                        fetchUserProfile(it.accessToken)
                    } ?: run {
                        _authState.value = AuthState.Error("Empty response")
                    }
                } else {
                    val error = response.errorBody()?.string() ?: "Unknown error"
                    _authState.value = AuthState.Error(error)
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Network error")
            }
        }
    }

    private suspend fun fetchUserProfile(token: String) {
        try {
            val response = ApiClient.apiService.getProfile("Bearer $token")
            if (response.isSuccessful) {
                _userProfile.value = response.body()
            }
        } catch (e: Exception) {
            // Handle error
        }
    }
}