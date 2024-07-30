package com.example.myapplication.features.movie.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.GenreRecyclerBinding
import com.example.myapplication.databinding.HomePageTitleItemBinding
import com.example.myapplication.databinding.LastMovieItemBinding
import com.example.myapplication.databinding.TopMovieSliderViewBinding
import com.example.myapplication.features.genre.domain.model.Genre
import com.example.myapplication.features.genre.presentation.ui.adapter.GenreAdapter
import com.example.myapplication.features.movie.domain.model.Movie
import com.example.myapplication.shared_componenet.constants.Constants

class HomeFragmentAdapter(
    private var movies: List<Movie> = listOf(),
    private var genres: List<Genre> = listOf())
        :RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //region properties
    private lateinit var sliderBinding          : TopMovieSliderViewBinding
    private lateinit var sliderAdapter          : TopMovieSliderAdapter
    private lateinit var genreTitleBinding      : HomePageTitleItemBinding
    private lateinit var lastMovieTitleBinding  : HomePageTitleItemBinding
    private lateinit var genreRecyclerBinding   : GenreRecyclerBinding
    private lateinit var genreAdapter           : GenreAdapter
    private lateinit var onPageChangeListener   : ViewPager2.OnPageChangeCallback
    private lateinit var lastMoveRecyclerBinding: LastMovieItemBinding
    private val layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8, 0, 8, 8)
    }
    //endregion
    //region inner(object/class)
    companion object {
        private const val NUMBER_OF_TOP_VIEWS = 4
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
    inner class GenreRecyclerViewHolder(
        private val binding : GenreRecyclerBinding,
        private val adapter : GenreAdapter,
        private val parent  : ViewGroup
    ): RecyclerView.ViewHolder(binding.root){
        fun bindData(){
            binding.homePageGenreRecycler.adapter = adapter
            adapter.updateData(genres)
            binding.homePageGenreRecycler.layoutManager =
                LinearLayoutManager(parent.context , LinearLayoutManager.HORIZONTAL, false)
        }
    }
    inner class LastMovieTitleViewHolder(private val binding : HomePageTitleItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bindData(){
            binding.titleText.text = "Last movies"
        }
    }
    inner class LastMovieViewHolder(private val binding: LastMovieItemBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun bindData(movie : Movie){
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
        } else if ( viewType == GENRE_RECYCLER_VIEW ){
            genreAdapter = GenreAdapter()
            genreRecyclerBinding = GenreRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }else if ( viewType == LAST_MOVIE_TITLE ) {
            lastMovieTitleBinding = HomePageTitleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        } else if (viewType == LAST_MOVIE_RECYCLER){
            lastMoveRecyclerBinding = LastMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return when (viewType) {
            TOP_MOVIE_VIEW_PAGER    -> TopMovieSliderViewHolder(sliderBinding, sliderAdapter, parent)
            GENRE_TITLE             -> GenreTitleViewHolder(genreTitleBinding)
            GENRE_RECYCLER_VIEW     -> GenreRecyclerViewHolder(genreRecyclerBinding, genreAdapter, parent)
            LAST_MOVIE_TITLE        -> LastMovieTitleViewHolder(lastMovieTitleBinding)
            else                    -> LastMovieViewHolder(lastMoveRecyclerBinding)
        }

    }
    override fun getItemCount(): Int {
        return movies.size + NUMBER_OF_TOP_VIEWS
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TopMovieSliderViewHolder -> {
                holder.bindData()
            }
            is GenreTitleViewHolder     -> {
                holder.bindData()
            }
            is GenreRecyclerViewHolder  -> {
                holder.bindData()
            }
            is LastMovieTitleViewHolder -> {
                holder.bindData()
            }
            is LastMovieViewHolder      -> {
                holder.bindData(movies[position- NUMBER_OF_TOP_VIEWS])
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(moviesResponce: List<Movie>) {
        if (moviesResponce.isNotEmpty()) {
            this.movies = moviesResponce
            notifyDataSetChanged()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateGenres(genres:  List<Genre>) {
        if (genres.isNotEmpty()) {
            this.genres = genres
            notifyDataSetChanged()
        }
    }
    //endregion
}