package com.example.myapplication.features.movie.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.MovieImageItemBinding

class MovieImageAdapter (private var photos : List<String>? = listOf())
    :RecyclerView.Adapter<MovieImageAdapter.PhotoViewHolder>(){
    //region properties
    private lateinit var binding: MovieImageItemBinding
    //endregion
    //region inner class
    inner class PhotoViewHolder(private val binding: MovieImageItemBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bindData(photoURL : String){
                Glide.with(binding.root.context)
                    .load(photoURL)
                    .into(binding.movieImage)
            }
        }
    //endregion
    //region Adapter methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        binding = MovieImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhotoViewHolder(binding)
    }
    override fun getItemCount():Int = photos?.size ?: 0
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        if (photos?.isNotEmpty() == true)
            holder.bindData(photos!![position])
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(photos : List<String>){
        if( photos.isNotEmpty() )
            this.photos = photos
        notifyDataSetChanged()
    }
    //endregion
}