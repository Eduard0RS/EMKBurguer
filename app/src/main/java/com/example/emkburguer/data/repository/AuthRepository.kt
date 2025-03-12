package com.example.emkburguer.data.repository

import com.example.emkburguer.data.model.LoginRequest
import com.example.emkburguer.data.model.LoginResponse
import com.example.emkburguer.data.remote.ApiClient
import com.example.emkburguer.data.remote.AuthService
import com.example.emkburguer.data.remote.FakeAuthService

class AuthRepository {
    private val authService = ApiClient.retrofit.create(AuthService::class.java)
    private val fakeAuthService = FakeAuthService()

    suspend fun login(request: LoginRequest): LoginResponse {
//        return authService.login(request)
        return fakeAuthService.login(request)
    }
}