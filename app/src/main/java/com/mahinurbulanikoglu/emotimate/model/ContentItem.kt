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
        title = "Mindfulness Of Body And Breath",
        imageResId = R.drawable.mindfulnessimage,
        audioResId = R.raw.meditation1,
        contentType = ContentType.MEDITATION
    ),
    ContentItem(
        title = "The Body Scan",
        imageResId = R.drawable.mindfulnessimage,
        audioResId = R.raw.meditation2,
        contentType = ContentType.MEDITATION
    ),
    ContentItem(
        title = "Mindful Movement",
        imageResId = R.drawable.mindfulnessimage,
        audioResId = R.raw.meditation3,
        contentType = ContentType.MEDITATION
    ),
    ContentItem(
        title = "Breath And Body",
        imageResId = R.drawable.mindfulnessimage,
        audioResId = R.raw.meditation4,
        contentType = ContentType.MEDITATION
    ),
    ContentItem(
        title = "Sounds And Thoughts",
        imageResId = R.drawable.mindfulnessimage,
        audioResId = R.raw.meditation5,
        contentType = ContentType.MEDITATION
    ),

)


val articleItems = listOf(
    ContentItem(
        title = "Mindfulness Üzerine",
        imageResId = R.drawable.rain,
        contentType = ContentType.ARTICLE,
        description = "Zihni anda tutmak"
    ),
    ContentItem(
        title = "Stres Yönetimi",
        imageResId = R.drawable.rain,
        contentType = ContentType.ARTICLE,
        description = "Stresi azaltmanın yolları"
    )
)



