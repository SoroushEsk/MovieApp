package com.example.myapplication.utils.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.utils.database.doe.MovieDao
import com.example.myapplication.features.movieentity.domain.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieAppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}