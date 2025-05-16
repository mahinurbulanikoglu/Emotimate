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
import com.mahinurbulanikoglu.emotimate.model.SchemaQuestion
import com.mahinurbulanikoglu.emotimate.model.SchemaTestAdapter
import com.mahinurbulanikoglu.emotimate.viewmodel.TestViewModel

class KaramsarlikTestiFragment : Fragment() {
    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: SchemaTestAdapter
    private val viewModel: TestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_karamsarlik_testi, container, false)
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

        // Sorularını buraya ekledim:
        questionList = listOf(
            SchemaQuestion(1, "İyi şeyler olduğunda bile kötü bir şey olacakmış gibi hissederim."),
            SchemaQuestion(2, "Geleceğe dair hep olumsuz düşüncelerim vardır."),
            SchemaQuestion(3, "Sorunları büyütme eğilimindeyim."),
            SchemaQuestion(4, "Kötü senaryolar üretmekten kendimi alıkoyamam."),
            SchemaQuestion(5, "Hayatın hep zorlayıcı ve tehditkâr yanlarına odaklanırım."),
            SchemaQuestion(6, "Umutlu olmak bana safça geliyor."),
            SchemaQuestion(7, "Bir şeyin kötü gitmesi beni şaşırtmaz, zaten öyle olur."),
            SchemaQuestion(8, "İyi şeylerin uzun sürmeyeceğini düşünürüm."),
            SchemaQuestion(9, "Kendimi güvence altına almak için hep en kötü ihtimale göre plan yaparım."),
            SchemaQuestion(10, "İçsel olarak hep bir tehlike ya da felaket beklentisi taşırım.")
        )

        adapter = SchemaTestAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        submitButton.setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }

            // Cevapları Map'e dönüştür
            val answers = questionList.associate {
                "soru${it.id}" to it.selectedScore
            }

            // Firebase'e kaydet
            viewModel.savePessimismTestResult(answers, totalScore)

            // Sonucu göster
            val message = when {
                totalScore >= 40 -> "Yüksek düzeyde Karamsarlık / Felaketçilik Şeması"
                totalScore in 25..39 -> "Orta düzeyde Karamsarlık / Felaketçilik Şeması"
                else -> "Düşük düzeyde Karamsarlık / Felaketçilik Şeması"
            }

            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}