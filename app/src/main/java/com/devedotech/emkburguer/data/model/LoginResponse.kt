package com.devedotech.emkburguer.data.model

data class LoginResponse(
    val token: String,
    val refreshToken: String,
    val userId: String,
    val role: String,
    val expiresAt: Long
)