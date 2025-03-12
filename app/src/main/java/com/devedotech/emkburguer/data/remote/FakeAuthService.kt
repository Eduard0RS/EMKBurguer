package com.devedotech.emkburguer.data.remote


import com.devedotech.emkburguer.data.model.LoginRequest
import com.devedotech.emkburguer.data.model.LoginResponse
import kotlinx.coroutines.delay
import java.util.UUID

class FakeAuthService : AuthService {
    override suspend fun login(request: LoginRequest): LoginResponse {
        delay(1000)

        return if (request.email == "teste@email.com" && request.password == "123456") {
            LoginResponse(
                token = UUID.randomUUID().toString(),
                refreshToken = UUID.randomUUID().toString(),
                userId = "123",
                role = "USER",
                expiresAt = System.currentTimeMillis() + 3600000
            )
        } else {
            throw Exception("Credenciais inválidas")
        }
    }
}