package com.example.myapplication.features.movie.domain.repository

import com.example.myapplication.shared_componenet.api.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesPageRepository (private val api : APIService){
    suspend fun getMovieByPageNumber(pageNumber : Int){
        return withContext(Dispatchers.IO){
            val response = api.getMoviePage(pageNumber)
            if ( response.isSuccessful){
                val moviesResponse = response.body() ?: throw Exception("Response Page ${pageNumber} body failed")
                val movieDetail    = moviesResponse.data.forEach{
                    val movieResponse = api.getMovie(it.id)
                    if ( movieResponse.isSuccessful ){
                        val movie = movieResponse.body() ?: throw Exception("One Moive Failed")
                        it.country = movie.data.country
                        it.imdb_rating = movie.data.imdb_rating
                        it.year = movie.data.year
                    }
                }
                moviesResponse
            }else {
                throw Exception("API call failed")
            }

        }
    }
}