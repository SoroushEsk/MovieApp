package com.example.myapplication.features.genre.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication.features.genre.domain.model.GenreResponse
import com.example.myapplication.features.genre.domain.repository.GenreRepository
import com.example.myapplication.features.movie.domain.repository.MovieRepository
import com.example.myapplication.features.movie.presentation.viewmodel.MovieViewModel
import com.example.myapplication.shared_componenet.api.API
import kotlinx.coroutines.launch

class GenreViewModel (private val repository: GenreRepository) : ViewModel() {
    private val _genres = MutableLiveData<GenreResponse>()
    val genres: LiveData<GenreResponse> get() = _genres
    fun getAllGenres(){
        viewModelScope.launch {
            try{
                val response = repository.getGenres()
                _genres.postValue(response)
            }catch(e : Exception){
                Log.e("GenreViewModel", "Error fetching genres", e)
            }
        }
    }
}
class GenreViewModelFactory(): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GenreViewModel::class.java)) {
            return GenreViewModel(GenreModule.watchRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
class GenreModule{
    companion object {
        val watchRepository: GenreRepository by lazy {
            GenreRepository(API.apiService)
        }
    }
}