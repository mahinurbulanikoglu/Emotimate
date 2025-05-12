package com.mahinurbulanikoglu.emotimate.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.mahinurbulanikoglu.emotimate.model.ShameTestResult
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

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
} 