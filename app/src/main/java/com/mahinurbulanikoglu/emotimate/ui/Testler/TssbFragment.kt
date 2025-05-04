package com.mahinurbulanikoglu.emotimate.ui.testler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahinurbulanikoglu.emotimate.adapter.TssbAdapter
import com.mahinurbulanikoglu.emotimate.databinding.FragmentTssbBinding
import com.mahinurbulanikoglu.emotimate.model.TssbQuestion

class TssbFragment : Fragment() {
    private var _binding: FragmentTssbBinding? = null
    private val binding get() = _binding!!

    // Soruların sabit listesi
    private val tssbQuestions = listOf(
        TssbQuestion("Travmatik olayı tekrar tekrar ve istenmeden hatırlama"),
        TssbQuestion("Travmatik olayla ilgili kabuslar görme"),
        TssbQuestion("Travmatik olayı sanki yeniden yaşıyormuş gibi hissetme (flashback)"),
        TssbQuestion("Travmatik olayı hatırlatan bir şeye maruz kaldığınızda aşırı derecede üzülme"),
        TssbQuestion("Travmatik olayı hatırlatan şeylere karşı fiziksel tepkiler verme (örneğin kalp çarpıntısı, terleme)"),
        TssbQuestion("Travmatik olayla ilgili anıları, düşünceleri veya hisleri bilinçli olarak uzaklaştırmaya çalışmak"),
        TssbQuestion("Travmatik olayı hatırlatan dış uyaranlardan (kişiler, yerler, faaliyetler) kaçınmak"),
        TssbQuestion("Travmatik olayın önemli bir bölümünü hatırlayamama"),
        TssbQuestion("Olaya veya insanlara karşı sürekli olumsuz inançlar geliştirme (“Ben kötüyüm”, “Kimseye güvenilmez”)"),
        TssbQuestion("Kendiyle, başkalarıyla veya dünya ile ilgili sürekli olumsuz duygular hissetme (örneğin korku, öfke, suçluluk, utanç)"),
        TssbQuestion("Önceden önemli olan etkinliklere karşı ilgisini kaybetme"),
        TssbQuestion("Başkalarından uzaklaşmış veya yabancılaşmış hissetme"),
        TssbQuestion("Olumlu duyguları yaşamakta zorlanma (örneğin sevgi, mutluluk, umut)"),
        TssbQuestion("Aşırı tetikte olma hali (her an olacak bir şey varmış gibi hissetme)"),
        TssbQuestion("Kolayca irkilme ya da sıçrama tepkisi gösterme"),
        TssbQuestion("Konsantrasyon zorluğu yaşama"),
        TssbQuestion("Uykuya dalmada ya da uykuyu sürdürmede zorluk çekme"),
        TssbQuestion("Öfke patlamaları ya da sinirlilik hali"),
        TssbQuestion("Kendine ya da başkalarına zarar verme düşünceleri"),
        TssbQuestion("Riskli veya kendine zarar verici davranışlarda bulunma (aşırı alkol alma, dikkatsiz araba kullanma vb.)")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTssbBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = TssbAdapter(tssbQuestions) { position, answer ->
            tssbQuestions[position].answer = answer
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.submitButton.setOnClickListener {
            val totalScore = tssbQuestions.sumOf { it.answer.takeIf { a -> a != -1 } ?: 0 }
            // Sonucu göster veya kaydet
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}