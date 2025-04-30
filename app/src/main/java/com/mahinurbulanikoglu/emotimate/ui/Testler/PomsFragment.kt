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

class PomsFragment : Fragment() {

    private lateinit var questionList: List<PomsQuestion>
    private lateinit var adapter: PomsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // POMS soruları
        questionList = listOf(
            PomsQuestion("Gergin"),
            PomsQuestion("Endişeli"),
            PomsQuestion("Huzursuz"),
            PomsQuestion("Sinirli"),
            PomsQuestion("Titrek"),
            PomsQuestion("Depresif"),
            PomsQuestion("Mutsuz"),
            PomsQuestion("Umutsuz"),
            PomsQuestion("Kendine acıyan"),
            PomsQuestion("Ağlamaklı"),
            PomsQuestion("Kızgın"),
            PomsQuestion("Öfkeli"),
            PomsQuestion("Düşmanca"),
            PomsQuestion("Saldırgan"),
            PomsQuestion("Sinirli"),
            PomsQuestion("Enerjik"),
            PomsQuestion("Canlı"),
            PomsQuestion("Güçlü"),
            PomsQuestion("Uyanık"),
            PomsQuestion("Dinamik"),
            PomsQuestion("Bitkin"),
            PomsQuestion("Halsiz"),
            PomsQuestion("Yorgun"),
            PomsQuestion("Uykulu"),
            PomsQuestion("Tükenmiş"),
            PomsQuestion("Dalgın"),
            PomsQuestion("Zihni bulanık"),
            PomsQuestion("Kafası karışık"),
            PomsQuestion("Kararsız"),
            PomsQuestion("Dikkatsiz"),
            PomsQuestion("Moralli"),
            PomsQuestion("Cesur"),
            PomsQuestion("Rahat"),
            PomsQuestion("İyimser"),
            PomsQuestion("Mutlu"),
            PomsQuestion("Üzgün"),
            PomsQuestion("İçine kapanık"),
            PomsQuestion("Tedirgin"),
            PomsQuestion("Panik içinde"),
            PomsQuestion("Keyifsiz"),
            PomsQuestion("Yalnız"),
            PomsQuestion("Değersiz"),
            PomsQuestion("Kendine kızgın"),
            PomsQuestion("İsyankar"),
            PomsQuestion("Gergin bir şekilde savunmacı"),
            PomsQuestion("Düşünceli"),
            PomsQuestion("Konsantre olabilen"),
            PomsQuestion("Kararlı"),
            PomsQuestion("Kontrollü"),
            PomsQuestion("Neşeli"),
            PomsQuestion("Dost canlısı"),
            PomsQuestion("Eğlenceli"),
            PomsQuestion("Sempatik"),
            PomsQuestion("Rahat hissetme"),
            PomsQuestion("Sevgi dolu"),
            PomsQuestion("Yardımsever"),
            PomsQuestion("İlgili"),
            PomsQuestion("Sevecen"),
            PomsQuestion("Karamsar"),
            PomsQuestion("Kırılgan"),
            PomsQuestion("Kırılmış"),
            PomsQuestion("Endişeli"),
            PomsQuestion("Aşırı duyarlı"),
            PomsQuestion("Duygusal olarak yorgun"),
            PomsQuestion("Bitap düşmüş")
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.pomsRecyclerView)
        adapter = PomsAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        view.findViewById<Button>(R.id.btnPomsSubmit).setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }
            Toast.makeText(requireContext(), "Toplam Puanınız: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}