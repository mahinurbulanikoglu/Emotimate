package com.mahinurbulanikoglu.emotimate.model

data class TssbTestResult(
    val userId: String = "",
    val timestamp: Long = 0,
    val totalScore: Int = 0,
    val answers: Map<String, Int> = mapOf()
)