package com.example.myapplication.shared_componenet.api

import android.content.Context.MODE_PRIVATE
import android.window.SplashScreen
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class API {
    companion object {
        private object Constant{
            const val baseURL = "https://api.cseshirazu307.ir/"
            const val connectionTime: Long = 60
        }
        private val client: OkHttpClient
            get() = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .writeTimeout(Constant.connectionTime, TimeUnit.SECONDS)
                .readTimeout(Constant.connectionTime, TimeUnit.SECONDS)
                .connectTimeout(Constant.connectionTime, TimeUnit.SECONDS)
                .build()

        private val gson: Gson get() = GsonBuilder().setLenient().create()

        private val loggingInterceptor: HttpLoggingInterceptor
            get() = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        private val retrofitBuilder: Retrofit.Builder
            get() = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))

        val apiService: APIService by lazy {
            retrofitBuilder
                .baseUrl(Constant.baseURL)
                .build()
                .create(APIService::class.java)
        }
    }
}