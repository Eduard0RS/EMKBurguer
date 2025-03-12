package com.devedotech.emkburguer.data.repository

import com.devedotech.emkburguer.data.model.LoginRequest
import com.devedotech.emkburguer.data.model.LoginResponse
import com.devedotech.emkburguer.data.remote.ApiClient
import com.devedotech.emkburguer.data.remote.AuthService
import com.devedotech.emkburguer.data.remote.FakeAuthService

class AuthRepository {
    private val authService = ApiClient.retrofit.create(AuthService::class.java)
    private val fakeAuthService = FakeAuthService()

    suspend fun login(request: LoginRequest): LoginResponse {
//        return authService.login(request)
        return fakeAuthService.login(request)
    }
}