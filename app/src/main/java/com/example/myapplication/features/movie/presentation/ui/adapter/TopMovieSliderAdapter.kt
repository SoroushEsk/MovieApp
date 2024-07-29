package com.example.myapplication.features.movie.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.TopMovieViewBinding
import com.example.myapplication.features.movie.domain.model.Movie
import com.example.myapplication.features.movie.domain.model.MoviesResponse
import com.example.myapplication.shared_componenet.constants.Constants

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
            binding.topMovieName.text = movie.title
            binding.topMovieCountry.text = movie.country
            binding.topMovieRating.text = movie.imdb_rating
            binding.topMovieYear.text = movie.year
            binding.ratingStartIcon.setImageResource(R.drawable.star)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): TopMovieViewHolder {
        binding = TopMovieViewBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return TopMovieViewHolder(binding)
    }

    override fun getItemCount(): Int = Constants.Top_Movie_Size

    override fun onBindViewHolder(holder : TopMovieViewHolder, position: Int) {
        if (movies.isNotEmpty()) {
            holder.bindData(movies.get(position))
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(moviesResponce: List<Movie>){
        if ( moviesResponce.isNotEmpty() ){
            this.movies = moviesResponce
            notifyDataSetChanged()
        }
    }
}