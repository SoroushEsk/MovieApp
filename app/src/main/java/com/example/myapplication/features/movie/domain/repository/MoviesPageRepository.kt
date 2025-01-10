package com.example.myapplication.features.movie.domain.repository

import com.example.myapplication.features.movie.domain.model.MoviesResponse
import com.example.myapplication.shared_componenet.api.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesPageRepository (private val api : APIService){
    suspend fun getMovieByPageNumber(pageNumber : Int) : MoviesResponse{
        return withContext(Dispatchers.IO){
            val response = api.getMoviePage(pageNumber)
            if ( response.isSuccessful){
                val moviesResponse = response.body() ?: throw Exception("Response Page ${pageNumber} body failed")
                val movieDetail    = moviesResponse.data.forEach{
                    val movieResponse = api.getMovie(it.id)
                    if ( movieResponse.isSuccessful ){
                        val movie = movieResponse.body() ?: throw Exception("One Moive Failed")
                        it.country = movie.country
                        it.imdb_rating = movie.imdb_rating
                        it.year = movie.year
                    }
                }
                moviesResponse
            }else {
                throw Exception("API call failed")
            }

        }
    }
}