package com.mahinurbulanikoglu.emotimate.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.mahinurbulanikoglu.emotimate.model.AbandonmentTestResult
import com.mahinurbulanikoglu.emotimate.model.DependencyTestResult
import com.mahinurbulanikoglu.emotimate.model.EmotionalDeprivationTestResult
import com.mahinurbulanikoglu.emotimate.model.EmotionalSuppressionTestResult
import com.mahinurbulanikoglu.emotimate.model.ShameTestResult
import com.mahinurbulanikoglu.emotimate.model.SocialIsolationTestResult
import com.mahinurbulanikoglu.emotimate.model.SelfSacrificeTestResult
import com.mahinurbulanikoglu.emotimate.model.SubjugationTestResult
import com.mahinurbulanikoglu.emotimate.model.PessimismTestResult
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import com.mahinurbulanikoglu.emotimate.model.FailureTestResult
import com.mahinurbulanikoglu.emotimate.model.HaklilikTestResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.mahinurbulanikoglu.emotimate.model.BeckDepresyonTestResult
import com.mahinurbulanikoglu.emotimate.model.BeckAnksiyeteTestResult
import com.mahinurbulanikoglu.emotimate.model.PanasTestResult
import com.mahinurbulanikoglu.emotimate.model.PomsTestResult
import com.mahinurbulanikoglu.emotimate.model.AsrsTestResult
import com.mahinurbulanikoglu.emotimate.model.TssbTestResult

class TestRepository {
    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

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

    suspend fun saveShameTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = ShameTestResult.getInterpretation(totalScore)

            val testResult = ShameTestResult(
                userId = userId,
                date = date,
                totalScore = totalScore,
                interpretation = interpretation,
                answers = answers
            )

            val testRef = database.getReference("users/$userId/test_results/shame_test")
            testRef.push().setValue(testResult).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getShameTestResults(): Result<List<ShameTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val testRef = database.getReference("users/$userId/test_results/shame_test")
            val snapshot = testRef.get().await()

