package com.example.myapplication.features.genre.domain.model

import com.example.myapplication.features.movie.domain.model.Metadata
import com.example.myapplication.features.movie.domain.model.Movie
import com.example.myapplication.shared_componenet.model.Description

data class GenreResponse(
    val status: Int,
    val description: Description,
    val data: List<Genre>
)
