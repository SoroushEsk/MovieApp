package com.example.myapplication.features.movieentity.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.shared_componenet.constants.Constants

@Entity(tableName = Constants.MovieTableName)
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = Constants.MovieTableId)val id : Int,

    @ColumnInfo(name = Constants.MovieTableTitle)
    val title       : String,

    @ColumnInfo(name = Constants.MovieTablePoster)
    val poster      : String,

    @ColumnInfo(name = Constants.MovieTableCountry)
    val country     : String,

    @ColumnInfo(name = Constants.MovieTableImdbRating)
    val imdb_rating : String,

    @ColumnInfo(name = Constants.MovieTableYear)
    val year        : String
)
