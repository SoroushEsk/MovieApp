package com.example.myapplication.register

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.features.token.domain.model.RegisterRequest
import com.example.myapplication.features.token.presentation.TokenViewModel
import com.example.myapplication.features.token.presentation.TokenViewModelFactory
import com.example.myapplication.shared_componenet.constants.Constants
import com.example.myapplication.utils.containsKey
import com.example.myapplication.utils.putBoolean
import com.example.myapplication.utils.putInteger
import com.example.myapplication.utils.putString
import java.security.MessageDigest

class RegisterActivity : AppCompatActivity() {
    //region Properties
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var viewModel: TokenViewModel
    //endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initSharedPreference()
        initViewModel()
        binding.submit.setOnClickListener{
            val userData = getRegisterRequest()
            viewModel.registerUser(userData)
            viewModel.registerResponse.observe(this){
                if(it.isSuccessful){
                    sharedPreferences.putBoolean(Constants.IsAuthenticated, true)
                }else {

                }
            }
        }
        var customeColoe = Color.parseColor("#FFAFAFAF")

    }


    //region initialize
    private fun initBinding(){
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun initSharedPreference(){
        sharedPreferences = getSharedPreferences(Constants.AppSharedPreference, MODE_PRIVATE)
        val isAuth: Boolean = sharedPreferences.containsKey(Constants.IsAuthenticated)
        val isUserId: Boolean = sharedPreferences.containsKey(Constants.UserID)
        val isToken: Boolean = sharedPreferences.containsKey(Constants.Token)
        if (!isAuth) sharedPreferences.putBoolean(Constants.IsAuthenticated, false)
        if (!isUserId) sharedPreferences.putInteger(Constants.UserID, 0)
        if (!isToken) sharedPreferences.putString(Constants.Token, "")
    }
    private fun initViewModel(){
        viewModel = ViewModelProvider(this, TokenViewModelFactory())[TokenViewModel::class.java]

    }
    //endregion

    //region Method
    private fun getRegisterRequest(): RegisterRequest {
        val name = binding.textInputEditTextName.text.toString().trim()
        val studentId = binding.textInputEditTextStNumber.text.toString().trim()
        val email = binding.textInputEditTextEmail.text.toString().trim()
        val password = binding.textInputEditTextPassword.text.toString().trim().toMD5()

        return RegisterRequest(email, name, studentId, password)
    }
    fun String.toMD5(): String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
    //endregion
}