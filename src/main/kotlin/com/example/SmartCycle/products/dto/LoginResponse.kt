package com.example.SmartCycle.products.dto

import com.example.SmartCycle.products.entities.User

data class LoginResponse(
    val user: User?,
    val token: String
)