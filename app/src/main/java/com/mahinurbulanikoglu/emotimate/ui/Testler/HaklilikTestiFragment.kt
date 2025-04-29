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

class HaklilikTestiFragment : Fragment() {

    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: SchemaTestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_haklilik_testi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            val message = when {
                totalScore >= 40 -> "Yüksek düzeyde Haklılık Şeması"
                totalScore in 25..39 -> "Orta düzeyde Haklılık Şeması"
                else -> "Düşük düzeyde Haklılık Şeması"
            }
            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}