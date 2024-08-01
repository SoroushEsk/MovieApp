package com.example.myapplication.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.features.genre.domain.model.Genre
import com.example.myapplication.features.genre.presentation.ui.adapter.GenreAdapter
import com.example.myapplication.features.genre.presentation.viewmodel.GenreViewModel
import com.example.myapplication.features.genre.presentation.viewmodel.GenreViewModelFactory
import com.example.myapplication.features.movie.domain.model.Movie
import com.example.myapplication.features.movie.presentation.ui.adapter.HomeFragmentAdapter
import com.example.myapplication.features.movie.presentation.ui.adapter.TopMovieSliderAdapter
import com.example.myapplication.features.movie.presentation.ui.scroll.EndlessRecyclerViewScrollListener
import com.example.myapplication.features.movie.presentation.viewmodel.*
import com.example.myapplication.moviedetail.MoviePage
import com.example.myapplication.shared_componenet.constants.Constants

class HomeFragment
    : Fragment(),
        HomeFragmentAdapter.OnMovieClickListener ,
        TopMovieSliderAdapter.OnSliderMovieClickListener,
        GenreAdapter.OnGenreClickListener {
    //region properties
    private lateinit var binding: FragmentHomeBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var pageMovieViewModel: MoviePageViewModel
    private lateinit var pageAdapter: HomeFragmentAdapter
    private lateinit var genreViewModel: GenreViewModel
    companion object{
        var MaxPageSize = 0
        var CurrentPageNumber = 1
    }
    //endregion
    //region lifecycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
        viewModelConfig()
        setupScrollListener()
    }
    //endregion
    //region initiation
    fun initViewModel() {
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory())[MovieViewModel::class.java]
        genreViewModel = ViewModelProvider(this, GenreViewModelFactory())[GenreViewModel::class.java]
        pageMovieViewModel = ViewModelProvider(this, MoviePageViewModelFactory())[MoviePageViewModel::class.java]
        movieViewModel.getAllMovies()
        genreViewModel.getAllGenres()
    }
    fun initAdapter() {
        pageAdapter = HomeFragmentAdapter()
        pageAdapter.setOnMovieClickListener(this)
        pageAdapter.setOnSliderClick(this)
        binding.homePageRecyclerView.adapter = pageAdapter
        binding.homePageRecyclerView.layoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
    //endregion
    //region methods
    override fun genreClick(genre: Genre) {
        TODO("Not yet implemented")
    }
    override fun onMovieClick(movie: Movie){
        val intent = Intent(requireContext(), MoviePage::class.java)
        intent.putExtra(Constants.Intent_Movie_Id, movie.id)
        startActivity(intent)
    }
    override fun onSliderMovieClick(movie: Movie) {
        val intent = Intent(requireContext(), MoviePage::class.java)
        intent.putExtra(Constants.Intent_Movie_Id, movie.id)
        startActivity(intent)
    }
    private fun viewModelConfig() {
        movieViewModel.movies.observe(viewLifecycleOwner) { movieList ->
            pageAdapter.updateMovies(movieList.data)
            MaxPageSize = movieList.metadata.page_count
            CurrentPageNumber = movieList.metadata.current_page
            pageAdapter.isLoading=false
            pageAdapter.isLoading=false
        }
        genreViewModel.genres.observe(viewLifecycleOwner) { genreList ->
            pageAdapter.updateGenres(genreList.data)
        }
        pageMovieViewModel.movies.observe(viewLifecycleOwner){movieList ->
            pageAdapter.updateMovies(movieList.data)
            pageAdapter.isLoading=false
        }
    }
    private fun setupScrollListener() {
        val layoutManager = binding.homePageRecyclerView.layoutManager as LinearLayoutManager
        binding.homePageRecyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun loadMoreItems() {
                pageAdapter.isLoading = true
                CurrentPageNumber++
                if (CurrentPageNumber <= MaxPageSize) {
                    pageMovieViewModel.getMoviesByPage(CurrentPageNumber)
                }
            }

            override fun isLastPage(): Boolean {
                return CurrentPageNumber == MaxPageSize
            }

            override fun isLoading(): Boolean {
                return pageAdapter.isLoading
            }
        })
    }
    //endregion
}