package com.mahinurbulanikoglu.emotimate.model

data class DependencyTestResult(
    val userId: String = "",
    val testName: String = "Bağımlılık Testi",
    val date: String = "",
    val totalScore: Int = 0,
    val interpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int): String {
            return when {
                score >= 40 -> "Yüksek düzeyde Bağımlılık Şeması"
                score in 25..39 -> "Orta düzeyde Bağımlılık Şeması"
                else -> "Düşük düzeyde Bağımlılık Şeması"
            }
        }
    }
} 