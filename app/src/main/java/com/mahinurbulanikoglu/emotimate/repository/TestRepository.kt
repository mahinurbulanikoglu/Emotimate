package com.mahinurbulanikoglu.emotimate.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.mahinurbulanikoglu.emotimate.model.AbandonmentTestResult
import com.mahinurbulanikoglu.emotimate.model.DependencyTestResult
import com.mahinurbulanikoglu.emotimate.model.EmotionalDeprivationTestResult
import com.mahinurbulanikoglu.emotimate.model.ShameTestResult
import com.mahinurbulanikoglu.emotimate.model.SocialIsolationTestResult
import com.mahinurbulanikoglu.emotimate.model.SelfSacrificeTestResult
import com.mahinurbulanikoglu.emotimate.model.SubjugationTestResult
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

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
}