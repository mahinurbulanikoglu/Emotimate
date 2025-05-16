package com.mahinurbulanikoglu.emotimate.ui.ai

data class ChatMessage(
    val text: String,
    val isUser: Boolean // true: kullanıcı, false: yapay zeka
)