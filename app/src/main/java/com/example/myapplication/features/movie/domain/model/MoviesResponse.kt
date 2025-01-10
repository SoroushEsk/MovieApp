package com.example.myapplication.features.movie.domain.model

import com.example.myapplication.shared_componenet.model.Description
data class MoviesResponse(
    val data: List<Movie>,
    val metadata: Metadata
)