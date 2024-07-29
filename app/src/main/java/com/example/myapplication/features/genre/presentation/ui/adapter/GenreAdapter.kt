package com.example.myapplication.features.genre.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.GenreItemBinding
import com.example.myapplication.features.genre.domain.model.Genre
import com.example.myapplication.features.movie.domain.model.Movie

class GenreAdapter (private var genres : List<Genre> = listOf())
    :RecyclerView.Adapter<GenreAdapter.GenreViewHolder>(){
    //region properties
    private lateinit var binding: GenreItemBinding
    inner class GenreViewHolder(private  val binding : GenreItemBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun bindData(genre : Genre){
            binding.genreName.text = genre.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        binding = GenreItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GenreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        if(genres.isNotEmpty())
            holder.bindData(genres.get(position))
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(genres: List<Genre>){
        if ( genres.isNotEmpty() ){
            this.genres = genres
            notifyDataSetChanged()
        }
    }
}