package com.mahinurbulanikoglu.emotimate.ui.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.mahinurbulanikoglu.emotimate.api.RetrofitClient
import com.mahinurbulanikoglu.emotimate.api.models.EmotionAnalysisRequest
import com.mahinurbulanikoglu.emotimate.utils.ApiKeyManager
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class AiViewModel : ViewModel() {
    private val _messages = MutableLiveData<List<ChatMessage>>(emptyList())
    val messages: LiveData<List<ChatMessage>> = _messages
    init {
        _messages.value = listOf(ChatMessage("Merhaba! Ben yapay zeka terapistinizim. Size nasıl yardımcı olabilirim?", false))
    }

    fun sendMessage(userText: String, emotion: String = "", userId: String = "test_user_1") {
        // Kullanıcı mesajını ekle
        val updated = _messages.value.orEmpty() + ChatMessage(userText, true)
        _messages.value = updated

        // API çağrısı
        viewModelScope.launch {
            try {
                val apiKey = ApiKeyManager.getApiKey()
                val request = EmotionAnalysisRequest(userText, emotion, userId)
                val response = RetrofitClient.emotimateApi.analyzeEmotion(apiKey, request)
                val botText = response.body()?.analysis ?: "Bir hata oluştu."
                _messages.postValue(_messages.value.orEmpty() + ChatMessage(botText, false))
            } catch (e: Exception) {
                _messages.postValue(_messages.value.orEmpty() + ChatMessage("Bir hata oluştu.", false))
            }
        }
    }
}
