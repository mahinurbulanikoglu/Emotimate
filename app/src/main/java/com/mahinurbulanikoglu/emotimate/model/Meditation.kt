package com.mahinurbulanikoglu.emotimate.model

import android.os.Parcelable
import com.mahinurbulanikoglu.emotimate.R
import kotlinx.parcelize.Parcelize

enum class ContentType {
    MEDITATION,
    BREATHING,
    RELAXING_MUSIC
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

val breathingItems = listOf(
    ContentItem(
        title = "Nefes Meditasyonu 5 Dakika",
        imageResId = R.drawable.resim,
        audioResId = R.raw.meditasyon_1nefes_meditasyonu_5_dakika,
        contentType = ContentType.BREATHING
    ),
    ContentItem(
        title = "Nefes Meditasyonu 20 Dakika",
        imageResId = R.drawable.mindfulness,
        audioResId = R.raw.meditasyon_2nefes_meditasyonu_20_dakika,
        contentType = ContentType.BREATHING
    ),

    ContentItem(
        title = "Nefes Meditasyonu 20 Dakika(Yönlendirmesiz)",
        imageResId = R.drawable.rain,
        audioResId = R.raw.meditasyon_3nefes_meditasyonu_20_dakika_yonlendirmesiz,
        contentType = ContentType.BREATHING
    ),
    ContentItem(
        title = "Beden Tarama",
        imageResId = R.drawable.meditation,
        audioResId = R.raw.meditasyon_4beden_tarama,
        contentType = ContentType.BREATHING
    ),
)

val relaxingMusicItems = listOf(
    ContentItem(
        title = "İçsel Yolculuk",
        imageResId = R.drawable.sunset,
        audioResId = R.raw.the_inner_calling,
        contentType = ContentType.RELAXING_MUSIC
    ),
    ContentItem(
        title = "Sakinliğin Derinliği",
        imageResId = R.drawable.ocean,
        audioResId = R.raw.our_peaceful_ocean,
        contentType = ContentType.RELAXING_MUSIC
    ),
    ContentItem(
        title = "Doğanın Derinliklerinde",
        imageResId = R.drawable.island,
        audioResId = R.raw.deep_into_nature,
        contentType = ContentType.RELAXING_MUSIC
    ),
)


