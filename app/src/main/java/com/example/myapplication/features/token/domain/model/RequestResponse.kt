package com.example.myapplication.features.token.domain.model

import com.example.myapplication.shared_componenet.model.Description

data class RequestResponse(
    val status: Int,
    val description: Description,
    val data: UserData
    )
