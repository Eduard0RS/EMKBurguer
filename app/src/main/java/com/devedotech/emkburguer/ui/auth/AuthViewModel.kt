package com.devedotech.emkburguer.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devedotech.emkburguer.data.local.DataStoreManager
import com.devedotech.emkburguer.data.model.LoginRequest
import com.devedotech.emkburguer.data.model.LoginResponse
import com.devedotech.emkburguer.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(context: Context) : ViewModel() {

    private val authRepository = AuthRepository()
    private val dataStoreManager = DataStoreManager(context)

    private val _rememberMe = MutableStateFlow(false)
    val rememberMe: StateFlow<Boolean> get() = _rememberMe

    init {
        viewModelScope.launch {
            dataStoreManager.rememberMeState.collect {
                _rememberMe.value = it
            }
        }
    }

    suspend fun getSavedCredentials(): Flow<Pair<String, String>> {
        return dataStoreManager.getCredentials
    }

    fun login(email: String, password: String, rememberMe: Boolean,onSuccess: (LoginResponse) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = authRepository.login(LoginRequest(email, password))
                if (rememberMe) {
                    dataStoreManager.saveCredentials(email,password)
                } else {
                    dataStoreManager.clearCredentials()
                }
                onSuccess(response)
            } catch (e: Exception) {
                onError(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun setRememberMe(rememberMe: Boolean,email: String, password: String) {
        viewModelScope.launch {
            dataStoreManager.saveRememberMeState(rememberMe)
            if (rememberMe) {
                dataStoreManager.saveCredentials(email,password)
            } else {
                dataStoreManager.clearCredentials()
            }
        }
    }
}


