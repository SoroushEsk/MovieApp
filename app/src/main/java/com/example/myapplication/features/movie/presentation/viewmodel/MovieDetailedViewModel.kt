package com.example.myapplication.features.movie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication.features.movie.domain.model.MovieDetailResponse
import com.example.myapplication.features.movie.domain.repository.MovieDetailRepository
import com.example.myapplication.features.movie.domain.repository.MoviesRepository
import com.example.myapplication.shared_componenet.api.API
import kotlinx.coroutines.launch

class MovieDetailedViewModel (private val respository : MovieDetailRepository)
    :ViewModel(){
    private val _movie = MutableLiveData<MovieDetailResponse>()
    val movie : LiveData<MovieDetailResponse> get() = _movie
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState
    fun getMovieById(movieId : Int){
        viewModelScope.launch{
            try{
                val response = respository.getMovieById(movieId)
                _loadingState.postValue(true)
                _movie.postValue(response)
            }catch(e:Exception){
                _loadingState.postValue(false)
                Log.e("MovieDetailedViewModel", "Error Fetching movie", e)
            }
        }
    }
}
class MovieDetailViewModelFactory(): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailedViewModel::class.java)) {
            return MovieDetailedViewModel(MovieDetailedModule.watchRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
class MovieDetailedModule{
    companion object {
        val watchRepository: MovieDetailRepository by lazy {
            MovieDetailRepository(API.apiService)
        }
    }
}
