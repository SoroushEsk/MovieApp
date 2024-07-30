package com.example.myapplication.features.movie.domain.repository

import com.example.myapplication.features.movie.domain.model.MoviesResponse
import com.example.myapplication.shared_componenet.api.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(private val api : APIService){

    suspend fun getMovies(): MoviesResponse {
        return withContext(Dispatchers.IO) {
            val response = api.getAllMovies()
            if (response.isSuccessful) {
                val movieResponse = response.body() ?: throw Exception("Response body is null")
                val movieDetail = movieResponse.data.forEach{
                    val responseUnique = api.getMovie(it.id)
                    if(responseUnique.isSuccessful){
                        val movie = responseUnique.body() ?: throw Exception("Only One Movie Detail Failed")
                        it.country = movie.data.country
                        it.imdb_rating = movie.data.imdb_rating
                        it.year = movie.data.year
                    }
                }

                movieResponse
            } else {
                throw Exception("API call failed with code ${response.code()}")
            }
        }
    }
}