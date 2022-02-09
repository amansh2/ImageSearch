package com.example.imagesearch.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private val retrofit =
    Retrofit.Builder()
        .baseUrl(UnsplashApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

object UnsplashApi {
    val retrofitService: UnsplashApiService by lazy {
        retrofit.create(UnsplashApiService::class.java)
    }
}


interface UnsplashApiService {
    companion object {
        const val CLIENT_ID = "IX0wiJhLuttcrJFNwTIvnI8W0JAgtF1RABPcYt-2MGA"
        const val BASE_URL = "https://api.unsplash.com/"
    }

    @Headers("Accept-version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse
}