package com.mahinurbulanikoglu.emotimate.ui.testler

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
import com.mahinurbulanikoglu.emotimate.model.AsrsQuestion

class AsrsFragment : Fragment() {

    private lateinit var questionList: List<AsrsQuestion>
    private lateinit var adapter: AsrsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_asrs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionList = listOf(
            AsrsQuestion("Görevler üzerinde dikkatini sürdürmekte ne sıklıkla zorluk çekersiniz?"),
            AsrsQuestion("Yapmanız gereken işleri düzenlemekte ne sıklıkla zorluk yaşarsınız?"),
            AsrsQuestion("Yoğun zihinsel çaba gerektiren bir işe başlamakta ne sıklıkla zorlanırsınız?"),
            AsrsQuestion("Uzun süre oturmanız gereken bir durumda, kıpırdanmak veya yerinizde duramamak gibi bir durumla ne sıklıkla karşılaşırsınız?"),
            AsrsQuestion("Başkalarının sözünü kesmek ya da onların konuşmasını yarıda kesmek gibi davranışları ne sıklıkla gösterirsiniz?"),
            AsrsQuestion("Sıklıkla eşyalarınızı kaybeder misiniz? (anahtar, gözlük, telefon, vs.)"),
            AsrsQuestion("Detaylara dikkat etmeyip dikkatsizlikten dolayı hatalar yapmak."),
            AsrsQuestion("Konuşulanları dinlemekte zorluk çekmek, dalıp gitmek."),
            AsrsQuestion("Verilen talimatları tam uygulayamamak veya tamamlamadan bırakmak."),
            AsrsQuestion("Zaman yönetiminde başarısızlık, işleri ertelemek."),
            AsrsQuestion("Sık sık dikkat dağıtan uyaranlara kapılmak."),
            AsrsQuestion("Günlük aktivitelerde unutkanlık yaşamak."),
            AsrsQuestion("Sabırsızlıkla sırada beklemekte zorlanmak."),
            AsrsQuestion("Sık sık düşünmeden hareket etmek."),
            AsrsQuestion("Konuşmaları veya faaliyetleri bölen şekilde davranmak."),
            AsrsQuestion("Huzursuzluk veya yerinde duramama hissi."),
            AsrsQuestion("Aşırı konuşkan olma."),
            AsrsQuestion("Durmadan hareket etme isteği (motor takılmış gibi hissetme).")
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.asrsRecyclerView)
        adapter = AsrsAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        view.findViewById<Button>(R.id.btnAsrsSubmit).setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }
            Toast.makeText(requireContext(), "Toplam Puanınız: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}