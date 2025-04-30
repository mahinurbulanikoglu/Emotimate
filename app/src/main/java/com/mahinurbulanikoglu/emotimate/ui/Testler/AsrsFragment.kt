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
            AsrsQuestion("Detaylara dikkat etmeden hatalar yapar mısınız?"),
            AsrsQuestion("Bir görevi sürdürürken dikkatinizin kolayca dağılması olur mu?"),
            AsrsQuestion("Kendinizi bir konuşmayı dinlerken zihinsel olarak başka yerlere kaymış şekilde bulur musunuz?"),
            AsrsQuestion("Bir görevi tamamlamakta organize olamamak sizi zorlar mı?"),
            AsrsQuestion("Zamanı etkili kullanamayıp işler birikiyor mu?"),
            AsrsQuestion("Bir görevi tamamlamak için çaba göstermeniz gereken işlerden kaçınır veya erteler misiniz?"),
            AsrsQuestion("Uzun süre oturmanız gereken durumlarda kıpırdanır veya yer değiştirir misiniz?"),
            AsrsQuestion("Hareketsiz kalmanız gereken zamanlarda kendinizi huzursuz hisseder misiniz?"),
            AsrsQuestion("Sizi dinleyen birinin sözünü keser ya da tamamlamaya çalışır mısınız?"),
            AsrsQuestion("İnsanların sözünü keser ya da onların sırasını almaya çalışır mısınız?"),
            AsrsQuestion("Sorular tamamlanmadan cevabını vermeye başlar mısınız?"),
            AsrsQuestion("Rutin işleri sürdürmekte zorlanır mısınız?"),
            AsrsQuestion("Hatırlamanız gereken eşyaları (anahtar, cüzdan, telefon vb.) sık sık kaybeder misiniz?"),
            AsrsQuestion("Size sıkıcı veya tekrarlayıcı gelen işleri yaparken dikkatiniz kolayca dağılır mı?"),
            AsrsQuestion("Aynı anda birden fazla şey yaparken zorlanır mısınız?"),
            AsrsQuestion("Hedeflerinizi sürdürmek için gerekli olan işleri tamamlamakta sıkıntı yaşar mısınız?"),
            AsrsQuestion("Sosyal ortamlarda konuşmaları bölerek dikkat dağıttığınız olur mu?"),
            AsrsQuestion("Dikkatinizi sürdürmenizi gerektiren durumlarda zorlanır mısınız?")
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