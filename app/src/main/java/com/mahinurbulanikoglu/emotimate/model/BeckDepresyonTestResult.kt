package com.mahinurbulanikoglu.emotimate.model

data class BeckDepresyonTestResult(
    val userId: String = "",
    val date: String = "",
    val totalScore: Int = 0,
    val interpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int): String {
            return when (score) {
                in 0..9 -> "Minimal depresyon"
                in 10..16 -> "Hafif depresyon"
                in 17..29 -> "Orta düzey depresyon"
                in 30..63 -> "Şiddetli depresyon"
                else -> "Geçersiz puan"
            }
        }
    }
} 