package com.devedotech.emkburguer.data.remote

import com.devedotech.emkburguer.data.model.LoginRequest
import com.devedotech.emkburguer.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}