package com.mahinurbulanikoglu.emotimate.model

data class AsrsQuestion(
    val id: Int,
    val questionText: String,
    var selectedScore: Int = 0
)