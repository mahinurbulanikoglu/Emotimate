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
import com.mahinurbulanikoglu.emotimate.model.PanasQuestion
import com.mahinurbulanikoglu.emotimate.viewmodel.TestViewModel
import com.mahinurbulanikoglu.emotimate.ui.model.PanasAdapter

class PanasFragment : Fragment() {

    private lateinit var questionList: List<PanasQuestion>
    private lateinit var adapter: PanasAdapter
    private val viewModel: TestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_panas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupUI(view)
    }

    private fun setupObservers() {
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Progress bar gösterme/gizleme işlemleri
        }
    }

    private fun setupUI(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.panasRecyclerView)
        val submitButton = view.findViewById<Button>(R.id.btnPanasSubmit)

        // PANAS soruları - Pozitif ve negatif duygular ayrı ayrı
        questionList = listOf(
            // Pozitif duygular (ilk 10 soru)
            PanasQuestion("İlgi duyulan"),
            PanasQuestion("Enerjik"),
            PanasQuestion("Güçlü"),
            PanasQuestion("Heyecanlı"),
            PanasQuestion("Gururlu"),
            PanasQuestion("Uyanık"),
            PanasQuestion("İlham veren"),
            PanasQuestion("Dikkatli"),
            PanasQuestion("Aktif"),
            PanasQuestion("Neşeli"),
            // Negatif duygular (son 10 soru)
            PanasQuestion("Üzüntülü"),
            PanasQuestion("Endişeli"),
            PanasQuestion("Suçlu"),
            PanasQuestion("Korkmuş"),
            PanasQuestion("Düşmanca"),
            PanasQuestion("Sinirli"),
            PanasQuestion("Utanmış"),
            PanasQuestion("Korkmuş"),
            PanasQuestion("Üzgün"),
            PanasQuestion("Alarmda")
        )

        adapter = PanasAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        submitButton.setOnClickListener {
            val answers = questionList.associate { it.question to it.selectedScore }
            
            // Pozitif ve negatif skorları ayrı ayrı hesapla
            val positiveScore = questionList.take(10).sumOf { it.selectedScore }
            val negativeScore = questionList.takeLast(10).sumOf { it.selectedScore }
            
            viewModel.savePanasTestResult(answers, positiveScore, negativeScore)
            
            // Sonuçları göster
            val positiveMessage = when (positiveScore) {
                in 10..19 -> "Düşük pozitif duygu"
                in 20..29 -> "Orta düzey pozitif duygu"
                in 30..39 -> "Yüksek pozitif duygu"
                in 40..50 -> "Çok yüksek pozitif duygu"
                else -> "Geçersiz puan"
            }

            val negativeMessage = when (negativeScore) {
                in 10..19 -> "Düşük negatif duygu"
                in 20..29 -> "Orta düzey negatif duygu"
                in 30..39 -> "Yüksek negatif duygu"
                in 40..50 -> "Çok yüksek negatif duygu"
                else -> "Geçersiz puan"
            }

            Toast.makeText(
                context,
                "Pozitif Duygu: $positiveMessage (Puan: $positiveScore)\n" +
                "Negatif Duygu: $negativeMessage (Puan: $negativeScore)",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}