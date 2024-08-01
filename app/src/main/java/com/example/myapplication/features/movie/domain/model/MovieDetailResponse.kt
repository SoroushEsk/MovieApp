package com.example.myapplication.features.movie.domain.model

import com.example.myapplication.shared_componenet.model.Description

data class MovieDetailResponse(
    val status: Int,
    val description: Description,
    val data: MovieDetailed
)
