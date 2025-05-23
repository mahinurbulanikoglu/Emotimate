package com.mahinurbulanikoglu.emotimate.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val OPEN_LIBRARY_BASE_URL = "https://openlibrary.org/"
    private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    private const val EUROPE_PMC_BASE_URL = "https://www.ebi.ac.uk/europepmc/webservices/rest/"

    private val openLibraryRetrofit = Retrofit.Builder()
        .baseUrl(OPEN_LIBRARY_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val tmdbRetrofit = Retrofit.Builder()
        .baseUrl(TMDB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val europePmcRetrofit = Retrofit.Builder()
        .baseUrl(EUROPE_PMC_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val apiService: ApiService by lazy {
        openLibraryRetrofit.create(ApiService::class.java)
    }

    val tmdbApiService: TMDbApiService by lazy {
        tmdbRetrofit.create(TMDbApiService::class.java)
    }

    val europePmcApiService: EuropePmcApiService by lazy {
        europePmcRetrofit.create(EuropePmcApiService::class.java)
    }
}