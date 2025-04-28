package com.mahinurbulanikoglu.emotimate.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.mahinurbulanikoglu.emotimate.R

@Parcelize
data class Book(
    val title: String?,
    val authors: List<Author>?,
    val description: String?,
    val imageUrl: String?,
    val cover: Cover?
) : Parcelable

@Parcelize
data class Cover(
    val medium: String
) : Parcelable


@Parcelize
data class Author(
    val name: String?
) : Parcelable