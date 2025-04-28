package com.mahinurbulanikoglu.emotimate.model

import com.mahinurbulanikoglu.emotimate.R

data class Movie(
    val title: String,
    val director: String,
    val description: String,
    val imageUrl: String
)
val movieItems = listOf(
    ContentItem(
        title = "Başlangıç",
        imageResId = R.drawable.rain,
        contentType = ContentType.MOVIE,
        description = "Bilim kurgu filmi"
    ),
    ContentItem(
        title = "Interstellar",
        imageResId = R.drawable.rain,
        contentType = ContentType.MOVIE,
        description = "Uzay ve zaman yolculuğu"
    )
)