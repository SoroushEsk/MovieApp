package com.example.myapplication.shared_componenet.api

import com.example.myapplication.features.genre.domain.model.GenreResponse
import com.example.myapplication.features.movie.domain.model.MovieDetailResponse
import com.example.myapplication.features.movie.domain.model.MoviesResponse
import com.example.myapplication.features.token.domain.model.RegisterRequest
import com.example.myapplication.features.token.domain.model.RequestResponse
import com.example.myapplication.features.token.domain.model.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @POST("api/v1/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RequestResponse>

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun getToken(
        @Field("grant_type") grantType: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<TokenResponse>

    @GET("api/v1/movies")
    suspend fun getAllMovies(): Response<MoviesResponse>

    @GET("api/v1/movies/{movie_id}")
    suspend fun getMovie(@Path("movie_id") id : Int): Response<MovieDetailResponse>

    @GET("api/v1/genres")
    suspend fun  getAllGenres() : Response<GenreResponse>

    @GET("api/v1/movies")
    suspend fun getMoviePage(@Query("page") page: Int) : Response<MoviesResponse>


    @GET("api/v1/movies")
    suspend fun searchMovies(@Query("q") name: String, @Query("page") page: Int): Response<MoviesResponse>
}

