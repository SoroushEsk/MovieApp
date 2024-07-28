package com.example.myapplication.features.movie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication.features.movie.domain.model.MoviesResponse
import com.example.myapplication.features.movie.domain.repository.MovieRepository
import com.example.myapplication.features.token.domain.repository.TokenRepository
import com.example.myapplication.features.token.presentation.HomePageModule
import com.example.myapplication.features.token.presentation.TokenViewModel
import com.example.myapplication.shared_componenet.api.API
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movies = MutableLiveData<MoviesResponse>()
    val movies: LiveData<MoviesResponse> get() = _movies

    fun getAllMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getMovies()
                _movies.postValue(response)
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movies", e)
                // You might want to update a separate LiveData for errors here
            }
        }
    }
}
class MovieViewModelFactory(): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(MovieHomePageModule.watchRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
class MovieHomePageModule{
    companion object {
        val watchRepository: MovieRepository by lazy {
            MovieRepository(API.apiService)
        }
    }
}