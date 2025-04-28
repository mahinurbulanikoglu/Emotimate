package com.mahinurbulanikoglu.emotimate.model

import com.mahinurbulanikoglu.emotimate.R

data class Book(
val title: String,
val authors: List<Author>,
val description: String,
val imageUrl: String,
val cover: Cover)

data class Cover(
    val medium: String // Kitap kapağının URL'si
)

data class Author(
    val name: String // veya daha fazla alan eklenebilir
)
