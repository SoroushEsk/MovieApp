package com.example.myapplication.features.movieentity.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.*
import androidx.room.Room
import com.example.myapplication.features.movieentity.domain.model.MovieEntity
import com.example.myapplication.features.movieentity.domain.repository.FavoriteFragmentRepository
import com.example.myapplication.features.movieentity.domain.repository.MoviePageRepository
import com.example.myapplication.shared_componenet.constants.Constants
import com.example.myapplication.utils.database.MovieAppDatabase
import com.example.myapplication.utils.database.doe.MovieDao
import kotlinx.coroutines.launch

class FavoriteFragmentViewModel(private val repository: FavoriteFragmentRepository) : ViewModel() {
    private val _movies = MutableLiveData<MutableList<MovieEntity>>()
    val movies : LiveData<MutableList<MovieEntity>> get() = _movies

    fun getAllFavoriteMovies(){
        viewModelScope.launch{
            _movies.postValue(repository.getFavoriteMovies())
        }
    }
}

class FavoriteFragmentViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviePageViewModel::class.java)) {
            val module = FavoriteFragmentViewModelModule.getInstance(context)
            return FavoriteFragmentViewModel(module.favoriteFragmentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class FavoriteFragmentViewModelModule private constructor(context: Context) {
    companion object {
        @Volatile
        private var instance: FavoriteFragmentViewModelModule? = null

        fun getInstance(context: Context): FavoriteFragmentViewModelModule {
            return instance ?: synchronized(this) {
                instance ?: FavoriteFragmentViewModelModule(context.applicationContext).also {
                    instance = it
                }
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

    val favoriteFragmentRepository: FavoriteFragmentRepository by lazy {
        FavoriteFragmentRepository(movieDao)
    }
}