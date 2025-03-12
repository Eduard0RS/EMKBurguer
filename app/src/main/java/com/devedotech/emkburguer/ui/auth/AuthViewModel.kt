package com.devedotech.emkburguer.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devedotech.emkburguer.data.local.DataStoreManager
import com.devedotech.emkburguer.data.model.LoginRequest
import com.devedotech.emkburguer.data.model.LoginResponse
import com.devedotech.emkburguer.data.repository.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class AuthViewModel(context: Context) : ViewModel() {

    private val authRepository = AuthRepository()

    private var auth: FirebaseAuth = Firebase.auth

    private val dataStoreManager = DataStoreManager(context)

    private val _rememberMe = MutableStateFlow(false)
    val rememberMe: StateFlow<Boolean> get() = _rememberMe
    var currentUser: FirebaseUser? = null
    init {
        viewModelScope.launch {
            dataStoreManager.rememberMeState.collect {
                _rememberMe.value = it
            }
            currentUser = auth.currentUser
        }
    }

    fun getSavedCredentials(): Flow<Pair<String, String>> {
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

    fun loginWithFirebase(email: String, password: String, rememberMe: Boolean, onSuccess: (FirebaseUser) -> Unit, onError: (String) -> Unit) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (rememberMe) {
                            viewModelScope.launch {
                                dataStoreManager.saveCredentials(email, password)
                            }
                        } else {
                            viewModelScope.launch {
                                dataStoreManager.clearCredentials()
                            }
                        }
                        onSuccess(user ?: return@addOnCompleteListener)
                    } else {
                        val exception = task.exception
                        when (exception) {
                            is FirebaseAuthInvalidCredentialsException -> {
                                onError("Credenciais inválidas!")
                            }
                            is FirebaseAuthUserCollisionException -> {
                                onError("O usuário já existe!")
                            }
                            else -> {
                                onError(exception?.message ?: "Erro desconhecido")
                            }
                        }
                    }
                }
        } catch (e: Exception) {
            onError("Erro desconhecido: ${e.message}")
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


