package com.example.myapplication.utils.database.doe

import androidx.room.*
import com.example.myapplication.shared_componenet.constants.Constants
import com.example.myapplication.features.movieentity.domain.model.MovieEntity

@Dao
interface MovieDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    fun insertMovie( movie : MovieEntity)

    @Update
    fun updateMovie( movie : MovieEntity)

    @Delete
    fun deleteMovie( movie : MovieEntity)

    @Query("SELECT * FROM ${Constants.MovieTableName}")
    fun getAllMovies() : MutableList<MovieEntity>

    @Query("SELECT COUNT(1) FROM ${Constants.MovieTableName} WHERE ${Constants.MovieTableId}=:userID")
    fun isMovieExists(userID: Int) : Int
}