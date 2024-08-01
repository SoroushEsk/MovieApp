package com.example.myapplication.home

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomePageBinding

class HomePage : AppCompatActivity() {
    //region Attributes
    private lateinit var binding : ActivityHomePageBinding
    //endregion
    //region lifeCycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initFragment()
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

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
    }
    private fun initFragment() {
        ImageViewCompat.setImageTintList(
            binding.homePageHomeIcon,
            ContextCompat.getColorStateList(this, R.color.app_yellow_color)
        )
        supportFragmentManager.beginTransaction()
            .add(binding.homePageFragmentContainer.id, HomeFragment())
            .commit()
    }
    //endregion
    //region methods

    //endregion
    //region override methods
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