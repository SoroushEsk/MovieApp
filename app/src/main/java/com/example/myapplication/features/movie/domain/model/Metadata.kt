package com.example.myapplication.features.movie.domain.model

data class Metadata(
    val current_page: Int,
    val per_page: Int,
    val page_count: Int,
    val total_count: Int
)