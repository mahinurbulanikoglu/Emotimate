package com.mahinurbulanikoglu.emotimate.ui.testler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahinurbulanikoglu.emotimate.R
import com.mahinurbulanikoglu.emotimate.model.BeckDepresyonAdapter
import com.mahinurbulanikoglu.emotimate.model.SchemaQuestion
import com.mahinurbulanikoglu.emotimate.viewmodel.TestViewModel

class BeckDepresyonOlcegiFragment : Fragment() {

    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: BeckDepresyonAdapter
    private val viewModel: TestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beck_depresyon_olcegi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupUI(view)
    }

    private fun setupObservers() {
        // Hata durumunu dinle
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }

        // Yükleme durumunu dinle
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Eğer layout'ta bir progress bar varsa burada gösterebilirsiniz
            // binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupUI(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.questionsRecyclerView)
        val submitButton = view.findViewById<Button>(R.id.submitTestButton)

        // BDÖ soruları (her biri 0-3 arası puan alacak şekilde)
        questionList = listOf(
            SchemaQuestion(1, "Üzüntü"),
            SchemaQuestion(2, "Gelecekten ümitsizlik"),
            SchemaQuestion(3, "Kendini başarısız hissetme"),
            SchemaQuestion(4, "Kendinden hoşnut olmama"),
            SchemaQuestion(5, "Kendini suçlama"),
            SchemaQuestion(6, "Kendini cezalandırma isteği"),
            SchemaQuestion(7, "Kendinden hoşlanmama"),
            SchemaQuestion(8, "İntihar düşünceleri"),
            SchemaQuestion(9, "Ağlama"),
            SchemaQuestion(10, "Huzursuzluk"),
            SchemaQuestion(11, "İnsanlarla ilgisini yitirme"),
            SchemaQuestion(12, "Karar verme güçlüğü"),
            SchemaQuestion(13, "Kendini değersiz hissetme"),
            SchemaQuestion(14, "Enerji kaybı"),
            SchemaQuestion(15, "Uykusuzluk"),
            SchemaQuestion(16, "Sinirlilik"),
            SchemaQuestion(17, "İştah değişikliği"),
            SchemaQuestion(18, "Kilo kaybı"),
            SchemaQuestion(19, "Sağlıkla meşgul olma"),
            SchemaQuestion(20, "Cinsel ilgi kaybı"),
            SchemaQuestion(21, "Günlük faaliyetlerde azalma")
        )

        adapter = BeckDepresyonAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        submitButton.setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }

            // Cevapları Map'e dönüştür
            val answers = questionList.associate {
                "soru${it.id}" to it.selectedScore
            }

            // Firebase'e kaydet
            viewModel.saveBeckDepresyonTestResult(answers, totalScore)
            Toast.makeText(requireContext(), "Test sonuçları kaydedildi", Toast.LENGTH_SHORT).show()

            // Sonucu göster
            val message = when (totalScore) {
                in 0..9 -> "Minimal depresyon"
                in 10..16 -> "Hafif depresyon"
                in 17..29 -> "Orta düzey depresyon"
                in 30..63 -> "Şiddetli depresyon"
                else -> "Geçersiz puan"
            }

            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}