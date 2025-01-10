package com.example.myapplication.register

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.features.token.domain.model.RegisterRequest
import com.example.myapplication.features.token.presentation.TokenViewModel
import com.example.myapplication.features.token.presentation.TokenViewModelFactory
import com.example.myapplication.home.HomePage
import com.example.myapplication.shared_componenet.constants.Constants
import com.example.myapplication.utils.containsKey
import com.example.myapplication.utils.putBoolean
import com.example.myapplication.utils.putInteger
import com.example.myapplication.utils.putString
import com.google.android.material.textfield.TextInputEditText
import java.security.MessageDigest
class RegisterActivity : AppCompatActivity() {
    //region Properties
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var viewModel: TokenViewModel
    private var isFocused = HashMap<Int, Boolean>()
    //endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initSharedPreference()
        initViewModel()
        binding.submit.setOnClickListener{
            val userData = getRegisterRequest()
            if (userData == null) return@setOnClickListener
            viewModel.registerUser(userData)
            viewModel.registerResponse.observe(this){
                if(it.isSuccessful){
                    sharedPreferences.putBoolean(Constants.IsAuthenticated, true)
                    startActivity(Intent(this, HomePage::class.java))
                    finish()
                }else {
                    showToast("Your Request Was Unsuccessful")
                }
            }
        }
        setupFocusChangeListener(binding.textInputEditTextName, binding.nameIcon, binding.nameBorder)
        setupFocusChangeListener(binding.textInputEditTextStd, binding.stdIcon, binding.stdBorder)
        setupFocusChangeListener(binding.textInputEditTextEmail, binding.emailIcon, binding.emailBorder)
        val passwordTextInput = binding.textInputEditTextPassword
        passwordTextInput.setOnFocusChangeListener{ _, hasFocus ->
            val colorResId = if (hasFocus) R.color.app_yellow_color else R.color.app_gray_color
            val borderResId = if (hasFocus) R.drawable.yellow_border else R.drawable.border_genre_item
            binding.passwordIcon.imageTintList = ContextCompat.getColorStateList(this, colorResId)
            binding.textInputLayoutPassword.setEndIconTintList(ContextCompat.getColorStateList(this, colorResId))
            binding.passwordBorder.setBackgroundResource(borderResId)
        }
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
    private fun setupFocusChangeListener(
        editText: TextInputEditText,
        icon: ImageView,
        border: View
    ) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            val colorResId = if (hasFocus) R.color.app_yellow_color else R.color.app_gray_color
            val borderResId = if (hasFocus) R.drawable.yellow_border else R.drawable.border_genre_item
            icon.imageTintList = ContextCompat.getColorStateList(this, colorResId)
            border.setBackgroundResource(borderResId)
        }
    }
    //endregion
    //region Method
    private fun getRegisterRequest(): RegisterRequest? {
        val name = binding.textInputEditTextName.text.toString().trim()
        val studentId = binding.textInputEditTextStd.text.toString().trim()
        val email = binding.textInputEditTextEmail.text.toString().trim()
        val password = binding.textInputEditTextPassword.text.toString().trim().toMD5()
        if (name.length < Companion.NAME_MIN_LENGTH || name.length > Companion.NAME_MAX_LENGTH) {
            showToast("Name must be between ${Companion.NAME_MIN_LENGTH} and ${Companion.NAME_MAX_LENGTH} characters")
            return null
        }
        if (!studentId.isValidStudentId()) {
            showToast("Invalid student ID. It should be 7 or 8 digits")
            return null
        }
        if (!email.isValidEmail()) {
            showToast("Invalid email address")
            return null
        }
        if (password.length < Companion.PASSWORD_MIN_LENGTH) {
            showToast("Password must be at least ${Companion.PASSWORD_MIN_LENGTH} characters long")
            return null
        }
        return RegisterRequest(email, password, name)
    }
    fun String.toMD5(): String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
    private fun String.isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
    private fun String.isValidStudentId(): Boolean {
        return Companion.STUDENT_ID_PATTERN.toRegex().matches(this)
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    //endregion
    companion object {
        private const val NAME_MIN_LENGTH = 2
        private const val PASSWORD_MIN_LENGTH = 8
        private const val STUDENT_ID_PATTERN = "^[0-9]{7,8}$"
        private const val NAME_MAX_LENGTH = 50
    }
}

