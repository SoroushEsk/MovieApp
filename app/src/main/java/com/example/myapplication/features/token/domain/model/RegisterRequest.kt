package com.example.myapplication.features.token.domain.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String
)
