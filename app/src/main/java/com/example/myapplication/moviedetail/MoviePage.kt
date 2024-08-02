package com.example.myapplication.moviedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMoviePageBinding
import com.example.myapplication.features.movie.domain.model.MovieDetailed
import com.example.myapplication.features.movie.presentation.ui.adapter.MoviePageAdapter
import com.example.myapplication.features.movie.presentation.viewmodel.MovieDetailViewModelFactory
import com.example.myapplication.features.movie.presentation.viewmodel.MovieDetailedViewModel
import com.example.myapplication.features.movie.presentation.viewmodel.MovieDetailedPageViewModelFactory
import com.example.myapplication.features.movieentity.domain.model.MovieEntity
import com.example.myapplication.features.movieentity.presentation.viewmodel.MoviePageViewModel
import com.example.myapplication.features.movieentity.presentation.viewmodel.MoviePageViewModelFactory
import com.example.myapplication.shared_componenet.constants.Constants

class MoviePage : AppCompatActivity(), MoviePageAdapter.onLikeButtonClick {
    //region properties
    private lateinit var binding            : ActivityMoviePageBinding
    private lateinit var viewModel          : MovieDetailedViewModel
    private lateinit var roomDBViewModel    : MoviePageViewModel
    private lateinit var pageAdapter        : MoviePageAdapter
    private          var isMovieInDatabase  : Boolean = false
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
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
    }
    fun initViewModel(){
        val movieId : Int = intent.getIntExtra(Constants.Intent_Movie_Id, 1)
        viewModel = ViewModelProvider(this, MovieDetailViewModelFactory())[MovieDetailedViewModel::class.java]
        viewModel.getMovieById(movieId)
        roomDBViewModel = ViewModelProvider(this, MoviePageViewModelFactory(this.applicationContext))[MoviePageViewModel::class.java]
        roomDBViewModel.isMovieExists(movieId)
    }
    fun viewModelConfig(){
        viewModel.movie.observe(this){movieDetail->
            pageAdapter.updateData(listOf(movieDetail.data))
        }
        roomDBViewModel.status.observe(this){movieStatus->
            isMovieInDatabase = movieStatus
        }
    }
    fun initAdapter(){
        pageAdapter = MoviePageAdapter(this)
        binding.moviePageRecycler.adapter = pageAdapter
        binding.moviePageRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    )
        }
    }
    //endregion
    //region adapter methods
    override fun isMovieExists(movie: MovieDetailed): Boolean = isMovieInDatabase
    override fun deleteMovie(movie: MovieDetailed) {
        roomDBViewModel.deleteMovie(MovieEntity(movie.id, movie.title, movie.poster, movie.country, movie.imdb_rating, movie.year))
    }
    override fun insertMovie(movie: MovieDetailed) {
        roomDBViewModel.insertMovie(MovieEntity(movie.id, movie.title, movie.poster, movie.country, movie.imdb_rating, movie.year))
    }
    //endregion
}