package com.mahinurbulanikoglu.emotimate.model

data class SocialIsolationTestResult(
    val userId: String = "",
    val testName: String = "Sosyal İzolasyon / Yabancılık Testi",
    val date: String = "",
    val totalScore: Int = 0,
    val interpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int): String {
            return when {
                score >= 40 -> "Yüksek düzeyde Sosyal İzolasyon / Yabancılık Şeması"
                score in 25..39 -> "Orta düzeyde Sosyal İzolasyon / Yabancılık Şeması"
                else -> "Düşük düzeyde Sosyal İzolasyon / Yabancılık Şeması"
            }
        }
    }
} 