package com.mahinurbulanikoglu.emotimate.model

data class SelfSacrificeTestResult(
    val userId: String = "",
    val testName: String = "Kendini Feda Testi",
    val date: String = "",
    val totalScore: Int = 0,
    val interpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int): String {
            return when {
                score >= 40 -> "Yüksek düzeyde Kendini Feda Şeması"
                score in 25..39 -> "Orta düzeyde Kendini Feda Şeması"
                else -> "Düşük düzeyde Kendini Feda Şeması"
            }
        }
    }
} 