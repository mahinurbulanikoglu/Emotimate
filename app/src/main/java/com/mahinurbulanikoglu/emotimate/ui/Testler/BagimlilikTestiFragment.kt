package com.mahinurbulanikoglu.emotimate.ui.Testler

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
import com.mahinurbulanikoglu.emotimate.model.SchemaQuestion
import com.mahinurbulanikoglu.emotimate.model.SchemaTestAdapter
import com.mahinurbulanikoglu.emotimate.viewmodel.TestViewModel

class BagimlilikTestiFragment : Fragment() {
    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: SchemaTestAdapter
    private val viewModel: TestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bagimlilik_testi, container, false)
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

        // Bağımlılık şeması için tüm sorular
        questionList = listOf(
            SchemaQuestion(1, "Günlük işlerimi yaparken başkalarından yardım almaya ihtiyaç duyarım."),
            SchemaQuestion(2, "Önemli kararları kendi başıma vermekte zorlanırım."),
            SchemaQuestion(3, "Hayatımda bir şeyler ters gittiğinde, başkalarının beni kurtarmasını beklerim."),
            SchemaQuestion(4, "Kendimi yalnız hissettiğimde, hemen birinin benimle olmasını isterim."),
            SchemaQuestion(5, "Başkaları olmadan kendimi güvende hissetmem."),
            SchemaQuestion(6, "Önemli konularda başkalarının fikrini almak zorunda hissederim."),
            SchemaQuestion(7, "Kendi başıma bir şeyler yapmakta zorlanırım."),
            SchemaQuestion(8, "Başkalarına bağımlı olmaktan rahatsız olmam."),
            SchemaQuestion(9, "Kendi başıma karar vermek beni endişelendirir."),
            SchemaQuestion(10, "Hayatımda bir şeyler ters gittiğinde, kendimi çaresiz hissederim.")
        )

        // Adapter ve RecyclerView bağlantılarını kur
        adapter = SchemaTestAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Butona tıklanınca puanları hesapla ve Firebase'e kaydet
        submitButton.setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }

            // Cevapları Map'e dönüştür
            val answers = questionList.associate {
                "soru${it.id}" to it.selectedScore
            }

            // Firebase'e kaydet
            viewModel.saveDependencyTestResult(answers, totalScore)

            // Sonucu göster
            val message = when {
                totalScore >= 40 -> "Yüksek düzeyde Bağımlılık Şeması"
                totalScore in 25..39 -> "Orta düzeyde Bağımlılık Şeması"
                else -> "Düşük düzeyde Bağımlılık Şeması"
            }

            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}