package com.example.myapplication.features.movie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication.features.movie.domain.model.MoviesResponse
import com.example.myapplication.features.movie.domain.repository.MoviesPageRepository
import com.example.myapplication.features.movie.domain.repository.SearchMovieRepository
import com.example.myapplication.shared_componenet.api.API
import kotlinx.coroutines.launch

class SearchViewModel (private val repository: SearchMovieRepository)
    : ViewModel(){
    private val _movies = MutableLiveData<MoviesResponse>()
    val movies : LiveData<MoviesResponse> get() = _movies

    fun searchMovie(pageNumber : Int, keyWord : String){
        viewModelScope.launch {
            try{
                val response = repository.searchMovie(pageNumber, keyWord)
                _movies.postValue(response)
            }catch ( e : Exception){
                Log.e("SearchViewModel Error", "Error fetching the movie")
            }
        }
    }
}
class SearchMovieViewModelFactory(): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if ( modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(SearchViewHolderModule.watchRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
class SearchViewHolderModule{
    companion object{
        val watchRepository: SearchMovieRepository by lazy {
            SearchMovieRepository(API.apiService)
        }
    }
}