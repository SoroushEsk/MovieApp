package com.example.myapplication.features.movie.presentation.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.example.myapplication.R
import com.example.myapplication.databinding.MoviePageRecyclerBinding
import com.example.myapplication.features.movie.domain.model.Movie
import com.example.myapplication.features.movie.domain.model.MovieDetailResponse
import com.example.myapplication.features.movie.domain.model.MovieDetailed
import com.example.myapplication.shared_componenet.constants.Constants

class MoviePageAdapter(
    private val listener: MoviePageAdapter.onLikeButtonClick,
    private var movies: List<MovieDetailResponse> = listOf())
    :RecyclerView.Adapter<MoviePageAdapter.MovieDetailViewHolder>(){
    //region properties
    private lateinit var binding: MoviePageRecyclerBinding
    //endregion
    //region SubClasses
    companion object{
        const val posterRevealDuratin = 1000
    }
    interface onLikeButtonClick{
        fun isMovieExists(movie : MovieDetailResponse) : Boolean
        fun deleteMovie(movie : MovieDetailResponse)
        fun insertMovie(movie : MovieDetailResponse)
    }
    inner class MovieDetailViewHolder(private val binding: MoviePageRecyclerBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun bindData(movie : MovieDetailResponse, position: Int){
            Glide.with(binding.root.context)
                .load(movie.poster)
                .into(binding.posterTransparent)
            Glide.with(binding.root.context)
                .load(movie.poster)
                .transition(DrawableTransitionOptions.withCrossFade(posterRevealDuratin))
                .into(binding.moviePagePoster)
            if ( listener.isMovieExists(movie) ){
                ImageViewCompat.setImageTintList(
                    binding.likeButton,
                    ContextCompat.getColorStateList(binding.root.context, R.color.red)
                )
            }else {
                ImageViewCompat.setImageTintList(
                    binding.likeButton,
                    ContextCompat.getColorStateList(binding.root.context, R.color.app_gray_color)
                )
            }
            binding.backButton.setImageResource(R.drawable.previous)
            binding.likeButton.setImageResource(R.drawable.movie_like)
            binding.ratingIcon.setImageResource(R.drawable.star)
            binding.durationIcon.setImageResource(R.drawable.clock)
            binding.dateIcon.setImageResource(R.drawable.calendar)
            binding.ratingText.text = movie.imdb_rating
            binding.duratinAmount.text = movie.runtime
            binding.dateAmount.text = movie.released
            binding.summaryIcon.setImageResource(R.drawable.summary)
            binding.actorIcon.setImageResource(R.drawable.mask)
            binding.summaryDescription.text = movie.plot
            binding.actorTitle.text = Constants.ActorTitle
            binding.summaryTitle.text = Constants.SummaryTitle
            binding.movieActorList.text = movie.actors
            binding.likeButton.setOnClickListener {
                if ( listener.isMovieExists(movie) ){
                    ImageViewCompat.setImageTintList(
                        binding.likeButton,
                        ContextCompat.getColorStateList(binding.root.context, R.color.app_gray_color)
                    )
                    listener.deleteMovie(movie)
                }else {
                    ImageViewCompat.setImageTintList(
                        binding.likeButton,
                        ContextCompat.getColorStateList(binding.root.context, R.color.red)
                    )
                    listener.insertMovie(movie)
                }
            }
            val photosAdapter = MovieImageAdapter(movie.images)
            binding.moviePictureRecycler.adapter = photosAdapter
            binding.moviePictureRecycler.layoutManager =
                LinearLayoutManager(
                    binding.root.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
        }
    }
    //endregion
    //region Adapter Methods
    override fun getItemCount(): Int = movies.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailViewHolder {
        binding = MoviePageRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.backButton.setOnClickListener{
            (parent.context as? Activity)?.finish()
        }
        return MovieDetailViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MovieDetailViewHolder, position: Int) {
        holder.bindData(movies[position], position)

    }
    fun updateItem(movie: MovieDetailResponse, position : Int) {
        notifyItemChanged(position)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(movie: List<MovieDetailResponse>) {
        this.movies = movie
        notifyDataSetChanged()
    }
    //endregion
}