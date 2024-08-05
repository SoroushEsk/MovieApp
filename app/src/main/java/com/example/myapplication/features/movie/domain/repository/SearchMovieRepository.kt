package com.example.myapplication.features.movie.domain.repository

import com.example.myapplication.features.movie.domain.model.MoviesResponse
import com.example.myapplication.shared_componenet.api.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchMovieRepository(private val api : APIService) {
    suspend fun searchMovie(pageNumber : Int , keyWord : String) : MoviesResponse{
        return withContext(Dispatchers.IO){
            val response  = api.searchMovies(pageNumber, keyWord)
            if ( response.isSuccessful ){
                response.body() ?: throw Exception("Search Body Error")
            } else {
                throw Exception("Search Incomplete")
            }
        }
    }
}