package com.example.studyroom.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetClient {
    // 后端接口地址，后续替换成你的真实服务地址
    private const val BASE_URL = "http://127.0.0.1:8080/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}