package com.mahinurbulanikoglu.emotimate.ui.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.mahinurbulanikoglu.emotimate.api.RetrofitClient
import com.mahinurbulanikoglu.emotimate.api.models.EmotionAnalysisRequest
import com.mahinurbulanikoglu.emotimate.model.ChatMessage
import com.mahinurbulanikoglu.emotimate.repository.FirebaseRepository
import com.mahinurbulanikoglu.emotimate.utils.ApiKeyManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import com.google.firebase.auth.FirebaseAuth

class AiViewModel : ViewModel() {
    private val repository = FirebaseRepository()
    private val auth = FirebaseAuth.getInstance()
    
    private val _messages = MutableLiveData<List<ChatMessage>>(emptyList())
    val messages: LiveData<List<ChatMessage>> = _messages
    
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadChatHistory()
        _messages.value = listOf(ChatMessage(
            text = "Merhaba! Ben yapay zeka terapistinizim. Size nasıl yardımcı olabilirim?",
            isUser = false
        ))
    }

    private fun loadChatHistory() {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            repository.getChatHistory(userId).collect { chatMessages ->
                _messages.value = chatMessages
            }
        }
    }

    fun sendMessage(userText: String, emotion: String = "") {
        val userId = auth.currentUser?.uid ?: return
        _isLoading.value = true

        // Kullanıcı mesajını kaydet
        val userMessage = ChatMessage(
            userId = userId,
            text = userText,
            isUser = true,
            emotion = emotion
        )
        
        viewModelScope.launch {
            repository.saveChatMessage(userMessage)
            
            try {
                val apiKey = ApiKeyManager.getApiKey()
                val request = EmotionAnalysisRequest(userText, emotion, userId)
                val response = RetrofitClient.emotimateApi.analyzeEmotion(apiKey, request)
                
                // AI yanıtını kaydet
                val aiMessage = ChatMessage(
                    userId = userId,
                    text = response.body()?.analysis ?: "Bir hata oluştu.",
                    isUser = false,
                    emotion = emotion,
                    analysis = response.body()?.analysis ?: "",
                    suggestions = response.body()?.suggestions ?: emptyList()
                )
                
                repository.saveChatMessage(aiMessage)
            } catch (e: Exception) {
                // Hata mesajını kaydet
                val errorMessage = ChatMessage(
                    userId = userId,
                    text = "Üzgünüm, bir hata oluştu. Lütfen tekrar deneyin.",
                    isUser = false
                )
                repository.saveChatMessage(errorMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
