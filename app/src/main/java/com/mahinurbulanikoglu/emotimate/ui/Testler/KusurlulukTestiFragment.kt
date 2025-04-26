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
import com.mahinurbulanikoglu.emotimate.model.SchemaTestAdapter
import com.mahinurbulanikoglu.emotimate.model.SchemaQuestion
class KusurlulukTestiFragment : Fragment() {private lateinit var questionList: List<SchemaQuestion>
    private lateinit var adapter: SchemaTestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment için XML layout dosyasını bağla
        return inflater.inflate(R.layout.fragment_kusurluluk_testi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // XML'deki RecyclerView ve Buton referanslarını al
        val recyclerView = view.findViewById<RecyclerView>(R.id.questionsRecyclerView)
        val submitButton = view.findViewById<Button>(R.id.submitTestButton)

        // Kusurluluk / Utanç şeması için tüm sorular
        questionList = listOf(
            SchemaQuestion(1, "Gerçek benliğimi tanısalardı, insanlar beni sevmezdi."),
            SchemaQuestion(2, "Benimle ilgili bir şeylerin derinlemesine yanlış olduğunu hissediyorum."),
            SchemaQuestion(3, "Kendimde utanılacak çok şey olduğunu hissediyorum."),
            SchemaQuestion(4, "Kusurlarımı saklamadığım sürece insanlar beni kabul etmez."),
            SchemaQuestion(5, "Yetersiz, değersiz veya kötü biri olduğumu sık sık hissediyorum."),
            SchemaQuestion(6, "Kendimi diğerlerinden daha aşağı hissederim."),
            SchemaQuestion(7, "İnsanlar bana yaklaşırsa, içimdeki kusurları göreceklerinden korkarım."),
            SchemaQuestion(8, "Çocukken sık sık kusurlu olduğumu hissederdim."),
            SchemaQuestion(9, "Bir ilişki içinde, sonunda gerçek benliğimi görüp beni reddedeceklerinden korkarım."),
            SchemaQuestion(10, "Değersizliğim yüzünden başkalarının sevgisini hak etmediğimi düşünürüm."),
            SchemaQuestion(11, "Hatalarım beni kötü bir insan yapar."),
            SchemaQuestion(12, "İnsanlarla yakın ilişkilerde kendimi yetersiz hissederim.")
        )

        // Adapter ve RecyclerView bağlantılarını kur
        adapter = SchemaTestAdapter(questionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Butona tıklanınca puanları hesapla
        submitButton.setOnClickListener {
            val totalScore = questionList.sumOf { it.selectedScore }

            val message = when {
                totalScore >= 55 -> "Yüksek düzeyde Kusurluluk / Utanç"
                totalScore in 35..54 -> "Orta düzeyde Kusurluluk / Utanç"
                else -> "Düşük düzeyde Kusurluluk / Utanç"
            }

            Toast.makeText(requireContext(), "$message\nToplam Puan: $totalScore", Toast.LENGTH_LONG).show()
        }
    }
}
