package com.mahinurbulanikoglu.emotimate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahinurbulanikoglu.emotimate.model.ShameTestResult
import com.mahinurbulanikoglu.emotimate.repository.TestRepository
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {
    private val repository = TestRepository()
    
    private val _testResults = MutableLiveData<List<ShameTestResult>>()
    val testResults: LiveData<List<ShameTestResult>> = _testResults
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveShameTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveShameTestResult(answers, totalScore)
                loadShameTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadShameTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getShameTestResults().fold(
                    onSuccess = { results ->
                        _testResults.value = results
                    },
                    onFailure = { error ->
                        _error.value = "Test sonuçları yüklenirken hata oluştu: ${error.message}"
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
} 