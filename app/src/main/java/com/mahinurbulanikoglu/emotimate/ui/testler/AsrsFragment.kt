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
import com.mahinurbulanikoglu.emotimate.model.AsrsQuestion
import com.mahinurbulanikoglu.emotimate.viewmodel.TestViewModel
import com.mahinurbulanikoglu.emotimate.model.AsrsAdapter

class AsrsFragment : Fragment() {
    private lateinit var questionList: List<AsrsQuestion>
    private lateinit var adapter: AsrsAdapter
    private val viewModel: TestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_asrs, container, false)
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
            // Eğer layout'ta bir progress bar varsa burada gösterebilirsiniz
        }
    }

    private fun setupUI(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.asrsRecyclerView)
        val submitButton = view.findViewById<Button>(R.id.btnAsrsSubmit)

        questionList = listOf(
            AsrsQuestion(1, "Görevler üzerinde dikkatini sürdürmekte ne sıklıkla zorluk çekersiniz?"),
            AsrsQuestion(2, "Yapmanız gereken işleri düzenlemekte ne sıklıkla zorluk yaşarsınız?"),
            AsrsQuestion(3, "Yoğun zihinsel çaba gerektiren bir işe başlamakta ne sıklıkla zorlanırsınız?"),
            AsrsQuestion(4, "Uzun süre oturmanız gereken bir durumda, kıpırdanmak veya yerinizde duramamak gibi bir durumla ne sıklıkla karşılaşırsınız?"),
            AsrsQuestion(5, "Başkalarının sözünü kesmek ya da onların konuşmasını yarıda kesmek gibi davranışları ne sıklıkla gösterirsiniz?"),
            AsrsQuestion(6, "Sıklıkla eşyalarınızı kaybeder misiniz? (anahtar, gözlük, telefon, vs.)"),
            AsrsQuestion(7, "Detaylara dikkat etmeyip dikkatsizlikten dolayı hatalar yapmak."),
            AsrsQuestion(8, "Konuşulanları dinlemekte zorluk çekmek, dalıp gitmek."),
            AsrsQuestion(9, "Verilen talimatları tam uygulayamamak veya tamamlamadan bırakmak."),
            AsrsQuestion(10, "Zaman yönetiminde başarısızlık, işleri ertelemek."),
            AsrsQuestion(11, "Sık sık dikkat dağıtan uyaranlara kapılmak."),
            AsrsQuestion(12, "Günlük aktivitelerde unutkanlık yaşamak."),
            AsrsQuestion(13, "Sabırsızlıkla sırada beklemekte zorlanmak."),
            AsrsQuestion(14, "Sık sık düşünmeden hareket etmek."),
            AsrsQuestion(15, "Konuşmaları veya faaliyetleri bölen şekilde davranmak."),
            AsrsQuestion(16, "Huzursuzluk veya yerinde duramama hissi."),
            AsrsQuestion(17, "Aşırı konuşkan olma."),
            AsrsQuestion(18, "Durmadan hareket etme isteği (motor takılmış gibi hissetme).")
        )

        adapter = AsrsAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        submitButton.setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }

            // Cevapları Map'e dönüştür
            val answers = questionList.associate {
                "soru${it.id}" to it.selectedScore
            }

            // Firebase'e kaydet
            viewModel.saveAsrsTestResult(answers, totalScore)

            // Sonucu göster
            val message = when {
                totalScore <= 16 -> "Yetişkinlerde DEHB olasılığı düşüktür."
                totalScore in 17..23 -> "Yetişkinlerde DEHB olasılığı vardır; daha fazla değerlendirme önerilir."
                else -> "Yetişkinlerde DEHB olasılığı yüksektir; kapsamlı klinik değerlendirme gereklidir."
            }

            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}