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

class BasarisizlikTestiFragment : Fragment() {
    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: SchemaTestAdapter
    private val viewModel: TestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_basarisizlik_testi, container, false)
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
            SchemaQuestion(1, "Kendimi çoğu zaman yetersiz hissederim."),
            SchemaQuestion(2, "Diğer insanlara göre daha az başarılıyım."),
            SchemaQuestion(3, "Hangi alana girersem gireyim sonunda başarısız olacağımı düşünürüm."),
            SchemaQuestion(4, "Zeki ya da yetenekli biri olduğuma inanmam."),
            SchemaQuestion(5, "İnsanlar benden beklenti içine girdiklerinde kaygılanırım."),
            SchemaQuestion(6, "Yeni bir işe başlarken genellikle başarısız olacağıma inanırım."),
            SchemaQuestion(7, "Akademik ya da mesleki başarılarımı küçümserim."),
            SchemaQuestion(8, "Çocukken başarısızlıklarım sürekli eleştirilirdi."),
            SchemaQuestion(9, "Kendimi sürekli başkalarıyla kıyaslarım ve yetersiz bulurum."),
            SchemaQuestion(10, "Bir işi hakkıyla yapamayacağımı düşündüğüm için başlamam.")
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
            viewModel.saveFailureTestResult(answers, totalScore)

            // Sonucu göster
            val message = when {
                totalScore >= 40 -> "Yüksek düzeyde Başarısızlık Şeması"
                totalScore in 25..39 -> "Orta düzeyde Başarısızlık Şeması"
                else -> "Düşük düzeyde Başarısızlık Şeması"
            }

            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}