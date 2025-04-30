package com.mahinurbulanikoglu.emotimate.ui.Testler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahinurbulanikoglu.emotimate.R
import com.mahinurbulanikoglu.emotimate.model.SchemaQuestion

class BeckAnksiyeteOlcegiFragment : Fragment() {

    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: BeckDepresyonAdapter // 4 seçenekli adapterı tekrar kullanıyoruz

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beck_anksiyete_olcegi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.questionsRecyclerView)
        val submitButton = view.findViewById<Button>(R.id.submitTestButton)

        // BAÖ soruları (her biri 0-3 arası puan alacak şekilde)
        questionList = listOf(
            SchemaQuestion(1, "Vücudumun bazı bölgelerinde uyuşma ya da karıncalanma hissettim."),
            SchemaQuestion(2, "Sıcak basmaları yaşadım."),
            SchemaQuestion(3, "Bacaklarımda zayıflık ya da titreme hissettim."),
            SchemaQuestion(4, "Kendimi rahatlatmakta zorlandım."),
            SchemaQuestion(5, "Aniden ya da mantıksız şekilde korkuya kapıldım."),
            SchemaQuestion(6, "Başım döndü ya da kendimi sersemlemiş hissettim."),
            SchemaQuestion(7, "Kalbim hızla attı ya da çarpıntı hissettim."),
            SchemaQuestion(8, "Dengemi kaybediyor gibi hissettim."),
            SchemaQuestion(9, "Panik haline kapıldım."),
            SchemaQuestion(10, "Bayılacakmış gibi oldum."),
            SchemaQuestion(11, "Nefes almakta zorlandım."),
            SchemaQuestion(12, "Boğuluyormuş gibi hissettim."),
            SchemaQuestion(13, "Ellerde veya vücutta titreme yaşadım."),
            SchemaQuestion(14, "Kontrolümü kaybedecekmişim gibi hissettim."),
            SchemaQuestion(15, "Ölüm korkusu yaşadım."),
            SchemaQuestion(16, "Mide bulantısı ya da karın ağrısı hissettim."),
            SchemaQuestion(17, "Soğuk ter döktüm."),
            SchemaQuestion(18, "Yutma güçlüğü yaşadım."),
            SchemaQuestion(19, "İçsel bir titreme hissi yaşadım (vücudumun içinde)."),
            SchemaQuestion(20, "Kendimi tuhaf ya da gerçek dışı hissettim (sanki kendimden kopmuş gibi)."),
            SchemaQuestion(21, "Yüzümde kızarma ya da sıcaklık artışı hissettim.")
        )

        adapter = BeckDepresyonAdapter(questionList) // 4 seçenekli adapterı kullanıyoruz
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        submitButton.setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }
            val message = when (totalScore) {
                in 0..7 -> "Minimal anksiyete"
                in 8..15 -> "Hafif anksiyete"
                in 16..25 -> "Orta düzey anksiyete"
                in 26..63 -> "Şiddetli anksiyete"
                else -> "Geçersiz puan"
            }
            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}