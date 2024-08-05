package com.example.myapplication.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
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
    private var recyclerViewState: Parcelable? = null
    private var scrollPosition = 0
    private lateinit var binding: FragmentHomeBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var pageMovieViewModel: MoviePageViewModel
    private lateinit var pageAdapter: HomeFragmentAdapter
    private lateinit var genreViewModel: GenreViewModel
    private var movies : MutableList<Movie> = mutableListOf()
    private var genres : MutableList<String>   = mutableListOf()
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
        MaxPageSize = 0
        CurrentPageNumber = 1
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
        viewModelConfig()
        setupScrollListener()
        if (recyclerViewState != null) {
            binding.homePageRecyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
            (binding.homePageRecyclerView.layoutManager as LinearLayoutManager).scrollToPosition(scrollPosition)
        }
    }
    override fun onPause() {
        super.onPause()
        recyclerViewState = binding.homePageRecyclerView.layoutManager?.onSaveInstanceState()
        scrollPosition = (binding.homePageRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    }
    //endregion
    //region initiation
    private fun initViewModel() {
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory())[MovieViewModel::class.java]
        genreViewModel = ViewModelProvider(this, GenreViewModelFactory())[GenreViewModel::class.java]
        pageMovieViewModel = ViewModelProvider(this, MovieDetailedPageViewModelFactory())[MoviePageViewModel::class.java]
        movieViewModel.getAllMovies()
        genreViewModel.getAllGenres()
    }
    private fun initAdapter() {
        pageAdapter = HomeFragmentAdapter()
        pageAdapter.setOnMovieClickListener(this)
        pageAdapter.setOnSliderClick(this)
        pageAdapter.setOnGenreClick(this)
        binding.homePageRecyclerView.adapter = pageAdapter
        binding.homePageRecyclerView.layoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
    //endregion
    //region methods
    override fun genreClick(genre: Genre) {
        var tempMovies : MutableList<Movie> = mutableListOf()
        if ( genres.contains(genre.name) ){
            genres.remove(genre.name)
            if(genres.isEmpty())
                tempMovies = movies
            else
                for (movie in movies) {
                    for (genre in genres) {
                        if (movie.genres.contains(genre)) {
                            tempMovies.add(movie)
                            break
                        }
                    }
                }
        } else {
            genres.add(genre.name)
            for (movie in movies) {
                for (genre in genres) {
                    if (movie.genres.contains(genre)) {
                        tempMovies.add(movie)
                        break
                    }
                }
            }
        }
        pageAdapter.setMovies(tempMovies)
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
        if (movies.isEmpty()) {
            movieViewModel.movies.observe(viewLifecycleOwner) { movieList ->
                movies.addAll(movieList.data)
                pageAdapter.updateMovies(filterMovies(movieList.data))
                MaxPageSize = movieList.metadata.page_count
                CurrentPageNumber = movieList.metadata.current_page
                pageAdapter.isLoading = false
            }
            movieViewModel.getAllMovies()
        } else {
            pageAdapter.updateMovies(filterMovies(movies))
        }

        if (pageAdapter.isGenresEmpty()) {
            genreViewModel.genres.observe(viewLifecycleOwner) { genreList ->
                pageAdapter.updateGenres(genreList.data)
            }
            genreViewModel.getAllGenres()
        }

        pageMovieViewModel.movies.observe(viewLifecycleOwner) { movieList ->
            movies.addAll(movieList.data)
            pageAdapter.updateMovies(filterMovies(movieList.data))
            pageAdapter.isLoading = false
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
    private fun filterMovies(movies : List<Movie>) : MutableList<Movie>{
        if(genres.isEmpty()) return movies as MutableList<Movie>
        val result : MutableList<Movie> = mutableListOf()
        for (movie in movies) {
            for (genre in genres) {
                if (movie.genres.contains(genre)) {
                    result.add(movie)
                    break
                }
            }
        }
        return result
    }
    //endregion
}