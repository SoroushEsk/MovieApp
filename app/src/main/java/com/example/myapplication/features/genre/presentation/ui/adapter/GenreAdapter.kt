package com.example.myapplication.features.genre.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.GenreItemBinding
import com.example.myapplication.features.genre.domain.model.Genre
import com.example.myapplication.features.movie.domain.model.Movie

class GenreAdapter (
        private val listener: GenreAdapter.OnGenreClickListener?,
        private var genres : List<Genre> = listOf())
    :RecyclerView.Adapter<GenreAdapter.GenreViewHolder>(){
    //region properties
    private lateinit var binding: GenreItemBinding
    //endregion
    //region subClass
    inner class GenreViewHolder(private  val binding : GenreItemBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun bindData(genre : Genre){
            binding.genreName.text = genre.name
        }
    }
    interface OnGenreClickListener{
        fun genreClick(genre : Genre)
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
        if(genres.isNotEmpty()) {
            holder.bindData(genres.get(position))
            holder.itemView.setOnClickListener{
                listener?.genreClick(genres[position])
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(genres: List<Genre>){
        if ( genres.isNotEmpty() ){
            this.genres = genres
            notifyDataSetChanged()
        }
    }
}