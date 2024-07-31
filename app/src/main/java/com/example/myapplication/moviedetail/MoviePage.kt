package com.example.myapplication.moviedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMoviePageBinding

class MoviePage : AppCompatActivity() {
    //region properties
    private lateinit var binding : ActivityMoviePageBinding
    //endregion
    //regin lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }
    //endregion
    //region instance methods
    fun initBinding(){
        binding = ActivityMoviePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    //endregion
}