package com.example.myapplication.features.movie.domain.repository

import com.example.myapplication.features.movie.domain.model.MoviesResponse
import com.example.myapplication.shared_componenet.api.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val api : APIService){

    suspend fun getMovies(): MoviesResponse {
        return withContext(Dispatchers.IO) {
            val response = api.getAllMovies()
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Response body is null")
            } else {
                throw Exception("API call failed with code ${response.code()}")
            }
        }
    }
}