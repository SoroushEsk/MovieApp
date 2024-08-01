package com.example.myapplication.moviedetail

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMoviePageBinding
import com.example.myapplication.features.movie.domain.model.MovieDetailed
import com.example.myapplication.features.movie.presentation.ui.adapter.MovieImageAdapter
import com.example.myapplication.features.movie.presentation.ui.adapter.MoviePageAdapter
import com.example.myapplication.features.movie.presentation.viewmodel.MovieDetailViewModelFactory
import com.example.myapplication.features.movie.presentation.viewmodel.MovieDetailedViewModel
import com.example.myapplication.shared_componenet.constants.Constants

class MoviePage : AppCompatActivity() {
    //region properties
    private lateinit var binding    : ActivityMoviePageBinding
    private lateinit var viewModel  : MovieDetailedViewModel
    private lateinit var pageAdapter: MoviePageAdapter
    //endregion
    //regin lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViewModel()
        initAdapter()
        viewModelConfig()
    }
    //endregion
    //region instance methods
    fun initBinding(){
        binding = ActivityMoviePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun initViewModel(){
        viewModel = ViewModelProvider(this, MovieDetailViewModelFactory())[MovieDetailedViewModel::class.java]
        viewModel.getMovieById(intent.getIntExtra(Constants.Intent_Movie_Id, 1))
    }
    fun viewModelConfig(){
        viewModel.movie.observe(this){movieDetail->
            pageAdapter.updateData(listOf(movieDetail.data))
        }
    }
    fun initAdapter(){
        pageAdapter = MoviePageAdapter()
        binding.moviePageRecycler.adapter = pageAdapter
        binding.moviePageRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
    //endregion
}