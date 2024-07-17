package com.example.myapplication.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityHomePageBinding

class HomePage : AppCompatActivity() {

    //region Attributes
    private lateinit var binding : ActivityHomePageBinding

    //endregion
    //region lifeCycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }


    //region Initialize
    private fun initBinding(){
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //endregion
}