package com.mahinurbulanikoglu.emotimate.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class ChatMessage(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val text: String = "",
    val isUser: Boolean = true,
    val emotion: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val analysis: String = "",
    val suggestions: List<String> = emptyList()
) 