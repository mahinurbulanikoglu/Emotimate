package com.mahinurbulanikoglu.emotimate.network

import com.mahinurbulanikoglu.emotimate.model.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EuropePmcApiService {
    @GET("search")
    fun searchArticles(
        @Query("query") query: String,
        @Query("format") format: String = "json",
        @Query("resultType") resultType: String = "core",
        @Query("pageSize") pageSize: Int = 3
    ): Call<ArticleResponse>
}