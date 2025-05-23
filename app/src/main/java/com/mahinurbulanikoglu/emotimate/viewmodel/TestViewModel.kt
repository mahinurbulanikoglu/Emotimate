package com.mahinurbulanikoglu.emotimate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahinurbulanikoglu.emotimate.model.ShameTestResult
import com.mahinurbulanikoglu.emotimate.model.AbandonmentTestResult
import com.mahinurbulanikoglu.emotimate.model.DependencyTestResult
import com.mahinurbulanikoglu.emotimate.model.EmotionalDeprivationTestResult
import com.mahinurbulanikoglu.emotimate.model.SocialIsolationTestResult
import com.mahinurbulanikoglu.emotimate.model.SelfSacrificeTestResult
import com.mahinurbulanikoglu.emotimate.model.SubjugationTestResult
import com.mahinurbulanikoglu.emotimate.model.EmotionalSuppressionTestResult
import com.mahinurbulanikoglu.emotimate.model.PessimismTestResult
import com.mahinurbulanikoglu.emotimate.model.FailureTestResult
import com.mahinurbulanikoglu.emotimate.repository.TestRepository
import com.mahinurbulanikoglu.emotimate.model.HaklilikTestResult
import com.mahinurbulanikoglu.emotimate.model.BeckDepresyonTestResult
import com.mahinurbulanikoglu.emotimate.model.BeckAnksiyeteTestResult
import com.mahinurbulanikoglu.emotimate.model.PanasTestResult
import com.mahinurbulanikoglu.emotimate.model.PomsTestResult
import com.mahinurbulanikoglu.emotimate.model.AsrsTestResult
import com.mahinurbulanikoglu.emotimate.model.TssbTestResult
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {
    private val repository = TestRepository()

    // Shame Test içinn
    private val _shameTestResults = MutableLiveData<List<ShameTestResult>>()
    val shameTestResults: LiveData<List<ShameTestResult>> = _shameTestResults

    // Abandonment Test için
    private val _abandonmentTestResults = MutableLiveData<List<AbandonmentTestResult>>()
    val abandonmentTestResults: LiveData<List<AbandonmentTestResult>> = _abandonmentTestResults

    // Dependency Test için
    private val _dependencyTestResults = MutableLiveData<List<DependencyTestResult>>()
    val dependencyTestResults: LiveData<List<DependencyTestResult>> = _dependencyTestResults

    // Emotional Deprivation Test için
    private val _emotionalDeprivationTestResults = MutableLiveData<List<EmotionalDeprivationTestResult>>()
    val emotionalDeprivationTestResults: LiveData<List<EmotionalDeprivationTestResult>> = _emotionalDeprivationTestResults

    // Social Isolation Test için
    private val _socialIsolationTestResults = MutableLiveData<List<SocialIsolationTestResult>>()
    val socialIsolationTestResults: LiveData<List<SocialIsolationTestResult>> = _socialIsolationTestResults

    private val _selfSacrificeTestResults = MutableLiveData<List<SelfSacrificeTestResult>>()
    val selfSacrificeTestResults: LiveData<List<SelfSacrificeTestResult>> = _selfSacrificeTestResults

    private val _subjugationTestResults = MutableLiveData<List<SubjugationTestResult>>()
    val subjugationTestResults: LiveData<List<SubjugationTestResult>> = _subjugationTestResults

    private val _emotionalSuppressionTestResults = MutableLiveData<List<EmotionalSuppressionTestResult>>()
    val emotionalSuppressionTestResults: LiveData<List<EmotionalSuppressionTestResult>> = _emotionalSuppressionTestResults

    private val _pessimismTestResults = MutableLiveData<List<PessimismTestResult>>()
    val pessimismTestResults: LiveData<List<PessimismTestResult>> = _pessimismTestResults

    private val _failureTestResults = MutableLiveData<List<FailureTestResult>>()
    val failureTestResults: LiveData<List<FailureTestResult>> = _failureTestResults

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _beckDepresyonTestResults = MutableLiveData<List<BeckDepresyonTestResult>>()
    val beckDepresyonTestResults: LiveData<List<BeckDepresyonTestResult>> = _beckDepresyonTestResults

    private val _beckAnksiyeteTestResults = MutableLiveData<List<BeckAnksiyeteTestResult>>()
    val beckAnksiyeteTestResults: LiveData<List<BeckAnksiyeteTestResult>> = _beckAnksiyeteTestResults

    private val _panasTestResults = MutableLiveData<List<PanasTestResult>>()
    val panasTestResults: LiveData<List<PanasTestResult>> = _panasTestResults

    private val _pomsTestResults = MutableLiveData<List<PomsTestResult>>()
    val pomsTestResults: LiveData<List<PomsTestResult>> = _pomsTestResults

    private val _asrsTestResults = MutableLiveData<List<AsrsTestResult>>()
    val asrsTestResults: LiveData<List<AsrsTestResult>> = _asrsTestResults

    private val _tssbTestResults = MutableLiveData<List<TssbTestResult>>()
    val tssbTestResults: LiveData<List<TssbTestResult>> = _tssbTestResults


    // Shame Test fonksiyonları
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
                        _shameTestResults.value = results
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

    // Abandonment Test fonksiyonları
    fun saveAbandonmentTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveAbandonmentTestResult(answers, totalScore)
                loadAbandonmentTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadAbandonmentTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getAbandonmentTestResults().fold(
                    onSuccess = { results ->
                        _abandonmentTestResults.value = results
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

    fun saveDependencyTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveDependencyTestResult(answers, totalScore)
                loadDependencyTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadDependencyTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getDependencyTestResults().fold(
                    onSuccess = { results ->
                        _dependencyTestResults.value = results
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

    fun saveEmotionalDeprivationTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveEmotionalDeprivationTestResult(answers, totalScore)
                loadEmotionalDeprivationTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadEmotionalDeprivationTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getEmotionalDeprivationTestResults().fold(
                    onSuccess = { results ->
                        _emotionalDeprivationTestResults.value = results
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

    fun saveSocialIsolationTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveSocialIsolationTestResult(answers, totalScore)
                loadSocialIsolationTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadSocialIsolationTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getSocialIsolationTestResults().fold(
                    onSuccess = { results ->
                        _socialIsolationTestResults.value = results
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

    fun saveSelfSacrificeTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveSelfSacrificeTestResult(answers, totalScore)
                loadSelfSacrificeTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadSelfSacrificeTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getSelfSacrificeTestResults().fold(
                    onSuccess = { results ->
                        _selfSacrificeTestResults.value = results
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

    fun saveSubjugationTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveSubjugationTestResult(answers, totalScore)
                loadSubjugationTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadSubjugationTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getSubjugationTestResults().fold(
                    onSuccess = { results ->
                        _subjugationTestResults.value = results
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

    fun saveEmotionalSuppressionTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveEmotionalSuppressionTestResult(answers, totalScore)
                loadEmotionalSuppressionTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadEmotionalSuppressionTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getEmotionalSuppressionTestResults().fold(
                    onSuccess = { results ->
                        _emotionalSuppressionTestResults.value = results
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

    fun savePessimismTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.savePessimismTestResult(answers, totalScore)
                loadPessimismTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadPessimismTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getPessimismTestResults().fold(
                    onSuccess = { results ->
                        _pessimismTestResults.value = results
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

    fun saveFailureTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.saveFailureTestResult(answers, totalScore)
                loadFailureTestResults()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadFailureTestResults() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getFailureTestResults().fold(
                    onSuccess = { resultList ->
                        _failureTestResults.value = resultList
                    },
                    onFailure = { error ->
                        _error.value = error.message
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    private val _haklilikTestResults = MutableLiveData<List<HaklilikTestResult>>()
    val haklilikTestResults: LiveData<List<HaklilikTestResult>> = _haklilikTestResults

    fun saveHaklilikTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveHaklilikTestResult(answers, totalScore)
                loadHaklilikTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadHaklilikTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getHaklilikTestResults().fold(
                    onSuccess = { results ->
                        _haklilikTestResults.value = results
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

    fun saveBeckDepresyonTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveBeckDepresyonTestResult(answers, totalScore)
                loadBeckDepresyonTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadBeckDepresyonTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getBeckDepresyonTestResults().fold(
                    onSuccess = { results ->
                        _beckDepresyonTestResults.value = results
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

    fun saveBeckAnksiyeteTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveBeckAnksiyeteTestResult(answers, totalScore)
                loadBeckAnksiyeteTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadBeckAnksiyeteTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getBeckAnksiyeteTestResults().fold(
                    onSuccess = { results ->
                        _beckAnksiyeteTestResults.value = results
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

    fun savePanasTestResult(answers: Map<String, Int>, positiveScore: Int, negativeScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.savePanasTestResult(answers, positiveScore, negativeScore)
                loadPanasTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadPanasTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getPanasTestResults().fold(
                    onSuccess = { results ->
                        _panasTestResults.value = results
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

    fun savePomsTestResult(
        answers: Map<String, Int>,
        tensionScore: Int,
        depressionScore: Int,
        angerScore: Int,
        fatigueScore: Int,
        confusionScore: Int,
        vigorScore: Int
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.savePomsTestResult(
                    answers = answers,
                    tensionScore = tensionScore,
                    depressionScore = depressionScore,
                    angerScore = angerScore,
                    fatigueScore = fatigueScore,
                    confusionScore = confusionScore,
                    vigorScore = vigorScore
                ).fold(
                    onSuccess = {
                        loadPomsTestResults()
                    },
                    onFailure = { error ->
                        _error.value = "Test sonucu kaydedilirken hata oluştu: ${error.message}"
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadPomsTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.loadPomsTestResults().fold(
                    onSuccess = { results ->
                        _pomsTestResults.value = results
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

    fun saveAsrsTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveAsrsTestResult(answers, totalScore)
                loadAsrsTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadAsrsTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getAsrsTestResults().fold(
                    onSuccess = { results ->
                        _asrsTestResults.value = results
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

    fun saveTssbTestResult(answers: Map<String, Int>, totalScore: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveTssbTestResult(answers, totalScore)
                loadTssbTestResults()
            } catch (e: Exception) {
                _error.value = "Test sonucu kaydedilirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadTssbTestResults() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getTssbTestResults().fold(
                    onSuccess = { results ->
                        _tssbTestResults.value = results
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