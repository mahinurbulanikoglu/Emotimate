package com.mahinurbulanikoglu.emotimate.model

import com.mahinurbulanikoglu.emotimate.ui.model.PanasQuestion
import com.mahinurbulanikoglu.emotimate.ui.model.PanasAdapter
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


class PanasFragment : Fragment() {

    private lateinit var questionList: List<PanasQuestion>
    private lateinit var adapter: PanasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_panas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // PANAS soruları
        questionList = listOf(
            // Pozitif Duygu Maddeleri
            PanasQuestion("İlgi"),
            PanasQuestion("Heyecan"),
            PanasQuestion("Güçlü hissetme"),
            PanasQuestion("Coşku"),
            PanasQuestion("Uyanıklık"),
            PanasQuestion("İlham"),
            PanasQuestion("Kararlı hissetme"),
            PanasQuestion("Gurur"),
            PanasQuestion("Aktiflik"),
            PanasQuestion("Neşe"),
            // Negatif Duygu Maddeleri
            PanasQuestion("Suçluluk"),
            PanasQuestion("Düşmanlık"),
            PanasQuestion("Korku"),
            PanasQuestion("Sinirlilik"),
            PanasQuestion("Utanç"),
            PanasQuestion("Sinir bozukluğu"),
            PanasQuestion("Sinmişlik"),
            PanasQuestion("Kaygı"),
            PanasQuestion("Üzüntü"),
            PanasQuestion("Endişe")
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.panasRecyclerView)
        adapter = PanasAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        view.findViewById<Button>(R.id.btnPanasSubmit).setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }
            Toast.makeText(requireContext(), "Toplam Puanınız: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}