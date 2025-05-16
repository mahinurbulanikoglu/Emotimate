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

class SosyalIzolasyonTestiFragment : Fragment() {
    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: SchemaTestAdapter
    private val viewModel: TestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sosyal_izolasyon_testi, container, false)
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

        // Sosyal İzolasyon şeması için tüm sorular
        questionList = listOf(
            SchemaQuestion(1, "İnsanların arasında kendimi farklı hissederim."),
            SchemaQuestion(2, "Sanki dışlanmışım ya da ait değilmişim gibi hissederim."),
            SchemaQuestion(3, "İnsanlarla kolayca yakınlık kuramam."),
            SchemaQuestion(4, "Kalabalık bir ortamda yalnız hissederim."),
            SchemaQuestion(5, "Genellikle arkadaş gruplarına ya da topluluklara ait hissetmem."),
            SchemaQuestion(6, "Diğer insanlar kolaylıkla kaynaşırken ben uzakta kalırım."),
            SchemaQuestion(7, "Sanki herkes birlikte, ben ise dışarıda kalmışım gibi gelir."),
            SchemaQuestion(8, "Beni gerçekten anlayabilecek insan bulmak zordur."),
            SchemaQuestion(9, "Hayatım boyunca hep biraz 'ayrıksı' oldum."),
            SchemaQuestion(10, "Sosyal ortamlarda içten içe kendimi güvensiz hissederim.")
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
            viewModel.saveSocialIsolationTestResult(answers, totalScore)

            // Sonucu göster
            val message = when {
                totalScore >= 40 -> "Yüksek düzeyde Sosyal İzolasyon / Yabancılık Şeması"
                totalScore in 25..39 -> "Orta düzeyde Sosyal İzolasyon / Yabancılık Şeması"
                else -> "Düşük düzeyde Sosyal İzolasyon / Yabancılık Şeması"
            }

            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}