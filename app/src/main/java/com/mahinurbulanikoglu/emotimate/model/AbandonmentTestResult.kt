package com.mahinurbulanikoglu.emotimate.model

data class AbandonmentTestResult(
    val userId: String = "",
    val testName: String = "Terk Edilme / İstikrarsızlık Testi",
    val date: String = "",
    val totalScore: Int = 0,
    val interpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int): String {
            return when {
                score >= 40 -> "Yüksek düzeyde Terk Edilme / İstikrarsızlık Şeması"
                score in 25..39 -> "Orta düzeyde Terk Edilme / İstikrarsızlık Şeması"
                else -> "Düşük düzeyde Terk Edilme / İstikrarsızlık Şeması"
            }
        }
    }
}