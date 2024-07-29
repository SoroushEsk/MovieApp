package com.example.myapplication.features.movie.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.marginEnd
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.HomePageTitleItemBinding
import com.example.myapplication.databinding.TopMovieSliderViewBinding
import com.example.myapplication.databinding.TopMovieViewBinding
import com.example.myapplication.features.movie.domain.model.Movie
import com.example.myapplication.shared_componenet.constants.Constants

class HomeFragmentAdapter(private var movies: List<Movie> = listOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //region properties
    private lateinit var sliderBinding          : TopMovieSliderViewBinding
    private lateinit var sliderAdapter          : TopMovieSliderAdapter
    private lateinit var genreTitleBinding      : HomePageTitleItemBinding
    private lateinit var onPageChangeListener   : ViewPager2.OnPageChangeCallback
    private val layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8, 0, 8, 8)
    }

    //endregion
    //region inner(object/class)
    companion object {
        private const val NUMBER_OF_RECYCLERS = 4
        private const val TOP_MOVIE_VIEW_PAGER = 0
        private const val GENRE_TITLE = 1
        private const val GENRE_RECYCLER_VIEW = 2
        private const val LAST_MOVIE_TITLE = 3
        private const val LAST_MOVIE_RECYCLER = 4
    }

    inner class TopMovieSliderViewHolder(
        private val binding: TopMovieSliderViewBinding,
        private val sliderAdapter: TopMovieSliderAdapter,
        private val parent: ViewGroup
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData() {
            binding.topViewPager.adapter = sliderAdapter
            sliderAdapter.updateData(movies)
        }
    }

    inner class GenreTitleViewHolder(private val binding : HomePageTitleItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bindData(){
            binding.titleText.text = "Genre"
        }
    }

    //endregion
    //region adapter methods
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TOP_MOVIE_VIEW_PAGER
            1 -> GENRE_TITLE
            2 -> GENRE_RECYCLER_VIEW
            3 -> LAST_MOVIE_TITLE
            else -> LAST_MOVIE_RECYCLER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TOP_MOVIE_VIEW_PAGER) {
            sliderAdapter = TopMovieSliderAdapter()
            sliderBinding = TopMovieSliderViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            val dotLayout = sliderBinding.slideDotLayout
            val dotImage = Array(Constants.Top_Movie_Size) { ImageView(parent.context) }
            dotImage.forEach {
                it.setImageResource(R.drawable.not_active_dot)
                it.layoutParams = layoutParams
                dotLayout.addView(it)
            }
            dotImage[0].setImageResource(R.drawable.active_dot)
            onPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    dotImage.mapIndexed { index, imageView ->
                        if (position == index) {
                            imageView.setImageResource(R.drawable.active_dot)
                        } else {
                            imageView.setImageResource(R.drawable.not_active_dot)
                        }
                    }
                    super.onPageSelected(position)
                }
            }
            sliderBinding.topViewPager.registerOnPageChangeCallback(onPageChangeListener)


        } else if ( viewType == GENRE_TITLE ){
            genreTitleBinding = HomePageTitleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return when (viewType) {
            TOP_MOVIE_VIEW_PAGER    -> TopMovieSliderViewHolder(sliderBinding, sliderAdapter, parent)
            GENRE_TITLE             -> GenreTitleViewHolder(genreTitleBinding)
            else -> TopMovieSliderViewHolder(sliderBinding, sliderAdapter, parent)
        }

    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TopMovieSliderViewHolder -> {
                holder.bindData()
            }
            is GenreTitleViewHolder     -> {
                holder.bindData()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(moviesResponce: List<Movie>) {
        if (moviesResponce.isNotEmpty()) {
            this.movies = moviesResponce
            notifyDataSetChanged()
        }
    }
    //endregion
}