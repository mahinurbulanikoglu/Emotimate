package com.mahinurbulanikoglu.emotimate.model

import android.os.Parcelable
import com.mahinurbulanikoglu.emotimate.R
import kotlinx.parcelize.Parcelize

enum class ContentType {
    MEDITATION,
    BOOK,
    MOVIE,
    ARTICLE
}

@Parcelize
data class ContentItem(
    val title: String,
    val imageResId: Int,
    val audioResId: Int? = null, // sadece meditasyon için
    val description: String? = null,
    val contentType: ContentType
) : Parcelable


val meditationItems = listOf(
    ContentItem(
        title = "Yağmur Meditasyonu",
        imageResId = R.drawable.emotimatefoto,
        audioResId = R.raw.rain_meditation_music,
        contentType = ContentType.MEDITATION
    ),
    ContentItem(
        title = "Dalga Sesi Meditasyonu",
        imageResId = R.drawable.emotimatefoto,
        audioResId = R.raw.rain_meditation_music,
        contentType = ContentType.MEDITATION
    ),
)
