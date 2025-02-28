package com.example.myapplication.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.shared_componenet.constants.Constants
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomePageBinding
import com.example.myapplication.fragment.favorite.FavoriteFragment
import com.example.myapplication.fragment.search.SearchFragment
import com.example.myapplication.utils.FragmentTypes
import com.google.android.material.snackbar.Snackbar

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
        searchIconAction()
        profileIconAction()
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
    private fun homeIconAction() {
        binding.homePageHomeIcon.setOnClickListener {
            Log.e("ButtonClick", "movie")
            when (currentFragment) {
                FragmentTypes.MovieFragment -> return@setOnClickListener
                FragmentTypes.FavoriteFragment, FragmentTypes.SearchFragment -> {
                    var homeFragment = supportFragmentManager.findFragmentByTag(Constants.MovieFragmentTag) as? HomeFragment
                    if (homeFragment == null) {
                        homeFragment = HomeFragment()
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(binding.homePageFragmentContainer.id, homeFragment, Constants.MovieFragmentTag)
                        .addToBackStack(Constants.MovieFragmentTag)
                        .commit()

                    updateIconColors(FragmentTypes.MovieFragment)

                    currentFragment = FragmentTypes.MovieFragment
                }
            }
        }
    }
    fun searchIconAction() {
        binding.homePageSearchIcon.setOnClickListener {
            Log.e("ButtonClick", "search")
            when (currentFragment) {
                FragmentTypes.SearchFragment -> return@setOnClickListener
                FragmentTypes.MovieFragment, FragmentTypes.FavoriteFragment -> {
                    var searchFragment = supportFragmentManager.findFragmentByTag(Constants.SearchFragmentTag) as? SearchFragment
                    if (searchFragment == null) {
                        searchFragment = SearchFragment()
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(binding.homePageFragmentContainer.id, searchFragment, Constants.SearchFragmentTag)
                        .addToBackStack(Constants.SearchFragmentTag)
                        .commit()
                    updateIconColors(FragmentTypes.SearchFragment)
                    currentFragment = FragmentTypes.SearchFragment
                }
            }
        }
    }
    fun favoriteIconAction() {
        binding.homePageLikeIcon.setOnClickListener {
            Log.e("ButtonClick", "favorite")
            when (currentFragment) {
                FragmentTypes.FavoriteFragment -> return@setOnClickListener
                FragmentTypes.MovieFragment, FragmentTypes.SearchFragment -> {
                    var favoriteFragment = supportFragmentManager.findFragmentByTag(Constants.FavoriteFragmentTag) as? FavoriteFragment
                    if (favoriteFragment == null) {
                        favoriteFragment = FavoriteFragment()
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(binding.homePageFragmentContainer.id, favoriteFragment, Constants.FavoriteFragmentTag)
                        .addToBackStack(Constants.FavoriteFragmentTag)
                        .commit()
                    updateIconColors(FragmentTypes.FavoriteFragment)
                    currentFragment = FragmentTypes.FavoriteFragment
                }
            }
        }
    }
    private fun profileIconAction(){
        binding.homePageProfileIcon.setOnClickListener(){
            Toast.makeText(this, "Available In The Next Version", Toast.LENGTH_LONG).show()
        }
    }
    private fun updateIconColors(fragmentType: FragmentTypes) {
        binding.homePageHomeIcon.imageTintList = ContextCompat.getColorStateList(this,
            if (fragmentType == FragmentTypes.MovieFragment) R.color.app_yellow_color
            else R.color.app_gray_color)
        binding.homePageLikeIcon.imageTintList = ContextCompat.getColorStateList(this,
            if (fragmentType == FragmentTypes.FavoriteFragment) R.color.app_yellow_color
            else R.color.app_gray_color)
        binding.homePageSearchIcon.imageTintList = ContextCompat.getColorStateList(this,
            if (fragmentType == FragmentTypes.SearchFragment) R.color.app_yellow_color
            else R.color.app_gray_color)
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