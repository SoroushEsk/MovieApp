package com.example.myapplication.features.movieentity.domain.repository

import com.example.myapplication.features.movieentity.domain.model.MovieEntity
import com.example.myapplication.utils.database.doe.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviePageRepository (private val movieDao : MovieDao){
    suspend fun deleteMovie(movieEntity: MovieEntity){
        return withContext(Dispatchers.IO){
            movieDao.deleteMovie(movieEntity)
        }
    }
    suspend fun insertMovie(movieEntity: MovieEntity){
        return withContext(Dispatchers.IO){
            movieDao.insertMovie(movieEntity)
        }
    }
    suspend fun movieExists(movieId : Int) :Boolean{
        return withContext(Dispatchers.IO){
            val response = movieDao.isMovieExists(movieId)
            if ( response > 0 ) true
            else if ( response == 0 ) false
            else throw Exception("Is Movie Existed Wrong Output")
        }
    }
}