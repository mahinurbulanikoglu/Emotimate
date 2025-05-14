package com.mahinurbulanikoglu.emotimate.model

data class PomsTestResult(
    val userId: String = "",
    val date: String = "",
    val tensionScore: Int = 0,
    val depressionScore: Int = 0,
    val angerScore: Int = 0,
    val fatigueScore: Int = 0,
    val confusionScore: Int = 0,
    val vigorScore: Int = 0,
    val totalMoodDisturbance: Int = 0,
    val answers: Map<String, Int> = mapOf()
) {
    companion object {
        fun getInterpretation(score: Int, type: String): String {
            return when (type) {
                "TENSION" -> when (score) {
                    in 0..8 -> "Düşük düzeyde gerilim"
                    in 9..15 -> "Orta düzeyde gerilim"
                    else -> "Yüksek düzeyde gerilim"
                }
                "DEPRESSION" -> when (score) {
                    in 0..8 -> "Düşük düzeyde depresyon"
                    in 9..15 -> "Orta düzeyde depresyon"
                    else -> "Yüksek düzeyde depresyon"
                }
                "ANGER" -> when (score) {
                    in 0..8 -> "Düşük düzeyde öfke"
                    in 9..15 -> "Orta düzeyde öfke"
                    else -> "Yüksek düzeyde öfke"
                }
                "FATIGUE" -> when (score) {
                    in 0..8 -> "Düşük düzeyde yorgunluk"
                    in 9..15 -> "Orta düzeyde yorgunluk"
                    else -> "Yüksek düzeyde yorgunluk"
                }
                "CONFUSION" -> when (score) {
                    in 0..8 -> "Düşük düzeyde zihin bulanıklığı"
                    in 9..15 -> "Orta düzeyde zihin bulanıklığı"
                    else -> "Yüksek düzeyde zihin bulanıklığı"
                }
                "VIGOR" -> when (score) {
                    in 0..8 -> "Düşük düzeyde canlılık"
                    in 9..15 -> "Orta düzeyde canlılık"
                    else -> "Yüksek düzeyde canlılık"
                }
                "TMD" -> when (score) {
                    in 0..40 -> "Normal ruh hali"
                    in 41..80 -> "Orta düzey ruhsal stres"
                    else -> "Yüksek ruhsal bozukluk riski"
                }
                else -> "Geçersiz değerlendirme"
            }
        }
    }
} 