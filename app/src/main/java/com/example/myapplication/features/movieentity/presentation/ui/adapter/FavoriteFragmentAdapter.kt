package com.example.myapplication.features.movieentity.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.LastMovieItemBinding
import com.example.myapplication.features.movie.domain.model.Movie
import com.example.myapplication.features.movie.presentation.ui.adapter.HomeFragmentAdapter
import com.example.myapplication.features.movieentity.domain.model.MovieEntity
import com.example.myapplication.fragment.favorite.FavoriteFragment
import com.example.myapplication.shared_componenet.constants.Constants

class FavoriteFragmentAdapter(
        private val onMovieClickListener: FavoriteFragmentAdapter.OnMovieClickListener,
        private var movies: MutableList<MovieEntity> = mutableListOf())
    :RecyclerView.Adapter<FavoriteFragmentAdapter.FavoriteMovieViewHolder>(){

    private lateinit var binding: LastMovieItemBinding

    interface OnMovieClickListener{
        fun onMovieClick(movieId : Int)
    }
    inner class FavoriteMovieViewHolder(private val binding: LastMovieItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bindData(movie : MovieEntity){
            Glide.with(binding.root.context)
                .load(movie.poster)
                .into(binding.homePageMoviePoster)
            binding.homePageMovieTitle.text = movie.title
            binding.movieCountryText.text   = movie.country
            binding.movieRatingText.text    = movie.imdb_rating
            binding.movieYearText.text      = movie.year
            binding.linkTextView.text       = Constants.Home_Page_Movie_Link_Name
            binding.movieCountryIcon.setImageResource(R.drawable.country)
            binding.movieRatingIcon.setImageResource(R.drawable.star)
            binding.movieYearIcon.setImageResource(R.drawable.calendar)
            binding.link.setImageResource(R.drawable.link)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        binding = LastMovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteMovieViewHolder(binding)
    }
    override fun getItemCount(): Int = movies.size
    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        if(movies.isNotEmpty()) {
            holder.bindData(movies[position])
            holder.itemView.setOnClickListener{
                onMovieClickListener.onMovieClick(movies[position].id)
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(moviesResponce: MutableList<MovieEntity>) {
        if (moviesResponce.isNotEmpty()) {
            this.movies = moviesResponce
            notifyDataSetChanged()
        }
    }
}