package com.mahinurbulanikoglu.emotimate.model

data class TssbQuestion(
    val id: Int,
    val questionText: String,
    var answer: Int = -1
)
