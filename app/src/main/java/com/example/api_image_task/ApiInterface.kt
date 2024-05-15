package com.example.api_image_task

import com.example.api_image_task.API.ImageResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("photos")
    fun getPhotos(): Call<ImageResponse>
}