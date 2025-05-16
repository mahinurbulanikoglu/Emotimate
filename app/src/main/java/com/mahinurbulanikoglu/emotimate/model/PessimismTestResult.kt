package com.mahinurbulanikoglu.emotimate.model

data class PessimismTestResult(
    val userId: String = "",
    val testName: String = "Karamsarlık / Felaketçilik Testi",
    val date: String = "",
    val totalScore: Int = 0,
    val interpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int): String {
            return when {
                score >= 40 -> "Yüksek düzeyde Karamsarlık / Felaketçilik Şeması"
                score in 25..39 -> "Orta düzeyde Karamsarlık / Felaketçilik Şeması"
                else -> "Düşük düzeyde Karamsarlık / Felaketçilik Şeması"
            }
        }
    }
} 