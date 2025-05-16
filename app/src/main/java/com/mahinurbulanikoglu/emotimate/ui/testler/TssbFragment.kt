package com.mahinurbulanikoglu.emotimate.ui.testler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahinurbulanikoglu.emotimate.adapter.TssbAdapter
import com.mahinurbulanikoglu.emotimate.databinding.FragmentTssbBinding
import com.mahinurbulanikoglu.emotimate.model.TssbQuestion
import com.mahinurbulanikoglu.emotimate.viewmodel.TestViewModel

class TssbFragment : Fragment() {
    private var _binding: FragmentTssbBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TestViewModel by viewModels()

    // Soruların sabit listesi
    private val tssbQuestions = listOf(
        TssbQuestion(1, "Travmatik olayı tekrar tekrar ve istenmeden hatırlama"),
        TssbQuestion(2, "Travmatik olayla ilgili kabuslar görme"),
        TssbQuestion(3, "Travmatik olayı sanki yeniden yaşıyormuş gibi hissetme (flashback)"),
        TssbQuestion(4, "Travmatik olayı hatırlatan bir şeye maruz kaldığınızda aşırı derecede üzülme"),
        TssbQuestion(5, "Travmatik olayı hatırlatan şeylere karşı fiziksel tepkiler verme (örneğin kalp çarpıntısı, terleme)"),
        TssbQuestion(6, "Travmatik olayla ilgili anıları, düşünceleri veya hisleri bilinçli olarak uzaklaştırmaya çalışmak"),
        TssbQuestion(7, "Travmatik olayı hatırlatan dış uyaranlardan (kişiler, yerler, faaliyetler) kaçınmak"),
        TssbQuestion(8, "Travmatik olayın önemli bir bölümünü hatırlayamama"),
        TssbQuestion(9, "Olaya veya insanlara karşı sürekli olumsuz inançlar geliştirme ('Ben kötüyüm', 'Kimseye güvenilmez')"),
        TssbQuestion(10, "Kendiyle, başkalarıyla veya dünya ile ilgili sürekli olumsuz duygular hissetme (örneğin korku, öfke, suçluluk, utanç)"),
        TssbQuestion(11, "Önceden önemli olan etkinliklere karşı ilgisini kaybetme"),
        TssbQuestion(12, "Başkalarından uzaklaşmış veya yabancılaşmış hissetme"),
        TssbQuestion(13, "Olumlu duyguları yaşamakta zorlanma (örneğin sevgi, mutluluk, umut)"),
        TssbQuestion(14, "Aşırı tetikte olma hali (her an olacak bir şey varmış gibi hissetme)"),
        TssbQuestion(15, "Kolayca irkilme ya da sıçrama tepkisi gösterme"),
        TssbQuestion(16, "Konsantrasyon zorluğu yaşama"),
        TssbQuestion(17, "Uykuya dalmada ya da uykuyu sürdürmede zorluk çekme"),
        TssbQuestion(18, "Öfke patlamaları ya da sinirlilik hali"),
        TssbQuestion(19, "Kendine ya da başkalarına zarar verme düşünceleri"),
        TssbQuestion(20, "Riskli veya kendine zarar verici davranışlarda bulunma (aşırı alkol alma, dikkatsiz araba kullanma vb.)")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTssbBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupUI()
    }

    private fun setupObservers() {
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Eğer layout'ta bir progress bar varsa burada gösterebilirsiniz
        }
    }

    private fun setupUI() {
        val adapter = TssbAdapter(tssbQuestions) { position, answer ->
            tssbQuestions[position].answer = answer
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.submitButton.setOnClickListener {
            val totalScore = tssbQuestions.sumOf { it.answer.takeIf { a -> a != -1 } ?: 0 }

            // Cevapları Map'e dönüştür
            val answers = tssbQuestions.associate {
                "soru${it.id}" to it.answer
            }

            // Firebase'e kaydet
            viewModel.saveTssbTestResult(answers, totalScore)

            // Sonucu göster
            val message = when {
                totalScore <= 10 -> "Belirti yok veya klinik açıdan anlamlı değil"
                totalScore in 11..32 -> "Hafif belirtiler; izleme önerilir"
                totalScore in 33..49 -> "Orta düzeyde TSSB olasılığı; psikolojik destek önerilir"
                else -> "Yüksek TSSB riski; profesyonel yardım şiddetle önerilir"
            }

            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}