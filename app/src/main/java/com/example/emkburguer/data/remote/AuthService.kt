package com.example.emkburguer.data.remote

import com.example.emkburguer.data.model.LoginRequest
import com.example.emkburguer.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}