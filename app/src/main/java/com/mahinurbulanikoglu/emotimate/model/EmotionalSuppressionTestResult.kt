package com.mahinurbulanikoglu.emotimate.model

data class EmotionalSuppressionTestResult(
    val userId: String = "",
    val testName: String = "Duygusal Bastırma Testi",
    val date: String = "",
    val totalScore: Int = 0,
    val interpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int): String {
            return when {
                score >= 40 -> "Yüksek düzeyde Duygusal Bastırma Şeması"
                score in 25..39 -> "Orta düzeyde Duygusal Bastırma Şeması"
                else -> "Düşük düzeyde Duygusal Bastırma Şeması"
            }
        }
    }
} 