package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _userProfile = MutableStateFlow<UserProfileResponse?>(null)
    val userProfile: StateFlow<UserProfileResponse?> = _userProfile

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val response: ProfileLoginResponse) : AuthState()
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
            _isLoading.value = true
            _errorMessage.value = null

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

                handleAuthResponse(response)
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = ApiClient.apiService.login(
                    LoginRequest(
                        username = username,
                        password = password
                    )
                )

                handleAuthResponse(response)
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchUserProfile(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = ApiClient.apiService.getProfile("Bearer $token")
                if (response.isSuccessful) {
                    _userProfile.value = response.body()
                } else {
                    _errorMessage.value = response.errorBody()?.string() ?: "Failed to load profile"
                }
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        _authState.value = AuthState.Idle
        _userProfile.value = null
    }

    fun refreshToken(refreshToken: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = ApiClient.apiService.refreshToken(
                    RefreshTokenRequest(refreshToken)
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        _authState.value = AuthState.Success(
                            ProfileLoginResponse(
                                accessToken = it.accessToken,
                                refreshToken = refreshToken, // Используем старый refresh token
                                userProfile = _userProfile.value
                            )
                        )
                    }
                } else {
                    _errorMessage.value = "Session expired. Please login again."
                    logout()
                }
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Обработка ответа аутентификации
    private fun handleAuthResponse(response: Response<*>) {
        if (response.isSuccessful) {
            when (val body = response.body()) {
                is AuthResponse -> {
                    // Для регистрации
                    _authState.value = AuthState.Success(
                        ProfileLoginResponse(
                            accessToken = body.accessToken,
                            refreshToken = body.refreshToken,
                            userProfile = null
                        )
                    )
                    fetchUserProfile(body.accessToken)
                }
                is ProfileLoginResponse -> {
                    // Для входа
                    _authState.value = AuthState.Success(body)
                    _userProfile.value = body.userProfile
                }
                else -> {
                    _authState.value = AuthState.Error("Unknown response format")
                }
            }
        } else {
            _authState.value = AuthState.Error(
                response.errorBody()?.string() ?: "Authentication failed"
            )
        }
    }

    // Обработка ошибок
    private fun handleError(exception: Exception) {
        val errorMsg = exception.message ?: "An unknown error occurred"
        _authState.value = AuthState.Error(errorMsg)
        _errorMessage.value = errorMsg
    }
}