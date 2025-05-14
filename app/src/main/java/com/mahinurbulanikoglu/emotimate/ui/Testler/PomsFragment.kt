package com.mahinurbulanikoglu.emotimate.ui.Testler

import android.os.Bundle
import android.util.Log
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
import com.mahinurbulanikoglu.emotimate.ui.testler.PomsQuestion
import com.mahinurbulanikoglu.emotimate.ui.testler.PomsAdapter
import com.mahinurbulanikoglu.emotimate.model.PomsTestResult
import com.mahinurbulanikoglu.emotimate.viewmodel.TestViewModel

class PomsFragment : Fragment() {

    private lateinit var questionList: List<PomsQuestion>
    private lateinit var adapter: PomsAdapter
    private val viewModel: TestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupUI(view)
    }

    private fun setupObservers() {
        // Hata durumunu dinle
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Log.e("PomsFragment", "Hata oluştu: $error")
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }

        // Yükleme durumunu dinle
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            Log.d("PomsFragment", "Yükleme durumu: $isLoading")
        }
    }

    private fun setupUI(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.pomsRecyclerView)
        val submitButton = view.findViewById<Button>(R.id.btnPomsSubmit)

        // POMS soruları
        questionList = listOf(
            // Tension (Gerilim/Kaygı) - 1-5
            PomsQuestion("Gergin"),
            PomsQuestion("Endişeli"),
            PomsQuestion("Huzursuz"),
            PomsQuestion("Sinirli"),
            PomsQuestion("Titrek"),
            // Depression (Depresyon/Çökkünlük) - 6-10
            PomsQuestion("Depresif"),
            PomsQuestion("Mutsuz"),
            PomsQuestion("Umutsuz"),
            PomsQuestion("Kendine acıyan"),
            PomsQuestion("Ağlamaklı"),
            // Anger (Öfke/Düşmanlık) - 11-15
            PomsQuestion("Kızgın"),
            PomsQuestion("Öfkeli"),
            PomsQuestion("Düşmanca"),
            PomsQuestion("Saldırgan"),
            PomsQuestion("Sinirli"),
            // Vigor (Canlılık/Enerji) - 16-20
            PomsQuestion("Enerjik"),
            PomsQuestion("Canlı"),
            PomsQuestion("Güçlü"),
            PomsQuestion("Uyanık"),
            PomsQuestion("Dinamik"),
            // Fatigue (Yorgunluk/Bıkkınlık) - 21-25
            PomsQuestion("Bitkin"),
            PomsQuestion("Halsiz"),
            PomsQuestion("Yorgun"),
            PomsQuestion("Uykulu"),
            PomsQuestion("Tükenmiş"),
            // Confusion (Zihin bulanıklığı/kararsızlık) - 26-30
            PomsQuestion("Dalgın"),
            PomsQuestion("Zihni bulanık"),
            PomsQuestion("Kafası karışık"),
            PomsQuestion("Kararsız"),
            PomsQuestion("Dikkatsiz")
        )

        adapter = PomsAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        submitButton.setOnClickListener {
            // Tüm soruların cevaplanıp cevaplanmadığını kontrol et
            val unansweredQuestions = questionList.filter { it.selectedScore == 0 }
            if (unansweredQuestions.isNotEmpty()) {
                Toast.makeText(context, "Lütfen tüm soruları cevaplayın", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Alt boyut puanlarını hesapla
            val tensionScore = questionList.subList(0, 5).sumOf { it.selectedScore }
            val depressionScore = questionList.subList(5, 10).sumOf { it.selectedScore }
            val angerScore = questionList.subList(10, 15).sumOf { it.selectedScore }
            val vigorScore = questionList.subList(15, 20).sumOf { it.selectedScore }
            val fatigueScore = questionList.subList(20, 25).sumOf { it.selectedScore }
            val confusionScore = questionList.subList(25, 30).sumOf { it.selectedScore }

            // Cevapları Map'e dönüştür
            val answers = questionList.associate { it.questionText to it.selectedScore }

            Log.d("PomsFragment", "Test sonuçları hesaplanıyor...")
            Log.d("PomsFragment", "Tension: $tensionScore")
            Log.d("PomsFragment", "Depression: $depressionScore")
            Log.d("PomsFragment", "Anger: $angerScore")
            Log.d("PomsFragment", "Vigor: $vigorScore")
            Log.d("PomsFragment", "Fatigue: $fatigueScore")
            Log.d("PomsFragment", "Confusion: $confusionScore")

            // Firebase'e kaydet
            viewModel.savePomsTestResult(
                answers = answers,
                tensionScore = tensionScore,
                depressionScore = depressionScore,
                angerScore = angerScore,
                fatigueScore = fatigueScore,
                confusionScore = confusionScore,
                vigorScore = vigorScore
            )

            // Sonuçları göster
            val tmd = tensionScore + depressionScore + angerScore + fatigueScore + confusionScore - vigorScore
            val message = """
                Gerilim: ${PomsTestResult.getInterpretation(tensionScore, "TENSION")} (Puan: $tensionScore)
                Depresyon: ${PomsTestResult.getInterpretation(depressionScore, "DEPRESSION")} (Puan: $depressionScore)
                Öfke: ${PomsTestResult.getInterpretation(angerScore, "ANGER")} (Puan: $angerScore)
                Yorgunluk: ${PomsTestResult.getInterpretation(fatigueScore, "FATIGUE")} (Puan: $fatigueScore)
                Zihin Bulanıklığı: ${PomsTestResult.getInterpretation(confusionScore, "CONFUSION")} (Puan: $confusionScore)
                Canlılık: ${PomsTestResult.getInterpretation(vigorScore, "VIGOR")} (Puan: $vigorScore)
                
                Toplam Ruh Hali Bozukluğu (TMD): ${PomsTestResult.getInterpretation(tmd, "TMD")} (Puan: $tmd)
            """.trimIndent()

            Log.d("PomsFragment", "Sonuç mesajı: $message")
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}