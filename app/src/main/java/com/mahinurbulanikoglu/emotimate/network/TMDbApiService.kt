package com.mahinurbulanikoglu.emotimate.network

import com.mahinurbulanikoglu.emotimate.model.Movie
import com.mahinurbulanikoglu.emotimate.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbApiService {
    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<MovieResponse>
}

data class MovieResponse(
    val results: List<Movie>
)