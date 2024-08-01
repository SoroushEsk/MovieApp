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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.myapplication.R
import com.example.myapplication.databinding.MoviePageRecyclerBinding
import com.example.myapplication.features.movie.domain.model.Movie
import com.example.myapplication.features.movie.domain.model.MovieDetailed
import com.example.myapplication.shared_componenet.constants.Constants

class MoviePageAdapter(private var movies: List<MovieDetailed> = listOf())
    :RecyclerView.Adapter<MoviePageAdapter.MovieDetailViewHolder>(){
    //region properties
    private lateinit var binding: MoviePageRecyclerBinding
    //endregion
    //region SubClasses
    inner class MovieDetailViewHolder(private val binding: MoviePageRecyclerBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun bindData(movie : MovieDetailed){
            Glide.with(binding.root.context)
                .load(movie.poster)
                .into(binding.posterTransparent)
            Glide.with(binding.root.context)
                .load(movie.poster)
                .placeholder(R.drawable.a)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean
                    ): Boolean {
                        Log.e("Glide", "Image load failed: ${e?.message}")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
                    ): Boolean {
                        Log.d("Glide", "Image loaded successfully")
                        return false
                    }
                })
                .into(binding.moviePagePoster)

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
        binding.likeButton.setOnClickListener {
            // TODO("saving the movie in database")
        }
        return MovieDetailViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MovieDetailViewHolder, position: Int) {
        holder.bindData(movies[position])
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(movie: List<MovieDetailed>) {
        this.movies = movie
        notifyDataSetChanged()
    }
    //endregion
}