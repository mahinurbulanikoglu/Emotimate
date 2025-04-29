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

class KendiniFedaTestiFragment : Fragment() {

    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: SchemaTestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_kendini_feda_testi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.questionsRecyclerView)
        val submitButton = view.findViewById<Button>(R.id.submitTestButton)

        // Sorularını buraya ekledim:
        questionList = listOf(
            SchemaQuestion(1, "Başkalarının ihtiyaçlarını kendi ihtiyaçlarımın önüne koyarım."),
            SchemaQuestion(2, "Kendi isteklerimi ifade ettiğimde suçluluk duyarım."),
            SchemaQuestion(3, "İnsanlara yardım etmekte kendimi sorumlu hissederim, sınır koyamam."),
            SchemaQuestion(4, "Kendi ihtiyaçlarımı bastırmayı alışkanlık haline getirdim."),
            SchemaQuestion(5, "İnsanların beni sevmesi için fedakârlık yapmalıyım diye düşünürüm."),
            SchemaQuestion(6, "Hayır demekte zorlanırım, çünkü karşımdaki üzülmesin isterim."),
            SchemaQuestion(7, "Sürekli başkaları için koştururken tükenmiş hissederim."),
            SchemaQuestion(8, "Kendim için bir şey yapmak “bencilce” gelir."),
            SchemaQuestion(9, "Kendi ihtiyaçlarım görmezden geliniyor ama bunu sorun etmiyorum."),
            SchemaQuestion(10, "Çevremdeki insanların mutsuzluğu, benim suçummuş gibi hissederim.")
        )

        adapter = SchemaTestAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        submitButton.setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }
            val message = when {
                totalScore >= 40 -> "Yüksek düzeyde Kendini Feda Şeması"
                totalScore in 25..39 -> "Orta düzeyde Kendini Feda Şeması"
                else -> "Düşük düzeyde Kendini Feda Şeması"
            }
            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}