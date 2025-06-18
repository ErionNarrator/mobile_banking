package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AccountSearchResult
import com.example.myapplication.data.TransferRequest
import com.example.myapplication.data.TransferResponse
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransferViewModel(
    private val apiService: ApiService,
    private val authToken: String
) : ViewModel() {

    private val _transferState = MutableStateFlow<TransferState>(TransferState.Idle)
    val transferState: StateFlow<TransferState> = _transferState

    private val _searchResults = MutableStateFlow<List<AccountSearchResult>>(emptyList())
    val searchResults: StateFlow<List<AccountSearchResult>> = _searchResults

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun setErrorMessage(message: String) {
        _errorMessage.value = message
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    sealed class TransferState {
        object Idle : TransferState()
        object Loading : TransferState()
        data class Success(val response: TransferResponse) : TransferState()
        data class Error(val message: String) : TransferState()
    }

    fun searchAccounts(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = apiService.searchAccounts(
                    token = "Bearer $authToken",
                    query = query
                )

                if (response.isSuccessful) {
                    _searchResults.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value = response.errorBody()?.string() ?: "Ошибка поиска"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка сети"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun transfer(
        recipientId: Int?,
        recipientAccountNumber: String?,
        amount: Double,
        currencyCode: String,
        description: String?
    ) {
        viewModelScope.launch {
            _transferState.value = TransferState.Loading
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = apiService.transfer(
                    token = "Bearer $authToken",
                    request = TransferRequest(
                        recipientId = recipientId,
                        recipientAccountNumber = recipientAccountNumber,
                        amount = amount,
                        currencyCode = currencyCode,
                        description = description
                    )
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        _transferState.value = TransferState.Success(it)
                    } ?: run {
                        _transferState.value = TransferState.Error("Пустой ответ от сервера")
                    }
                } else {
                    val error = response.errorBody()?.string() ?: "Ошибка перевода"
                    _transferState.value = TransferState.Error(error)
                    _errorMessage.value = error
                }
            } catch (e: Exception) {
                val error = e.message ?: "Неизвестная ошибка"
                _transferState.value = TransferState.Error(error)
                _errorMessage.value = error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetState() {
        _transferState.value = TransferState.Idle
    }
}