            val results = mutableListOf<ShameTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<ShameTestResult>()?.let { result ->
                    results.add(result)
                }
            }

            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveAbandonmentTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = AbandonmentTestResult.getInterpretation(totalScore)

            val testResult = AbandonmentTestResult(
                userId = userId,
                date = date,
                totalScore = totalScore,
                interpretation = interpretation,
                answers = answers
            )

            val testRef = database.getReference("users/$userId/test_results/abandonment_test")
            testRef.push().setValue(testResult).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAbandonmentTestResults(): Result<List<AbandonmentTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val testRef = database.getReference("users/$userId/test_results/abandonment_test")
            val snapshot = testRef.get().await()

            val results = mutableListOf<AbandonmentTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<AbandonmentTestResult>()?.let { result ->
                    results.add(result)
                }
            }

            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveDependencyTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = DependencyTestResult.getInterpretation(totalScore)

            val testResult = DependencyTestResult(
                userId = userId,
                date = date,
                totalScore = totalScore,
                interpretation = interpretation,
                answers = answers
            )

            val testRef = database.getReference("users/$userId/test_results/dependency_test")
            testRef.push().setValue(testResult).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDependencyTestResults(): Result<List<DependencyTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val testRef = database.getReference("users/$userId/test_results/dependency_test")
            val snapshot = testRef.get().await()

            val results = mutableListOf<DependencyTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<DependencyTestResult>()?.let { result ->
                    results.add(result)
                }
            }

            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveEmotionalDeprivationTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = EmotionalDeprivationTestResult.getInterpretation(totalScore)

            val testResult = EmotionalDeprivationTestResult(
                userId = userId,
                date = date,
                totalScore = totalScore,
                interpretation = interpretation,
                answers = answers
            )

            val testRef = database.getReference("users/$userId/test_results/emotional_deprivation_test")
            testRef.push().setValue(testResult).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getEmotionalDeprivationTestResults(): Result<List<EmotionalDeprivationTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val testRef = database.getReference("users/$userId/test_results/emotional_deprivation_test")
            val snapshot = testRef.get().await()

            val results = mutableListOf<EmotionalDeprivationTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<EmotionalDeprivationTestResult>()?.let { result ->
                    results.add(result)
                }
            }

            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveSocialIsolationTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = SocialIsolationTestResult.getInterpretation(totalScore)

            val testResult = SocialIsolationTestResult(
                userId = userId,
                date = date,
                totalScore = totalScore,
                interpretation = interpretation,
                answers = answers
            )

            val testRef = database.getReference("users/$userId/test_results/social_isolation_test")
            testRef.push().setValue(testResult).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSocialIsolationTestResults(): Result<List<SocialIsolationTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val testRef = database.getReference("users/$userId/test_results/social_isolation_test")
            val snapshot = testRef.get().await()

            val results = mutableListOf<SocialIsolationTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<SocialIsolationTestResult>()?.let { result ->
                    results.add(result)
                }
            }

            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveSelfSacrificeTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = SelfSacrificeTestResult.getInterpretation(totalScore)

            val testResult = SelfSacrificeTestResult(
                userId = userId,
                date = date,
                totalScore = totalScore,
                interpretation = interpretation,
                answers = answers
            )

            val testRef = database.getReference("users/$userId/test_results/self_sacrifice_test")
            testRef.push().setValue(testResult).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSelfSacrificeTestResults(): Result<List<SelfSacrificeTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val testRef = database.getReference("users/$userId/test_results/self_sacrifice_test")
            val snapshot = testRef.get().await()

            val results = mutableListOf<SelfSacrificeTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<SelfSacrificeTestResult>()?.let { result ->
                    results.add(result)
                }
            }

            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveSubjugationTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = SubjugationTestResult.getInterpretation(totalScore)

            val testResult = SubjugationTestResult(
                userId = userId,
                date = date,
                totalScore = totalScore,
                interpretation = interpretation,
                answers = answers
            )

            val testRef = database.getReference("users/$userId/test_results/subjugation_test")
            testRef.push().setValue(testResult).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSubjugationTestResults(): Result<List<SubjugationTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val testRef = database.getReference("users/$userId/test_results/subjugation_test")
            val snapshot = testRef.get().await()

            val results = mutableListOf<SubjugationTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<SubjugationTestResult>()?.let { result ->
                    results.add(result)
                }
            }

            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveEmotionalSuppressionTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = EmotionalSuppressionTestResult.getInterpretation(totalScore)

            val testResult = EmotionalSuppressionTestResult(
                userId = userId,
                date = date,
                totalScore = totalScore,
                interpretation = interpretation,
                answers = answers
            )

            val testRef = database.getReference("users/$userId/test_results/emotional_suppression_test")
            testRef.push().setValue(testResult).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getEmotionalSuppressionTestResults(): Result<List<EmotionalSuppressionTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val testRef = database.getReference("users/$userId/test_results/emotional_suppression_test")
            val snapshot = testRef.get().await()

            val results = mutableListOf<EmotionalSuppressionTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<EmotionalSuppressionTestResult>()?.let { result ->
                    results.add(result)
                }
            }

            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun savePessimismTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = PessimismTestResult.getInterpretation(totalScore)

            val testResult = PessimismTestResult(
                userId = userId,
                date = date,
                totalScore = totalScore,
                interpretation = interpretation,
                answers = answers
            )

            val testRef = database.getReference("users/$userId/test_results/pessimism_test")
            testRef.push().setValue(testResult).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPessimismTestResults(): Result<List<PessimismTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val testRef = database.getReference("users/$userId/test_results/pessimism_test")
            val snapshot = testRef.get().await()

            val results = mutableListOf<PessimismTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<PessimismTestResult>()?.let { result ->
                    results.add(result)
                }
            }

            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveFailureTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = FailureTestResult.getInterpretation(totalScore)

            val testResult = FailureTestResult(
                userId = userId,
                date = date,
                totalScore = totalScore,
                interpretation = interpretation,
                answers = answers
            )

            val testRef = database.getReference("users/$userId/test_results/failure_test")
            testRef.push().setValue(testResult).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFailureTestResults(): Result<List<FailureTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val testRef = database.getReference("users/$userId/test_results/failure_test")
            val snapshot = testRef.get().await()

            val results = mutableListOf<FailureTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<FailureTestResult>()?.let { result ->
                    results.add(result)
                }
            }

            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun saveHaklilikTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = HaklilikTestResult.getInterpretation(totalScore)
            val testResult = HaklilikTestResult(
                userId = userId,
                date = date,
                totalScore = totalScore,
                interpretation = interpretation,
                answers = answers
            )
            val testRef = database.getReference("users/$userId/test_results/haklilik_test")
            testRef.push().setValue(testResult).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getHaklilikTestResults(): Result<List<HaklilikTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))
            val testRef = database.getReference("users/$userId/test_results/haklilik_test")
            val snapshot = testRef.get().await()
            val results = mutableListOf<HaklilikTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<HaklilikTestResult>()?.let { result ->
                    results.add(result)
                }
            }
            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveBeckDepresyonTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = BeckDepresyonTestResult.getInterpretation(totalScore)
            val testResult = BeckDepresyonTestResult(
                userId = userId,
                date = date,
                totalScore = totalScore,
                interpretation = interpretation,
                answers = answers
            )
            val testRef = database.getReference("users/$userId/test_results/beck_depresyon_test")
            testRef.push().setValue(testResult).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBeckDepresyonTestResults(): Result<List<BeckDepresyonTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))
            val testRef = database.getReference("users/$userId/test_results/beck_depresyon_test")
            val snapshot = testRef.get().await()
            val results = mutableListOf<BeckDepresyonTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<BeckDepresyonTestResult>()?.let { result ->
                    results.add(result)
                }
            }
            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveBeckAnksiyeteTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = BeckAnksiyeteTestResult.getInterpretation(totalScore)
            val testResult = BeckAnksiyeteTestResult(
                userId = userId,
                date = date,
                totalScore = totalScore,
                interpretation = interpretation,
                answers = answers
            )
            val testRef = database.getReference("users/$userId/test_results/beck_anksiyete_test")
            testRef.push().setValue(testResult).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBeckAnksiyeteTestResults(): Result<List<BeckAnksiyeteTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))
            val testRef = database.getReference("users/$userId/test_results/beck_anksiyete_test")
            val snapshot = testRef.get().await()
            val results = mutableListOf<BeckAnksiyeteTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<BeckAnksiyeteTestResult>()?.let { result ->
                    results.add(result)
                }
            }
            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun savePanasTestResult(answers: Map<String, Int>, positiveScore: Int, negativeScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val positiveInterpretation = PanasTestResult.getPositiveInterpretation(positiveScore)
            val negativeInterpretation = PanasTestResult.getNegativeInterpretation(negativeScore)
            val testResult = PanasTestResult(
                userId = userId,
                date = date,
                positiveScore = positiveScore,
                negativeScore = negativeScore,
                positiveInterpretation = positiveInterpretation,
                negativeInterpretation = negativeInterpretation,
                answers = answers
            )
            val testRef = database.getReference("users/$userId/test_results/panas_test")
            testRef.setValue(testResult)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPanasTestResults(): Result<List<PanasTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))
            val testRef = database.getReference("users/$userId/test_results/panas_test")
            val snapshot = testRef.get().await()
            val results = snapshot.children.mapNotNull { it.getValue(PanasTestResult::class.java) }
            _panasTestResults.postValue(results)
            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun savePomsTestResult(
        answers: Map<String, Int>,
        tensionScore: Int,
        depressionScore: Int,
        angerScore: Int,
        fatigueScore: Int,
        confusionScore: Int,
        vigorScore: Int
    ): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))
            Log.d("TestRepository", "Kullanıcı ID: $userId")
            
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            Log.d("TestRepository", "Tarih: $date")

            // TMD hesaplama
            val totalMoodDisturbance = tensionScore + depressionScore + angerScore +
                                     fatigueScore + confusionScore - vigorScore
            Log.d("TestRepository", "TMD: $totalMoodDisturbance")

            val testResult = PomsTestResult(
                userId = userId,
                date = date,
                tensionScore = tensionScore,
                depressionScore = depressionScore,
                angerScore = angerScore,
                fatigueScore = fatigueScore,
                confusionScore = confusionScore,
                vigorScore = vigorScore,
                totalMoodDisturbance = totalMoodDisturbance,
                answers = answers
            )

            Log.d("TestRepository", "Test sonucu oluşturuldu: $testResult")

            val testRef = database.getReference("users/$userId/test_results/poms_test")
            Log.d("TestRepository", "Veritabanı yolu: users/$userId/test_results/poms_test")
            
            try {
                testRef.push().setValue(testResult).await()
                Log.d("TestRepository", "Test sonucu başarıyla kaydedildi")
                
                // Kaydedilen veriyi doğrula
                val savedData = testRef.get().await()
                Log.d("TestRepository", "Kaydedilen veri doğrulandı: $savedData")
                
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e("TestRepository", "Firebase kaydetme hatası", e)
                Result.failure(e)
            }
        } catch (e: Exception) {
            Log.e("TestRepository", "Test sonucu kaydedilirken hata oluştu", e)
            Result.failure(e)
        }
    }

    suspend fun loadPomsTestResults(): Result<List<PomsTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))
            val testRef = database.getReference("users/$userId/test_results/poms_test")

            val results = testRef.get().await().children.mapNotNull { it.getValue(PomsTestResult::class.java) }
            _pomsTestResults.postValue(results)
            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveAsrsTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = when {
                totalScore <= 16 -> "Yetişkinlerde DEHB olasılığı düşüktür."
                totalScore in 17..23 -> "Yetişkinlerde DEHB olasılığı vardır; daha fazla değerlendirme önerilir."
                else -> "Yetişkinlerde DEHB olasılığı yüksektir; kapsamlı klinik değerlendirme gereklidir."
            }

            val testResult = AsrsTestResult(
                userId = userId,
                timestamp = System.currentTimeMillis(),
                totalScore = totalScore,
                answers = answers
            )

            val testRef = database.getReference("users/$userId/test_results/asrs_test")
            testRef.push().setValue(testResult).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAsrsTestResults(): Result<List<AsrsTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val testRef = database.getReference("users/$userId/test_results/asrs_test")
            val snapshot = testRef.get().await()

            val results = mutableListOf<AsrsTestResult>()
            snapshot.children.forEach { child ->
                child.getValue<AsrsTestResult>()?.let { result ->
                    results.add(result)
                }
            }

            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveTssbTestResult(answers: Map<String, Int>, totalScore: Int): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val interpretation = when {
                totalScore <= 10 -> "Belirti yok veya klinik açıdan anlamlı değil"
                totalScore in 11..32 -> "Hafif belirtiler; izleme önerilir"
                totalScore in 33..49 -> "Orta düzeyde TSSB olasılığı; psikolojik destek önerilir"
                else -> "Yüksek TSSB riski; profesyonel yardım şiddetle önerilir"
            }

            val testResult = TssbTestResult(
                userId = userId,
                timestamp = System.currentTimeMillis(),
                totalScore = totalScore,
                answers = answers
            )

            val testRef = database.getReference("users/$userId/test_results/tssb_test")
            testRef.push().setValue(testResult).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTssbTestResults(): Result<List<TssbTestResult>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Kullanıcı girişi yapılmamış"))

            val testRef = database.getReference("users/$userId/test_results/tssb_test")
            val snapshot = testRef.get().await()

            val results = snapshot.children.mapNotNull { child ->
                child.getValue(TssbTestResult::class.java)
            }.sortedByDescending { it.timestamp }

            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}