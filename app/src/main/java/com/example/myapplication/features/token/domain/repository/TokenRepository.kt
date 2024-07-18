package com.example.myapplication.features.token.domain.repository

import com.example.myapplication.features.token.domain.model.RegisterRequest
import com.example.myapplication.features.token.domain.model.RequestResponse
import com.example.myapplication.shared_componenet.api.APIService
import retrofit2.Response


class TokenRepository(private val api : APIService) {

    suspend fun registerUser(registerRequest: RegisterRequest): Response<RequestResponse> {
        return api.registerUser(registerRequest)
    }
}