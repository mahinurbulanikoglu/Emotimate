package com.mahinurbulanikoglu.emotimate.model

data class PanasTestResult(
    val userId: String = "",
    val date: String = "",
    val positiveScore: Int = 0,
    val negativeScore: Int = 0,
    val positiveInterpretation: String = "",
    val negativeInterpretation: String = "",
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getPositiveInterpretation(score: Int): String {
            return when (score) {
                in 10..19 -> "Düşük pozitif duygu"
                in 20..29 -> "Orta düzey pozitif duygu"
                in 30..39 -> "Yüksek pozitif duygu"
                in 40..50 -> "Çok yüksek pozitif duygu"
                else -> "Geçersiz puan"
            }
        }

        fun getNegativeInterpretation(score: Int): String {
            return when (score) {
                in 10..19 -> "Düşük negatif duygu"
                in 20..29 -> "Orta düzey negatif duygu"
                in 30..39 -> "Yüksek negatif duygu"
                in 40..50 -> "Çok yüksek negatif duygu"
                else -> "Geçersiz puan"
            }
        }
    }
} 