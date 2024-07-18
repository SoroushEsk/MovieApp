package com.example.myapplication.features.token.presentation

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.features.token.domain.model.RegisterRequest
import com.example.myapplication.features.token.domain.model.RequestResponse
import com.example.myapplication.features.token.domain.model.UserData
import com.example.myapplication.features.token.domain.repository.TokenRepository
import com.example.myapplication.shared_componenet.api.API
import kotlinx.coroutines.launch
import retrofit2.Response

class TokenViewModel(private val repository: TokenRepository) : ViewModel() {
    private val _registerResponse = MutableLiveData<Response<RequestResponse>>()
    val registerResponse: LiveData<Response<RequestResponse>> get() = _registerResponse


    fun registerUser(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            val response = repository.registerUser(registerRequest)
            _registerResponse.postValue(response)

            if (response.isSuccessful) {
                _registerResponse.postValue(response)
            }
        }
    }

}
class TokenViewModelFactory() :ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TokenViewModel::class.java)) {
            return TokenViewModel(HomePageModule.watchRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
class HomePageModule {
    companion object {
        val watchRepository: TokenRepository by lazy {
            TokenRepository(API.apiService)
        }
    }
}