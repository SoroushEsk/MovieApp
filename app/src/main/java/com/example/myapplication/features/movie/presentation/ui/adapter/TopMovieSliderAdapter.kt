package com.example.myapplication.features.movie.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.TopMovieViewBinding
import com.example.myapplication.features.movie.domain.model.Movie
import com.example.myapplication.features.movie.domain.model.MoviesResponse

class TopMovieSliderAdapter (private var movies : List<Movie> = listOf()) :
        RecyclerView.Adapter<TopMovieSliderAdapter.TopMovieViewHolder>()
{
    private lateinit var binding: TopMovieViewBinding
    inner class TopMovieViewHolder(private val binding : TopMovieViewBinding) :
            RecyclerView.ViewHolder(binding.root){
        fun bindData(movie : Movie){
            Glide.with(binding.root.context)
                .load(movie.poster)
                .into(binding.moviePoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): TopMovieViewHolder {
        binding = TopMovieViewBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return TopMovieViewHolder(binding)
    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder : TopMovieViewHolder, position: Int) {
        if (movies.isNotEmpty())
            holder.bindData(movies.get(position))
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(moviesResponce: List<Movie>){
        if ( moviesResponce.isNotEmpty() ){
            this.movies = moviesResponce
            notifyDataSetChanged()
        }
    }
}