package com.example.myapplication.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySplashScreenBinding
import com.example.myapplication.home.HomePage
import com.example.myapplication.register.RegisterActivity
import com.example.myapplication.shared_componenet.constants.Constants
import com.example.myapplication.utils.containsKey
import com.example.myapplication.utils.putBoolean
import com.example.myapplication.utils.putInteger
import com.example.myapplication.utils.putString

class SplashScreen : AppCompatActivity() {
    //region properties

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding : ActivitySplashScreenBinding
    //endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingInit()
        initSharedPreference()
        binding.mainAppIcon.alpha = 0f
        binding.mainAppIcon.animate().setDuration(1500).alpha(1f).withEndAction{
            val intent = if (sharedPreferences.getBoolean(Constants.IsAuthenticated, false))
                             Intent(this, HomePage::class.java)
                         else Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



    //region initialization
    private fun bindingInit(){
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun initSharedPreference() {
        sharedPreferences = getSharedPreferences(Constants.AppSharedPreference, MODE_PRIVATE)
        val isAuth: Boolean = sharedPreferences.containsKey(Constants.IsAuthenticated)
        val isUserId: Boolean = sharedPreferences.containsKey(Constants.UserID)
        val isToken: Boolean = sharedPreferences.containsKey(Constants.Token)
        if (!isAuth) sharedPreferences.putBoolean(Constants.IsAuthenticated, false)
        if (!isUserId) sharedPreferences.putInteger(Constants.UserID, 0)
        if (!isToken) sharedPreferences.putString(Constants.Token, "")
    }
    //endregion
}