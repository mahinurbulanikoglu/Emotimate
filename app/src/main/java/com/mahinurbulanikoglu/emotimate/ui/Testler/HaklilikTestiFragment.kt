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

class HaklilikTestiFragment : Fragment() {

    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: SchemaTestAdapter
    private val viewModel: TestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_haklilik_testi, container, false)
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
            SchemaQuestion(1, "Kurallar bana göre değilmiş gibi hissederim."),
            SchemaQuestion(2, "İstediğim şey hemen olsun isterim."),
            SchemaQuestion(3, "Başkalarının ihtiyaçlarını çok da önemsemem."),
            SchemaQuestion(4, "Benden daha az başarılı insanların görüşlerini dikkate almam."),
            SchemaQuestion(5, "Eleştirilmek beni öfkelendirir, çünkü hep haklı olduğumu düşünürüm."),
            SchemaQuestion(6, "Beklentilerim karşılanmadığında kolayca öfkelenirim."),
            SchemaQuestion(7, "İnsanların bana ayrıcalık tanıması gerektiğine inanırım."),
            SchemaQuestion(8, "Hızlı sonuç almayı hak ettiğimi düşünürüm."),
            SchemaQuestion(9, "İlişkilerde benim dediklerimin olması gerektiğine inanırım."),
            SchemaQuestion(10, "Çocukken her istediğim karşılandığında kendimi özel hissederdim.")
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
            viewModel.saveHaklilikTestResult(answers, totalScore)
            Toast.makeText(requireContext(), "Test sonuçları kaydedildi", Toast.LENGTH_SHORT).show()

            // Sonucu göster
            val message = when {
                totalScore >= 55 -> "Yüksek düzeyde Haklılık"
                totalScore in 35..54 -> "Orta düzeyde Haklılık"
                else -> "Düşük düzeyde Haklılık"
            }

            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
    }
