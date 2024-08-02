package com.example.myapplication.features.movieentity.domain.repository

import com.example.myapplication.features.movieentity.domain.model.MovieEntity
import com.example.myapplication.utils.database.doe.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteFragmentRepository (private val movieDao : MovieDao){
    suspend fun getFavoriteMovies():MutableList<MovieEntity>{
        return withContext(Dispatchers.IO){
            movieDao.getAllMovies()
        }
    }
}