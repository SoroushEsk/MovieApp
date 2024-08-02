package com.example.myapplication.utils.database.doe

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.shared_componenet.constants.Constants
import com.example.myapplication.features.movieentity.domain.model.MovieEntity

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
    fun getUser(userID: Int) : Int
}