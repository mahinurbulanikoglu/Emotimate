package com.mahinurbulanikoglu.emotimate.model

data class SubjugationTestResult(
    val userId: String = "",
    val testName: String = "Boyun Eğicilik Testi",
    val date: String = "",
    val totalScore: Int = 0,
    val interpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int): String {
            return when {
                score >= 40 -> "Yüksek düzeyde Boyun Eğicilik Şeması"
                score in 25..39 -> "Orta düzeyde Boyun Eğicilik Şeması"
                else -> "Düşük düzeyde Boyun Eğicilik Şeması"
            }
        }
    }
} 