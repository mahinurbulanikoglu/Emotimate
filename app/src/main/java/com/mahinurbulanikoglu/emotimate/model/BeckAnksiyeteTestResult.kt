package com.mahinurbulanikoglu.emotimate.model

data class BeckAnksiyeteTestResult(
    val userId: String = "",
    val date: String = "",
    val totalScore: Int = 0,
    val interpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int): String {
            return when (score) {
                in 0..7 -> "Minimal anksiyete"
                in 8..15 -> "Hafif anksiyete"
                in 16..25 -> "Orta düzey anksiyete"
                in 26..63 -> "Şiddetli anksiyete"
                else -> "Geçersiz puan"
            }
        }
    }
} 