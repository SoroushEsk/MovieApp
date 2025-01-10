package com.example.myapplication.features.token.domain.model

import com.example.myapplication.shared_componenet.model.Description

data class RequestResponse(
    val userId: Int,
    val name: String,
    val email: String,
    val created_at: String,
    val updated_at: String
    )
data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val refresh_token: String?
)
