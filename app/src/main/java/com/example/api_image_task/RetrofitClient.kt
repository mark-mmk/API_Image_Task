package com.example.api_image_task

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    var BASE_URL = "https://jsonplaceholder.typicode.com/"
    fun getRetrofitClient(): ApiInterface {
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
    }
}