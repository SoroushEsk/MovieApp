package com.example.myapplication.features.movieentity.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.myapplication.features.movieentity.domain.model.MovieEntity
import com.example.myapplication.features.movieentity.domain.repository.MoviePageRepository
import com.example.myapplication.moviedetail.MoviePage
import com.example.myapplication.shared_componenet.constants.Constants
import com.example.myapplication.utils.database.MovieAppDatabase
import com.example.myapplication.utils.database.doe.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviePageViewModel(private val repository: MoviePageRepository)
    :ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status : LiveData<Boolean> get() = _status

    fun isMovieExists(movieId: Int){
        viewModelScope.launch {
            _status.postValue(repository.movieExists(movieId))
        }
    }

    fun insertMovie(movie: MovieEntity) {
        viewModelScope.launch {
            repository.insertMovie(movie)
        }
    }


    fun deleteMovie(movie: MovieEntity) {
        viewModelScope.launch {
            repository.deleteMovie(movie)
        }
    }
}

class MoviePageViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviePageViewModel::class.java)) {
            val module = MoviePageViewModelModule.getInstance(context)
            return MoviePageViewModel(module.moviePageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
class MoviePageViewModelModule private constructor(context: Context) {
    companion object {
        @Volatile
        private var instance: MoviePageViewModelModule? = null

        fun getInstance(context: Context): MoviePageViewModelModule {
            return instance ?: synchronized(this) {
                instance ?: MoviePageViewModelModule(context.applicationContext).also { instance = it }
            }
        }
    }

    private val database: MovieAppDatabase by lazy {
        Room.databaseBuilder(context, MovieAppDatabase::class.java, Constants.MovieAppDatabase)
            .fallbackToDestructiveMigration()
            .build()
    }

    private val movieDao: MovieDao by lazy {
        database.movieDao()
    }

    val moviePageRepository: MoviePageRepository by lazy {
        MoviePageRepository(movieDao)
    }
}