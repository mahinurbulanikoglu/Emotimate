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

class TerkedilmeTestiFragment : Fragment() {

    private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: SchemaTestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_terkedilme_testi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView ve Submit Button referanslarını al
        val recyclerView = view.findViewById<RecyclerView>(R.id.questionsRecyclerView)
        val submitButton = view.findViewById<Button>(R.id.submitTestButton)

        // Terk edilme / İstikrarsızlık şeması için soruları tanımla
        questionList = listOf(
            SchemaQuestion(1, "Sevdiğim kişilerin beni terk edeceğinden korkarım."),
            SchemaQuestion(2, "Partnerimden veya çok yakın olduğum birinden ayrıldığımda kendimi aşırı derecede kaybolmuş ve boşlukta hissederim."),
            SchemaQuestion(3, "Sevdiğim insanların bir gün ortadan kaybolacağına, hastalanacağına, beni terk edeceğine veya öleceğine inanırım."),
            SchemaQuestion(4, "İnsanlarla bağ kurduğumda, o bağı kaybetme korkusu yüzünden ilişkiyi sabote edebilirim."),
            SchemaQuestion(5, "Biriyle çok yakınlaştığımda, aramızda mutlaka bir şeylerin ters gideceğinden korkarım."),
            SchemaQuestion(6, "İlişkilerimde sürekli, partnerimin benden daha iyi birini bulup gideceğinden endişe duyarım."),
            SchemaQuestion(7, "Sevdiğim kişilerin değişken veya güvenilmez olduğunu düşünürüm; bir gün var, bir gün yoklardır."),
            SchemaQuestion(8, "Hayatımdaki insanlara tamamen güvenemem, çünkü eninde sonunda yalnız kalırım."),
            SchemaQuestion(9, "Kendimi sık sık çaresiz, yalnız ve korunmasız hissederim."),
            SchemaQuestion(10, "İlişkilerimde, diğer kişinin her zaman yanımda olmasını sağlamak için çok fazla fedakârlık yaparım.")
        )

        // Adapter ve RecyclerView bağlantısını yap
        adapter = SchemaTestAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Submit butonuna tıklandığında puanları hesapla
        submitButton.setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }

            // Puan sonuçlarını göstermek için uygun mesajları göster
            val message = when {
                totalScore >= 40 -> "Yüksek düzeyde Terk Edilme / İstikrarsızlık Şeması"
                totalScore in 25..39 -> "Orta düzeyde Terk Edilme / İstikrarsızlık Şeması"
                else -> "Düşük düzeyde Terk Edilme / İstikrarsızlık Şeması"
            }

            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}
