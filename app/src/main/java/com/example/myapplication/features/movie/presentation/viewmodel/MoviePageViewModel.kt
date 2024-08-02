package com.example.myapplication.features.movie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication.features.movie.domain.model.MoviesResponse
import com.example.myapplication.features.movie.domain.repository.MoviesPageRepository
import com.example.myapplication.shared_componenet.api.API
import kotlinx.coroutines.launch

class MoviePageViewModel (private val repository: MoviesPageRepository) : ViewModel(){
    private val _movies = MutableLiveData<MoviesResponse>()
    val movies: LiveData<MoviesResponse> get() = _movies
    fun getMoviesByPage(pageNumber : Int){
        viewModelScope.launch {
            try{
                val response = repository.getMovieByPageNumber(pageNumber)
                _movies.postValue(response)
            } catch(e : Exception) {
                Log.e( "MovieByPage", "Error fetching movies", e)
            }
        }
    }
}
class MovieDetailedPageViewModelFactory(): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if ( modelClass.isAssignableFrom(MoviePageViewModel::class.java)){
            return MoviePageViewModel(MovieByPageModule.watchRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
class MovieByPageModule{
    companion object{
        val watchRepository: MoviesPageRepository by lazy {
            MoviesPageRepository(API.apiService)
        }
    }
}