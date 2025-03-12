package com.devedotech.emkburguer.data.model

data class CompanyProfile(
    val id: String,
    val name: String,
    val cnpj: String,
    val address: String,
    val logoUrl: String?
)
