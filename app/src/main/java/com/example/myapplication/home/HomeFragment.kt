package com.example.myapplication.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.features.movie.presentation.ui.adapter.TopMovieSliderAdapter
import com.example.myapplication.features.movie.presentation.viewmodel.MovieViewModel
import com.example.myapplication.features.movie.presentation.viewmodel.MovieViewModelFactory

class HomeFragment : Fragment() {

    //region properties

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var topMovieAdapter: TopMovieSliderAdapter


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
    }
    //endregion
    //region initiation
    fun initBinding() {
//        binding = FragmentHomeBinding.inflate(inflater, container, false)
    }

    fun initViewModel() {
        viewModel = ViewModelProvider(this, MovieViewModelFactory())[MovieViewModel::class.java]

        viewModel.getAllMovies()
    }

    fun initAdapter() {
        topMovieAdapter = TopMovieSliderAdapter()
    }

    //endregion
    //region method
    private fun viewModelConfig() {
        viewModel.movies.observe(viewLifecycleOwner) { movieList ->
            topMovieAdapter.updateData(movieList.data)
        }

        binding.topViewPager.adapter = topMovieAdapter

    }
    //endregion
}