package com.example.myapplication.features.genre.presentation.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.GenreItemBinding
import com.example.myapplication.features.genre.domain.model.Genre
import com.example.myapplication.features.movie.domain.model.Movie

class GenreAdapter (
        private val listener: GenreAdapter.OnGenreClickListener?,
        private var genres : List<Genre> = listOf())
    :RecyclerView.Adapter<GenreAdapter.GenreViewHolder>(){
    //region properties
    private lateinit var binding: GenreItemBinding
    private var genreSelected : MutableList<Boolean> = MutableList(genres.size){false}
    //endregion
    //region subClass
    inner class GenreViewHolder(val binding : GenreItemBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun bindData(genre : Genre, isSelected: Boolean){
            binding.genreName.text = genre.name
            binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    if (isSelected) R.color.light_gray_color else R.color.black
                )
            )
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
            holder.bindData(genres[position], genreSelected[position])
            holder.itemView.setOnClickListener{
                listener?.genreClick(genres[position])
                genreSelected[position] = !genreSelected[position]
                notifyItemChanged(position)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(genres: List<Genre>){
        if ( genres.isNotEmpty() ){
            var isSame = true
            if(genres.size == this.genres.size) {
                for (genre in 0 until genres.size) {
                    if (this.genres[genre].id != genres[genre].id)
                        isSame = false
                }
            }else isSame = false
            this.genres = genres
            if (!isSame)
                genreSelected = MutableList(genres.size){false}
            notifyDataSetChanged()
        }
    }
    //region State Management Methods
    fun saveState(): MutableList<Boolean> {
        return genreSelected
    }

    fun restoreState(savedState: MutableList<Boolean>) {
        genreSelected = savedState
        notifyDataSetChanged()
    }
    //endregion

}