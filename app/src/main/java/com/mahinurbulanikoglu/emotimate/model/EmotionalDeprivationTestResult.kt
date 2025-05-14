package com.mahinurbulanikoglu.emotimate.model

data class EmotionalDeprivationTestResult(
    val userId: String = "",
    val testName: String = "Duygusal Yoksunluk Testi",
    val date: String = "",
    val totalScore: Int = 0,
    val interpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int): String {
            return when {
                score >= 40 -> "Yüksek düzeyde Duygusal Yoksunluk Şeması"
                score in 25..39 -> "Orta düzeyde Duygusal Yoksunluk Şeması"
                else -> "Düşük düzeyde Duygusal Yoksunluk Şeması"
            }
        }
    }
} 