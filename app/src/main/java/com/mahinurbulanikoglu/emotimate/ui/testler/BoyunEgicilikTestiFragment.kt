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

class BoyunEgicilikTestiFragment : Fragment() {
    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: SchemaTestAdapter
    private val viewModel: TestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_boyun_egicilik_testi, container, false)
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
            SchemaQuestion(1, "İnsanlarla çatışmaktan kaçınmak için isteklerimden vazgeçerim."),
            SchemaQuestion(2, "Kendi fikirlerimi ifade etmekten çekinirim."),
            SchemaQuestion(3, "Başkalarının kontrolü altında hissederim."),
            SchemaQuestion(4, "Sürekli başkalarının kurallarına uymak zorundaymışım gibi hissederim."),
            SchemaQuestion(5, "Haklı olsam bile itiraz etmem, alttan alırım."),
            SchemaQuestion(6, "İçimde öfke hissederim ama bastırırım."),
            SchemaQuestion(7, "Başkaları beni yönlendirirse işler daha kolay yürür gibi gelir."),
            SchemaQuestion(8, "Çocukluğumda da kurallara uymak zorundaydım, yoksa cezalandırılırdım."),
            SchemaQuestion(9, "Bir otorite figürü karşısında boyun eğmeye meyilliyim."),
            SchemaQuestion(10, "Hayatımı başkalarının kurallarına göre yaşarım.")
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
            viewModel.saveSubjugationTestResult(answers, totalScore)

            // Sonucu göster
            val message = when {
                totalScore >= 40 -> "Yüksek düzeyde Boyun Eğicilik Şeması"
                totalScore in 25..39 -> "Orta düzeyde Boyun Eğicilik Şeması"
                else -> "Düşük düzeyde Boyun Eğicilik Şeması"
            }

            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}