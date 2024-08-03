package com.example.myapplication.fragment.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteBinding
import com.example.myapplication.features.movieentity.presentation.ui.adapter.FavoriteFragmentAdapter
import com.example.myapplication.features.movieentity.presentation.viewmodel.FavoriteFragmentViewModel
import com.example.myapplication.features.movieentity.presentation.viewmodel.FavoriteFragmentViewModelFactory
import com.example.myapplication.moviedetail.MoviePage
import com.example.myapplication.shared_componenet.constants.Constants

class FavoriteFragment : Fragment(), FavoriteFragmentAdapter.OnMovieClickListener {

    private lateinit var fragmentAdapter: FavoriteFragmentAdapter
    private lateinit var viewModel      : FavoriteFragmentViewModel
    private lateinit var binding        : FragmentFavoriteBinding
    //region lifecycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
        viewModelConfig()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllFavoriteMovies()
    }
    //endregion
    //region methods
    private fun initViewModel(){
        viewModel = ViewModelProvider(this, FavoriteFragmentViewModelFactory(this.requireContext()))[FavoriteFragmentViewModel::class.java]
        viewModel.getAllFavoriteMovies()
    }
    private fun initAdapter(){
        fragmentAdapter = FavoriteFragmentAdapter(this)
        binding.movieRecycler.adapter = fragmentAdapter
        binding.movieRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
    private fun viewModelConfig(){
        viewModel.movies.observe(viewLifecycleOwner){ movieList->
            if (movieList.isEmpty()){
                binding.movieRecycler.visibility = View.GONE
                binding.loadAnimation.visibility = View.VISIBLE
            }else{
                movieList.sortByDescending { it.createdAt }
                fragmentAdapter.updateMovies(movieList)
                binding.movieRecycler.visibility = View.VISIBLE
                binding.loadAnimation.visibility = View.GONE
            }
        }
    }
    //endregion
    //region override methods
    override fun onMovieClick(movieId: Int) {
        val intent = Intent(requireContext(), MoviePage::class.java)
        intent.putExtra(Constants.Intent_Movie_Id, movieId)
        startActivity(intent)
    }
    //endregion
}