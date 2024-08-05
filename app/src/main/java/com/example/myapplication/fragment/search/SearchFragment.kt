package com.example.myapplication.fragment.search

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.databinding.SearchMovieItemBinding
import com.example.myapplication.features.movie.domain.model.Movie
import com.example.myapplication.features.movie.presentation.ui.adapter.SearchRecyclerAdapter
import com.example.myapplication.features.movie.presentation.ui.scroll.EndlessRecyclerViewScrollListener
import com.example.myapplication.features.movie.presentation.viewmodel.SearchMovieViewModelFactory
import com.example.myapplication.features.movie.presentation.viewmodel.SearchViewModel
import com.example.myapplication.moviedetail.MoviePage
import com.example.myapplication.shared_componenet.constants.Constants
import kotlinx.coroutines.*
import kotlin.math.max

class SearchFragment : Fragment(), SearchRecyclerAdapter.OnMovieClickListener{
    //region properties
    private var recyclerViewState       : Parcelable? = null
    private var scrollPosition          : Int         = 0
    private var keyWord                 : String?     = null
    private val coroutineScope                        = CoroutineScope(Dispatchers.Main + Job())
    private var searchJob               : Job?        = null
    private var isNewKeyWord            : Boolean     = true
    private val debounceTime            : Long        = 300L
    private lateinit var binding        : FragmentSearchBinding
    private lateinit var viewModel      : SearchViewModel
    private lateinit var pageAdapter    : SearchRecyclerAdapter
    companion object{
        var maxPageSize       :Int = 0
        var currentPageNumber :Int = 1
    }
    //endregion
    //region lifecycle method
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        maxPageSize = 0
        currentPageNumber = 1
        keyWord = null
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
        viewModelConfig()
        initScrollLinstener()
        initRealTimeSearch()
        if (recyclerViewState != null) {
            binding.searchRecycler.layoutManager?.onRestoreInstanceState(recyclerViewState)
            (binding.searchRecycler.layoutManager as LinearLayoutManager).scrollToPosition(scrollPosition)
        }
    }
    override fun onPause() {
        super.onPause()
        recyclerViewState = binding.searchRecycler.layoutManager?.onSaveInstanceState()
        scrollPosition = (binding.searchRecycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    }
    //endregion
    //region override methods
    override fun onMovieClick(movie: Movie) {
        val intent = Intent(requireContext(), MoviePage::class.java)
        intent.putExtra(Constants.Intent_Movie_Id, movie.id)
        startActivity(intent)
    }
    //endregion
    //region initiation methods
    private fun initAdapter(){
        pageAdapter = SearchRecyclerAdapter(this)
        binding.searchRecycler.adapter = pageAdapter
        binding.searchRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
    private fun initViewModel(){
        viewModel  = ViewModelProvider(this, SearchMovieViewModelFactory())[SearchViewModel::class.java]
    }
    private fun initScrollLinstener(){
        val layoutManager:LinearLayoutManager =
            binding.searchRecycler.layoutManager as LinearLayoutManager
        binding.searchRecycler.addOnScrollListener(
            object : EndlessRecyclerViewScrollListener(layoutManager)
        {
            override fun loadMoreItems() {
                pageAdapter.isLoading = true
                currentPageNumber++
                Log.e("viewModel", "is running")
                if ( currentPageNumber <= maxPageSize && keyWord != null){
                    viewModel.searchMovie(currentPageNumber, keyWord!!)
                }
            }
            override fun isLastPage(): Boolean {
                return currentPageNumber == maxPageSize
            }
            override fun isLoading(): Boolean {
                return pageAdapter.isLoading
            }
        }
        )
    }
    private fun initRealTimeSearch(){
        val searchEditText = binding.searchTextInput
        searchEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    delay(debounceTime)
                    s?.toString()?.let{searchQuery->
                        performSearch(searchQuery)
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }
    //endregion
    //region function
    private fun viewModelConfig(){
        viewModel.movies.observe(viewLifecycleOwner){movieResponse->

            if(isNewKeyWord) {
                pageAdapter.setMovies(movieResponse.data)
                isNewKeyWord = false
            }else pageAdapter.updateMovies(movieResponse.data)
            pageAdapter.isLoading = false
            maxPageSize = movieResponse.metadata.page_count
            currentPageNumber = movieResponse.metadata.current_page
        }
    }
    private fun performSearch(query : String){
        currentPageNumber = 1
        isNewKeyWord = true
        keyWord = query
        viewModel.searchMovie(currentPageNumber++, query)}
    //endregion
}