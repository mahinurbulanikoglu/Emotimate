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
import com.mahinurbulanikoglu.emotimate.model.SchemaTestAdapter

class DuygusalBastirmaTestiFragment : Fragment() {

    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: SchemaTestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_duygusal_bastirma_testi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.questionsRecyclerView)
        val submitButton = view.findViewById<Button>(R.id.submitTestButton)

        // Sorularını buraya ekledim:
        questionList = listOf(
            SchemaQuestion(1, "Duygularımı açıkça göstermekten çekinirim."),
            SchemaQuestion(2, "İnsanların önünde ağlamaktan ya da heyecanlanmaktan utanırım."),
            SchemaQuestion(3, "Hissettiklerimi belli edersem zayıf görüneceğimi düşünürüm."),
            SchemaQuestion(4, "Çocukken duygularımı göstermem hoş karşılanmazdı."),
            SchemaQuestion(5, "Duygularımı bastırmam gerektiğini düşünürüm."),
            SchemaQuestion(6, "Kendimi ifade etmek bana zor gelir."),
            SchemaQuestion(7, "Kontrolü kaybetmekten korkarım."),
            SchemaQuestion(8, "İnsanların beni duygusal görmesini istemem."),
            SchemaQuestion(9, "İçimde yoğun duygular olsa da bunları dışarıya belli etmem."),
            SchemaQuestion(10, "Hissettiklerimi sadece kendi içimde yaşarım.")
        )

        adapter = SchemaTestAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        submitButton.setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }
            val message = when {
                totalScore >= 40 -> "Yüksek düzeyde Duygusal Bastırma Şeması"
                totalScore in 25..39 -> "Orta düzeyde Duygusal Bastırma Şeması"
                else -> "Düşük düzeyde Duygusal Bastırma Şeması"
            }
            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}