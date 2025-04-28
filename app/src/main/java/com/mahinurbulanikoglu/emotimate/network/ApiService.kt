package com.mahinurbulanikoglu.emotimate.network

import com.mahinurbulanikoglu.emotimate.model.Book
import com.mahinurbulanikoglu.emotimate.model.Movie

import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("api/books")
    fun getBookInfo(
        @Query("bibkeys") bibkeys: String,  // Open Library API'den kitap verilerini almak için ISBN veya kitap anahtarını kullanıyoruz
        @Query("format") format: String = "json",
        @Query("jscmd") jscmd: String = "data"
    ): Call<Map<String, Book>>


    @GET("movies") // Filmleri almak için API endpoint
    fun getMovies(): Call<List<Movie>>
}