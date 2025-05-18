package com.mahinurbulanikoglu.emotimate.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class MoodEntry(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val mood: String = "",
    val note: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val activities: List<String> = emptyList(),
    val triggers: List<String> = emptyList()
) 