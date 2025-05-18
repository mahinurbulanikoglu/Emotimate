package com.mahinurbulanikoglu.emotimate.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mahinurbulanikoglu.emotimate.model.ChatMessage
import com.mahinurbulanikoglu.emotimate.model.MoodEntry
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebaseRepository {
    private val db = FirebaseFirestore.getInstance()
    private val chatCollection = db.collection("chats")
    private val moodCollection = db.collection("moods")

    // Sohbet mesajlarını kaydet
    suspend fun saveChatMessage(message: ChatMessage) = withContext(Dispatchers.IO) {
        try {
            chatCollection.add(message).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Kullanıcının sohbet geçmişini getir
    fun getChatHistory(userId: String): Flow<List<ChatMessage>> = flow {
        try {
            val snapshot = chatCollection
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val messages = snapshot.toObjects(ChatMessage::class.java)
            emit(messages)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    // Duygu durumu kaydet
    suspend fun saveMoodEntry(moodEntry: MoodEntry) = withContext(Dispatchers.IO) {
        try {
            moodCollection.add(moodEntry).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Kullanıcının duygu durumu geçmişini getir
    fun getMoodHistory(userId: String): Flow<List<MoodEntry>> = flow {
        try {
            val snapshot = moodCollection
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val moods = snapshot.toObjects(MoodEntry::class.java)
            emit(moods)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    // Son 7 günlük duygu durumu istatistiklerini getir
    suspend fun getWeeklyMoodStats(userId: String): Map<String, Int> = withContext(Dispatchers.IO) {
        try {
            val oneWeekAgo = com.google.firebase.Timestamp(
                System.currentTimeMillis() / 1000 - 7 * 24 * 60 * 60,
                0
            )
            
            val snapshot = moodCollection
                .whereEqualTo("userId", userId)
                .whereGreaterThan("timestamp", oneWeekAgo)
                .get()
                .await()
            
            val moods = snapshot.toObjects(MoodEntry::class.java)
            moods.groupBy { it.mood }
                .mapValues { it.value.size }
        } catch (e: Exception) {
            emptyMap()
        }
    }
} 