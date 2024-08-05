package com.example.myapplication.features.movie.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.SearchMovieItemBinding
import com.example.myapplication.features.movie.domain.model.Movie
import com.example.myapplication.shared_componenet.constants.Constants

class SearchRecyclerAdapter(
    private val onMovieClickListener: SearchRecyclerAdapter.OnMovieClickListener,
    private var movies              : MutableList<Movie> = mutableListOf()
):RecyclerView.Adapter<SearchRecyclerAdapter.SearchMovieItemViewHolder>(){
    //region properties
    private lateinit var binding : SearchMovieItemBinding
    var isLoading                : Boolean = false
    //endregion
    //region sub classes
    interface OnMovieClickListener{
        fun onMovieClick(movie : Movie)
    }
    inner class SearchMovieItemViewHolder(
        private val binding : SearchMovieItemBinding
    ) :RecyclerView.ViewHolder(binding.root){
        fun bindData(movie : Movie){
            Glide.with(binding.root.context)
                .load(movie.poster)
                .into(binding.posterFadeBackground)
            Glide.with(binding.root.context)
                .load(movie.poster)
                .into(binding.imagePoster)
            binding.link.setImageResource(R.drawable.link)
            binding.linkTextView.text = Constants.Home_Page_Movie_Link_Name
            binding.searchMovieTitle.text = movie.title
        }
    }
    //endregion
    //region adapter methods
    override fun getItemCount(): Int = movies.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieItemViewHolder {
        binding = SearchMovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchMovieItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchMovieItemViewHolder, position: Int) {
        if( movies.isNotEmpty() ){
            holder.bindData(movies[position])
            holder.itemView.setOnClickListener{
                onMovieClickListener.onMovieClick(movies[position])
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(moviesResponce: List<Movie>) {
        if (moviesResponce.isNotEmpty()) {
            this.movies.addAll(moviesResponce)
            notifyDataSetChanged()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(moviesResponce: List<Movie>) {
        if (moviesResponce.isNotEmpty()) {
            this.movies = moviesResponce as MutableList<Movie>
            notifyDataSetChanged()
        }
    }
    //endregion
}