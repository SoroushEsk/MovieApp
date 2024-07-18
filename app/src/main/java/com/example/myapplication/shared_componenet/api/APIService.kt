package com.example.myapplication.shared_componenet.api

import com.example.myapplication.features.token.domain.model.RegisterRequest
import com.example.myapplication.features.token.domain.model.RequestResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {

    @POST("register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RequestResponse>

}


