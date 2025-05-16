package com.mahinurbulanikoglu.emotimate.model

data class ShameTestResult(
    val userId: String = "",
    val testName: String = "Kusurluluk/Utanç Testi",
    val date: String = "",
    val totalScore: Int = 0,
    val interpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int): String {
            return when {
                score >= 55 -> "Yüksek düzeyde Kusurluluk / Utanç"
                score in 35..54 -> "Orta düzeyde Kusurluluk / Utanç"
                else -> "Düşük düzeyde Kusurluluk / Utanç"
            }
        }
    }
} 