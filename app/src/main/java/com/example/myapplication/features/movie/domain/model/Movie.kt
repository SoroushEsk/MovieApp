package com.example.myapplication.features.movie.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val year: String,
    val country: String,
    val imdb_rating: String,
    val poster: String,
    val genres: List<String>,
    val images: List<String>
)