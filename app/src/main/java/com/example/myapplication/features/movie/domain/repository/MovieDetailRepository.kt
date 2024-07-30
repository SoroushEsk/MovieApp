package com.example.myapplication.features.movie.domain.repository

import com.example.myapplication.shared_componenet.api.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailRepository(private val api : APIService) {
    suspend fun getMovieById(movieId : Int){
        return withContext(Dispatchers.IO){
            val response = api.getMovie(movieId)
            if(response.isSuccessful){
                response.body() ?: throw Exception("Only One Movie Detail Failed")
            }else {
                throw Exception("Movie Not Received ${response.code()}")
            }
        }
    }
}