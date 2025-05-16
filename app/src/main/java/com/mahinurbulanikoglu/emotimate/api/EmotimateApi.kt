package com.mahinurbulanikoglu.emotimate.api

import com.mahinurbulanikoglu.emotimate.api.models.EmotionAnalysisRequest
import com.mahinurbulanikoglu.emotimate.api.models.EmotionAnalysisResponse
import com.mahinurbulanikoglu.emotimate.api.models.TherapyRequest
import com.mahinurbulanikoglu.emotimate.api.models.TherapyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Header

interface EmotimateApi {
    @POST("/api/v1/analyze-emotion")
    suspend fun analyzeEmotion(
        @Header("X-API-Key") apiKey: String,
        @Body request: EmotionAnalysisRequest
    ): Response<EmotionAnalysisResponse>

    @POST("/api/v1/therapy-response")
    suspend fun getTherapyResponse(
        @Header("X-API-Key") apiKey: String,
        @Body request: TherapyRequest
    ): Response<TherapyResponse>
}
