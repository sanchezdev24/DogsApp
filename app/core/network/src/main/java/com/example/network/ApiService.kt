package com.example.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApiService {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(NetworkConstants.TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(NetworkConstants.TIMEOUT, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(NetworkConstants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}