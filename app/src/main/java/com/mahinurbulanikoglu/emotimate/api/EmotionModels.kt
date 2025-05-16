package com.mahinurbulanikoglu.emotimate.api.models

data class EmotionAnalysisRequest(
    val text: String,
    val emotion: String,
    val user_id: String
)

data class EmotionAnalysisResponse(
    val emotion: String,
    val confidence: Double,
    val analysis: String
)

data class TherapyRequest(
    val emotion: String,
    val context: String,
    val language: String = "tr"
)

data class TherapyResponse(
    val response: String,
    val techniques: List<String>,
    val suggestions: List<String>
)