package com.example.myapplication.shared_componenet.api

import com.example.myapplication.features.movie.domain.model.MovieDetailResponse
import com.example.myapplication.features.movie.domain.model.MoviesResponse
import com.example.myapplication.features.token.domain.model.RegisterRequest
import com.example.myapplication.features.token.domain.model.RequestResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {

    @POST("register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RequestResponse>

    @GET("movies")
    suspend fun getAllMovies(): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") id : Int): Response<MovieDetailResponse>

}


