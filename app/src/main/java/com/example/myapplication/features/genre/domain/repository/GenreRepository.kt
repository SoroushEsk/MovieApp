package com.example.myapplication.features.genre.domain.repository

import com.example.myapplication.features.genre.domain.model.GenreResponse
import com.example.myapplication.shared_componenet.api.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class GenreRepository (private val api : APIService){
    suspend fun getGenres(): GenreResponse{
        return withContext(Dispatchers.IO){
            val response = api.getAllGenres()
            if(response.isSuccessful){
                val genreResponse = response.body() ?: throw Exception("genre body is empty")
                genreResponse
            }else{
                throw Exception("API call failed with code ${response.code()}")
            }
        }
    }
}