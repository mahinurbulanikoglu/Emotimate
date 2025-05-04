package com.mahinurbulanikoglu.emotimate.ui.kisilik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahinurbulanikoglu.emotimate.adapter.KisilikAdapter
import com.mahinurbulanikoglu.emotimate.databinding.FragmentKisilikTestiBinding
import com.mahinurbulanikoglu.emotimate.model.KisilikQuestion

class KisilikTestiFragment : Fragment() {
    private var _binding: FragmentKisilikTestiBinding? = null
    private val binding get() = _binding!!

    // Soru listesini burada tanımla:
    private val kisilikQuestions = listOf(
        KisilikQuestion("Canlı ve enerjik bir kişiyim."),
        KisilikQuestion("Başkalarının duygularına duyarlıyımdır."),
        KisilikQuestion("Dağınık ve düzensizimdir."),
        KisilikQuestion("Endişeli ve kolay gerilen biriyimdir."),
        KisilikQuestion("Yeni fikirlere ve deneyimlere açığım."),
        KisilikQuestion("Konuşkan ve dışa dönük biriyim."),
        KisilikQuestion("Başkalarının sorunlarıyla ilgilenirim."),
        KisilikQuestion("İşleri planlı ve düzenli yaparım."),
        KisilikQuestion("Kolayca üzülürüm."),
        KisilikQuestion("Hayal gücüm zengindir."),
        KisilikQuestion("Sessiz ve içine kapanık biriyimdir."),
        KisilikQuestion("Başkalarına karşı şüpheci ve soğuk davranırım."),
        KisilikQuestion("Detaylara dikkat ederim."),
        KisilikQuestion("Sakin kalmayı başarırım."),
        KisilikQuestion("Geleneksel fikirlere bağlıyım."),
        KisilikQuestion("Sosyal ortamlarda rahatımdır."),
        KisilikQuestion("İnsanlara güven duyarım."),
        KisilikQuestion("Başladığım işleri bitiririm."),
        KisilikQuestion("Zihinsel olarak dengesizimdir."),
        KisilikQuestion("Sanatsal yönüm kuvvetlidir."),
        KisilikQuestion("Geri planda kalmayı tercih ederim."),
        KisilikQuestion("Diğer insanlara karşı ilgisizimdir."),
        KisilikQuestion("Plansız hareket ederim."),
        KisilikQuestion("Çabuk moralim bozulur."),
        KisilikQuestion("Keşfetmeyi severim."),
        KisilikQuestion("Etkili konuşmalar yapabilirim."),
        KisilikQuestion("Yardım etmeyi severim."),
        KisilikQuestion("Kendi düzenimi sağlamakta iyiyimdir."),
        KisilikQuestion("Stres karşısında zayıfım."),
        KisilikQuestion("Yaratıcıyım."),
        KisilikQuestion("Çekinirim ve kolay kolay konuşmam."),
        KisilikQuestion("İnsanlara karşı mesafeli davranırım."),
        KisilikQuestion("İşleri savsaklarım."),
        KisilikQuestion("Hızlı ruh hali değişiklikleri yaşarım."),
        KisilikQuestion("Yeniliklerden heyecan duyarım."),
        KisilikQuestion("Liderlik yapmayı severim."),
        KisilikQuestion("Diğerlerinin iyiliğini düşünürüm."),
        KisilikQuestion("Sorumluluk sahibiyim."),
        KisilikQuestion("Ruhsal olarak kararsızım."),
        KisilikQuestion("Yeni düşüncelere açığım."),
        KisilikQuestion("Konuşmaya başlamadan önce uzun düşünürüm."),
        KisilikQuestion("İnsanlara karşı ilgisizim."),
        KisilikQuestion("Zamanı iyi yönetemem."),
        KisilikQuestion("Tutarlı bir kişiliğe sahibim.")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKisilikTestiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = KisilikAdapter(kisilikQuestions) { position, answer ->
            kisilikQuestions[position].answer = answer
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.submitButton.setOnClickListener {
            // Sonuç hesaplama ve gösterme
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}