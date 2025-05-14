package com.mahinurbulanikoglu.emotimate.repository

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

class TestRepository {
    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

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

}