package com.mahinurbulanikoglu.emotimate.model

data class FailureTestResult(
    val userId: String = "",
    val testName: String = "Başarısızlık Testi",
    val date: String = "",
    val totalScore: Int = 0,
    val interpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int): String {
            return when {
                score >= 40 -> "Yüksek düzeyde Başarısızlık Şeması"
                score in 25..39 -> "Orta düzeyde Başarısızlık Şeması"
                else -> "Düşük düzeyde Başarısızlık Şeması"
            }
        }
    }
} 