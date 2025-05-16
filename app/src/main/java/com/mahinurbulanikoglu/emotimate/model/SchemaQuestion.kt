package com.mahinurbulanikoglu.emotimate.model

data class SchemaQuestion(
    val id: Int,                    // Her soruya ait bir numara (1,2,3,...)
    val questionText: String,      // Soru metni
    var selectedScore: Int = 0     // Kullanıcının verdiği puan (varsayılan 0)
)
