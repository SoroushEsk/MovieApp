package com.example.myapplication.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.shared_componenet.constants.Constants
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomePageBinding
import com.example.myapplication.fragment.favorite.FavoriteFragment
import com.example.myapplication.fragment.search.SearchFragment
import com.example.myapplication.utils.FragmentTypes

class HomePage : AppCompatActivity() {
    //region Attributes
    private lateinit var binding : ActivityHomePageBinding
    private lateinit var currentFragment : FragmentTypes
    //endregion
    //region lifeCycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initFragment()
        homeIconAction()
        favoriteIconAction()
    }
    //region Initialize
    private fun initBinding() {
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
    }
    private fun initFragment() {
        ImageViewCompat.setImageTintList(
            binding.homePageHomeIcon,
            ContextCompat.getColorStateList(this, R.color.app_yellow_color)
        )
        supportFragmentManager.beginTransaction()
            .replace(binding.homePageFragmentContainer.id, HomeFragment(), Constants.MovieFragmentTag)
            .addToBackStack(Constants.MovieFragmentTag)
            .commit()
        currentFragment = FragmentTypes.MovieFragment
    }
    //endregion
    //region methods
    private fun homeIconAction(){
        binding.homePageHomeIcon.setOnClickListener{
            Log.e("ButtonClick", "movie")
            when (currentFragment) {
                FragmentTypes.MovieFragment -> return@setOnClickListener
                FragmentTypes.FavoriteFragment -> {
                    ImageViewCompat.setImageTintList(
                        binding.homePageLikeIcon,
                        ContextCompat.getColorStateList(this, R.color.app_gray_color)
                    )
                }
                FragmentTypes.SearchFragment -> {
                    ImageViewCompat.setImageTintList(
                        binding.homePageSearchIcon,
                        ContextCompat.getColorStateList(this, R.color.app_gray_color)
                    )
                }
            }
            ImageViewCompat.setImageTintList(
                binding.homePageHomeIcon,
                ContextCompat.getColorStateList(this, R.color.app_yellow_color)
            )
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(binding.homePageFragmentContainer.id, HomeFragment(), Constants.MovieFragmentTag)
                .addToBackStack(Constants.MovieFragmentTag)
                .commit()

            currentFragment = FragmentTypes.MovieFragment
        }
    }
    fun searchIconAction(){
        binding.homePageLikeIcon.setOnClickListener{
            Log.e("ButtonClick", "favorite")
            when (currentFragment) {
                FragmentTypes.SearchFragment -> return@setOnClickListener
                FragmentTypes.MovieFragment -> {
                    ImageViewCompat.setImageTintList(
                        binding.homePageHomeIcon,
                        ContextCompat.getColorStateList(this, R.color.app_gray_color)
                    )
                }
                FragmentTypes.FavoriteFragment -> {
                    ImageViewCompat.setImageTintList(
                        binding.homePageLikeIcon,
                        ContextCompat.getColorStateList(this, R.color.app_gray_color)
                    )
                }
            }
            ImageViewCompat.setImageTintList(
                binding.homePageSearchIcon,
                ContextCompat.getColorStateList(this, R.color.app_yellow_color)
            )
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(binding.homePageFragmentContainer.id, SearchFragment(), Constants.SearchFragmentTag)
                .addToBackStack(Constants.SearchFragmentTag)
                .commit()

            currentFragment = FragmentTypes.SearchFragment
        }
    }
    fun favoriteIconAction(){
        binding.homePageLikeIcon.setOnClickListener{
            Log.e("ButtonClick", "favorite")
            when (currentFragment) {
                FragmentTypes.FavoriteFragment -> return@setOnClickListener
                FragmentTypes.MovieFragment -> {
                    ImageViewCompat.setImageTintList(
                        binding.homePageHomeIcon,
                        ContextCompat.getColorStateList(this, R.color.app_gray_color)
                    )
                }
                FragmentTypes.SearchFragment -> {
                    ImageViewCompat.setImageTintList(
                        binding.homePageSearchIcon,
                        ContextCompat.getColorStateList(this, R.color.app_gray_color)
                    )
                }
            }
            ImageViewCompat.setImageTintList(
                binding.homePageLikeIcon,
                ContextCompat.getColorStateList(this, R.color.app_yellow_color)
            )
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(binding.homePageFragmentContainer.id, FavoriteFragment(), Constants.FavoriteFragmentTag)
                .addToBackStack(Constants.FavoriteFragmentTag)
                .commit()

            currentFragment = FragmentTypes.FavoriteFragment
        }
    }
    //endregion
    //region override methods
    override fun onBackPressed() {
        super.onBackPressed()
        val fragmentManager = supportFragmentManager
        finish()
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
    //endregion
}