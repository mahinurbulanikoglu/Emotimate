package com.mahinurbulanikoglu.emotimate.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mahinurbulanikoglu.emotimate.R
import com.mahinurbulanikoglu.emotimate.databinding.FragmentHomeBinding
import com.mahinurbulanikoglu.emotimate.model.ContentItem
import com.mahinurbulanikoglu.emotimate.model.ContentType
import com.mahinurbulanikoglu.emotimate.model.meditationItems

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MeditationViewModel by activityViewModels()

    private var selectedMood: String? = null
    private lateinit var db: FirebaseFirestore

    // RecyclerView ve Adapter için değişkenler
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContentAdapter
    //private lateinit var items: List<ContentItem>

    val meditationItems = listOf(
        ContentItem(
            title = "Yağmur Meditasyonu",
            imageResId = R.drawable.emotimatefoto,
            audioResId = R.raw.rain_meditation_music,
            contentType = ContentType.MEDITATION
        ),
        ContentItem(
            title = "Dalga Sesi Meditasyonu",
            imageResId = R.drawable.emotimatefoto,
            audioResId = R.raw.rain_meditation_music,
            contentType = ContentType.MEDITATION
        )
    )

    private val motivationMap = mapOf(
        "Mükemmel" to listOf(
            "Sen bir yıldızsın, ışığını yay! ✨",
            "Bu enerjinle her şeyin üstesinden gelirsin!",
            "Harika bir ruh halindesin, bunu kutla! 🎉",
            "Senin gibi birini görmek ilham verici!",
            "Bugün her şey mümkün görünüyor 💫"
        ),
        "İyi" to listOf(
            "Harika gidiyorsun! Bu enerjiyi koru 🌟",
            "Bugünün gücünü yarına taşıyabilirsin!",
            "Kendinle gurur duymalısın 👏",
            "Bu anın tadını çıkar!",
            "Mutluluğunu başkalarına da yansıt 💛"
        ),
        "Ortalama" to listOf(
            "Her gün aynı olmayabilir ama sen hep ilerliyorsun.",
            "Kendine zaman tanı, her şey yoluna girecek.",
            "Nefes al, yeniden başlamak için harika bir an.",
            "Bu his geçici, sen kalıcısın.",
            "İyileşmek de bir başarıdır. İlerlemen önemli 💚"
        ),
        "Nötr" to listOf(
            "Kendini zorlamak zorunda değilsin, bu da bir his.",
            "Bugün biraz dinlenmek de iyidir 🛌",
            "Duygular gelip geçer, sen buradasın.",
            "Ruh halin ne olursa olsun, kıymetlisin.",
            "Her şey net değilse bile sen doğru yoldasın."
        ),
        "Kötü" to listOf(
            "Zor zamanlar geçer, sen güçlü kalırsın 💪",
            "Yalnız değilsin, bu hisler geçecek.",
            "Bugün ağlamak bile cesarettir.",
            "Kendine nazik ol, bugünlük bu kadarı yeter 🌧️",
            "Unutma, fırtınadan sonra güneş mutlaka doğar ☀️"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        // LayoutManager ekleme
        recyclerView.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Duygu kartları tıklama işlemleri
        binding.cardPerfect.setOnClickListener { onMoodSelected("Mükemmel", binding.cardPerfect) }
        binding.cardGood.setOnClickListener { onMoodSelected("İyi", binding.cardGood) }
        binding.cardAverage.setOnClickListener { onMoodSelected("Ortalama", binding.cardAverage) }
        binding.cardNeutral.setOnClickListener { onMoodSelected("Nötr", binding.cardNeutral) }
        binding.cardBad.setOnClickListener { onMoodSelected("Kötü", binding.cardBad) }

        // Kaydet butonu
        binding.btnSave.setOnClickListener {
            selectedMood?.let { mood ->
                val comment = binding.editNote.text.toString()
                saveMoodToFirestore(mood, comment)
            }
        }

        // Meditasyon listesi ayarı

        val adapter = ContentAdapter(meditationItems) { selectedItem ->
            if (selectedItem.contentType == ContentType.MEDITATION) {
                viewModel.selectMeditation(selectedItem)

                // SafeArgs ile Navigation geçişi yapıyoruz
                val action = HomeFragmentDirections.actionHomeToMeditationDetail(
                    meditationItem = selectedItem,
                    title = selectedItem.title,
                    audioResId = selectedItem.audioResId ?: -1, // Eğer null ise -1 gönder
                    imageResId = selectedItem.imageResId
                )
                findNavController().navigate(action)
            }
        }
        binding.recyclerView.adapter = adapter

        // RecyclerView'e veri yüklenip yüklenmediğini kontrol etme
        Log.d("HomeFragment", "RecyclerView Items: ${meditationItems.size}")
        adapter.notifyDataSetChanged()
    }






    private fun onMoodSelected(mood: String, selectedCard: CardView) {
        resetCardColors()
        selectedCard.setCardBackgroundColor(Color.parseColor("#BBDEFB"))

        binding.editNote.visibility = View.VISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.tvMotivation.visibility = View.VISIBLE

        selectedMood = mood
        showMotivation(mood)
    }

    private fun showMotivation(mood: String) {
        val messages = motivationMap[mood]
        val randomMessage = messages?.random() ?: "Bugün nasılsın?"
        binding.tvMotivation.text = randomMessage
    }

    private fun resetCardColors() {
        val defaultColor = Color.WHITE
        binding.cardPerfect.setCardBackgroundColor(defaultColor)
        binding.cardGood.setCardBackgroundColor(defaultColor)
        binding.cardAverage.setCardBackgroundColor(defaultColor)
        binding.cardNeutral.setCardBackgroundColor(defaultColor)
        binding.cardBad.setCardBackgroundColor(defaultColor)
    }

    private fun saveMoodToFirestore(mood: String, comment: String?) {
        val data = hashMapOf(
            "mood" to mood,
            "comment" to comment,
            "timestamp" to FieldValue.serverTimestamp()
        )

        db.collection("feelings")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Duygun kaydedildi 🎉", Toast.LENGTH_SHORT).show()
                resetUI()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Kaydedilemedi ❌", Toast.LENGTH_SHORT).show()
            }
    }

    private fun resetUI() {
        resetCardColors()
        binding.editNote.setText("")
        binding.editNote.visibility = View.GONE
        binding.btnSave.visibility = View.GONE
        selectedMood = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
