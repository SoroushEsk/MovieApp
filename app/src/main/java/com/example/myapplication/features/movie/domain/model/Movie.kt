package com.example.myapplication.features.movie.domain.model

data class Movie(
    val id: Int,
    val title: String,
    var year: String,
    var country: String,
    var imdb_rating: String,
    val poster: String,
    val genres: List<String>,
    val images: List<String>
)