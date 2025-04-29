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

class BagimlilikTestiFragment : Fragment() {

    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: SchemaTestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bagimlilik_testi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.questionsRecyclerView)
        val submitButton = view.findViewById<Button>(R.id.submitTestButton)

        // Sorularını buraya ekledim:
        questionList = listOf(
            SchemaQuestion(1, "Kendi başıma karar vermekte zorlanırım."),
            SchemaQuestion(2, "Genellikle başkalarının fikrine güvenmeyi tercih ederim."),
            SchemaQuestion(3, "Başkalarının desteği olmadan yeni bir işe başlamaktan korkarım."),
            SchemaQuestion(4, "Maddi ya da duygusal olarak birilerine ihtiyaç duyarım."),
            SchemaQuestion(5, "Hayatımda bana rehberlik edecek birinin olması beni rahatlatır."),
            SchemaQuestion(6, "Başkalarının yokluğunda işler ters gider diye endişelenirim."),
            SchemaQuestion(7, "Yeni şeyler denemekte kararsız kalırım çünkü hata yapmaktan korkarım."),
            SchemaQuestion(8, "Kendimi sık sık çaresiz hissederim."),
            SchemaQuestion(9, "Sık sık kendime \"Bunu tek başıma yapamam\" derim."),
            SchemaQuestion(10, "Bağımsız olmak beni tedirgin eder.")
        )

        adapter = SchemaTestAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        submitButton.setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }
            val message = when {
                totalScore >= 40 -> "Yüksek düzeyde Bağımlılık Şeması"
                totalScore in 25..39 -> "Orta düzeyde Bağımlılık Şeması"
                else -> "Düşük düzeyde Bağımlılık Şeması"
            }
            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}