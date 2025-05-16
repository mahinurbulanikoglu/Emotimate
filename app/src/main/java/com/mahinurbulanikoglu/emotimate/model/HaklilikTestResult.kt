package com.mahinurbulanikoglu.emotimate.model

data class HaklilikTestResult(
    val userId: String = "",
    val testName: String = "Haklılık Testi",
    val date: String = "",
    val totalScore: Int = 0,
    val interpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int): String {
            return when {
                score >= 40 -> "Yüksek düzeyde Haklılık Şeması"
                score in 25..39 -> "Orta düzeyde Haklılık Şeması"
                else -> "Düşük düzeyde Haklılık Şeması"
            }
        }
    }
}