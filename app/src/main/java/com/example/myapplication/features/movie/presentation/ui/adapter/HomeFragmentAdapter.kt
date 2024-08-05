package com.example.myapplication.features.movie.presentation.ui.adapter

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
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
    private var movies: MutableList<Movie> = mutableListOf(),
    private var genres: MutableList<Genre> = mutableListOf())
        :RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //region properties
    private val handler = Handler(Looper.getMainLooper())
    private var autoScrollRunnable: Runnable? = null
    private val autoScrollDelay = Constants.SliderDuration
    var isLoading = false
    private var currentSliderPosition = 0
    private var currentGenrePosition  = 0
    private lateinit var sliderBinding          : TopMovieSliderViewBinding
    private lateinit var sliderAdapter          : TopMovieSliderAdapter
    private lateinit var genreTitleBinding      : HomePageTitleItemBinding
    private lateinit var lastMovieTitleBinding  : HomePageTitleItemBinding
    private lateinit var genreRecyclerBinding   : GenreRecyclerBinding
    private lateinit var genreAdapter           : GenreAdapter
    private lateinit var onPageChangeListener   : ViewPager2.OnPageChangeCallback
    private lateinit var onGenreChangeListener  : RecyclerView.OnScrollListener
    private lateinit var lastMoveRecyclerBinding: LastMovieItemBinding
    private var movieClickListener: OnMovieClickListener? = null
    private var sliderMovieClickListener: TopMovieSliderAdapter.OnSliderMovieClickListener? = null
    private var genreClickListener: GenreAdapter.OnGenreClickListener? = null
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
        private const val SCROLL_DURATION = 1000L
    }
    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
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
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindData(){
            binding.homePageGenreRecycler.adapter = adapter
            adapter.updateData(genres)
            binding.homePageGenreRecycler.layoutManager =
                LinearLayoutManager(parent.context , LinearLayoutManager.HORIZONTAL, false)
            binding.homePageGenreRecycler.addOnScrollListener(onGenreChangeListener)
            binding.homePageGenreRecycler.scrollToPosition(currentGenrePosition)
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
            sliderAdapter = TopMovieSliderAdapter(sliderMovieClickListener)
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
                        if (position%Constants.Top_Movie_Size == index) {
                            imageView.setImageResource(R.drawable.active_dot)
                        } else {
                            imageView.setImageResource(R.drawable.not_active_dot)
                        }
                    }
                    currentSliderPosition = position
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
            genreAdapter = GenreAdapter(genreClickListener)
            genreRecyclerBinding = GenreRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            onGenreChangeListener = object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    currentGenrePosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    super.onScrolled(recyclerView, dx, dy)
                }
            }
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
                Log.e("sth", currentSliderPosition.toString())
                sliderBinding.topViewPager.setCurrentItem(currentSliderPosition, false)
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
                holder.itemView.setOnClickListener{
                    movieClickListener?.onMovieClick(movies[position- NUMBER_OF_TOP_VIEWS])
                }
            }
        }
    }
    fun setOnMovieClickListener(listener: OnMovieClickListener) {
        movieClickListener = listener
    }
    fun setOnSliderClick(listener: TopMovieSliderAdapter.OnSliderMovieClickListener){
        sliderMovieClickListener = listener
    }
    fun setOnGenreClick(listener: GenreAdapter.OnGenreClickListener){
        genreClickListener = listener
    }
    fun isGenresEmpty() : Boolean = genres.isEmpty()
    fun ViewPager2.setCurrentItemWithDuration(
        item: Int,
        duration: Long,
        interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
        pagePxWidth: Int = width // Default to screen width
    ) {
        val pxToDrag: Int = pagePxWidth * (item - currentItem)
        val animator = ValueAnimator.ofInt(0, pxToDrag)
        var previousValue = 0
        animator.addUpdateListener { valueAnimator ->
            val currentValue = valueAnimator.animatedValue as Int
            val currentPxToDrag = (currentValue - previousValue).toFloat()
            fakeDragBy(-currentPxToDrag)
            previousValue = currentValue
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) { beginFakeDrag() }
            override fun onAnimationEnd(animation: Animator) { endFakeDrag() }
            override fun onAnimationCancel(animation: Animator) { /* Ignored */ }
            override fun onAnimationRepeat(animation: Animator) { /* Ignored */ }
        })
        animator.interpolator = interpolator
        animator.duration = duration
        animator.start()
    }
    private fun startAutoScroll() {
        autoScrollRunnable = object : Runnable {
            override fun run() {
                sliderBinding.topViewPager.setCurrentItemWithDuration(
                    sliderBinding.topViewPager.currentItem + 1,
                    SCROLL_DURATION
                )
                handler.postDelayed(this, autoScrollDelay)
            }
        }
        handler.postDelayed(autoScrollRunnable!!, autoScrollDelay)
    }
    private fun stopAutoScroll() {
        autoScrollRunnable?.let { handler.removeCallbacks(it) }
    }
    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is TopMovieSliderViewHolder) {
            startAutoScroll()
        }
    }
    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder is TopMovieSliderViewHolder) {
            stopAutoScroll()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(moviesResponce: List<Movie>) {
        if (moviesResponce.isNotEmpty()) {
            val movieSize = this.movies.size
            this.movies.addAll(moviesResponce)
            notifyDataSetChanged()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(movies: MutableList<Movie>) {
        if (movies.isNotEmpty()) {
            this.movies = movies
            notifyDataSetChanged()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateGenres(genres:  List<Genre>) {
        if (genres.isNotEmpty()) {
            this.genres.addAll(genres)
            notifyDataSetChanged()
        }
    }
    fun onDestroy() {
        stopAutoScroll()
    }
    //endregion
}