package com.mahinurbulanikoglu.emotimate.model

data class AsrsTestResult(
    val userId: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val answers: Map<String, Int> = mapOf(),
    val totalScore: Int = 0
)