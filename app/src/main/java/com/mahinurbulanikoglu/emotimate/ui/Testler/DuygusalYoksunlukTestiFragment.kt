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

class DuygusalYoksunlukTestiFragment : Fragment() {

    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: SchemaTestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_duygusal_yoksunluk_testi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.questionsRecyclerView)
        val submitButton = view.findViewById<Button>(R.id.submitTestButton)

        // Sorularını buraya ekledim:
        questionList = listOf(
            SchemaQuestion(1, "Kimse benim duygusal ihtiyaçlarımı gerçekten karşılamadı."),
            SchemaQuestion(2, "Kimse beni gerçekten anlamıyor."),
            SchemaQuestion(3, "Bana içtenlikle ilgi gösteren biri yok."),
            SchemaQuestion(4, "Duygusal desteğe ihtiyacım olduğunda yanımda kimse olmaz."),
            SchemaQuestion(5, "Sevgi ve şefkat görmek konusunda hep bir eksiklik hissederim."),
            SchemaQuestion(6, "İnsanlara ne kadar yakın olursam olayım, yine de bir boşluk hissederim."),
            SchemaQuestion(7, "Biriyle ne kadar konuşursam konuşayım, tam olarak anlaşılamam."),
            SchemaQuestion(8, "İlişkilerimde derin bir duygusal bağ kurmakta zorlanırım."),
            SchemaQuestion(9, "İçimde kronik bir yalnızlık duygusu vardır."),
            SchemaQuestion(10, "Hep eksik bir şeyler varmış gibi hissederim ama ne olduğunu bilemem.")
        )

        adapter = SchemaTestAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        submitButton.setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }
            val message = when {
                totalScore >= 40 -> "Yüksek düzeyde Duygusal Yoksunluk Şeması"
                totalScore in 25..39 -> "Orta düzeyde Duygusal Yoksunluk Şeması"
                else -> "Düşük düzeyde Duygusal Yoksunluk Şeması"
            }
            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}