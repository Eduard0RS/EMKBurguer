package com.example.emkburguer.data.model

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val phone: String?,
    val profileImage: String?,
    val company: CompanyProfile? = null
)