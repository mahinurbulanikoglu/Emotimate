package com.mahinurbulanikoglu.emotimate.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.mahinurbulanikoglu.emotimate.model.relaxingMusicItems
import com.mahinurbulanikoglu.emotimate.model.breathingItems
import com.mahinurbulanikoglu.emotimate.model.Article
import com.mahinurbulanikoglu.emotimate.model.Book
import com.mahinurbulanikoglu.emotimate.model.ContentItem
import com.mahinurbulanikoglu.emotimate.model.meditationItems
import com.mahinurbulanikoglu.emotimate.network.RetrofitInstance
import com.mahinurbulanikoglu.emotimate.ui.home.adapter.ArticleAdapter
import com.mahinurbulanikoglu.emotimate.ui.home.adapter.BookAdapter
import com.mahinurbulanikoglu.emotimate.ui.home.adapter.MovieAdapter
import com.mahinurbulanikoglu.emotimate.ui.home.adapter.MeditationAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var db: FirebaseFirestore

    private lateinit var bookAdapter: BookAdapter
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var articleAdapter: ArticleAdapter

    private var selectedMood: String? = null

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
    private fun setupRecyclerView() {
        // Book Adapter
        bookAdapter = BookAdapter { selectedBook ->
            val action = HomeFragmentDirections.actionHomeToBookDetail(selectedBook)
            findNavController().navigate(action)
        }
        binding.recyclerViewBooks.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = bookAdapter
        }

        // Movie Adapter
        movieAdapter = MovieAdapter { selectedMovie ->
            val action = HomeFragmentDirections.actionHomeToMovieDetail(selectedMovie)
            findNavController().navigate(action)
        }
        binding.recyclerViewMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = movieAdapter
        }

        // Article Adapter
        articleAdapter = ArticleAdapter { selectedArticle ->
            val action = HomeFragmentDirections.actionHomeToArticleDetail(selectedArticle)
            findNavController().navigate(action)
        }
        binding.recyclerViewArticles.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = articleAdapter
        }

        // Meditations
        binding.recyclerViewMeditations.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = MeditationAdapter(meditationItems) { selectedItem ->
                navigateToDetail(selectedItem)
            }
        }

        // Breathing Exercises
        binding.recyclerViewBreathing.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = MeditationAdapter(breathingItems) { selectedItem ->
                navigateToDetail(selectedItem)
            }
        }

        // Relaxing Music
        binding.recyclerViewRelaxingMusic.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = MeditationAdapter(relaxingMusicItems) { selectedItem ->
                navigateToDetail(selectedItem)
            }
        }
    }

    private fun observeBooks() {
        homeViewModel.books.observe(viewLifecycleOwner) { books ->
            books?.let {
                bookAdapter.submitList(it)
            }
        }
    }
    private fun observeMovies() {
        homeViewModel.movies.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                movieAdapter.submitList(it)
            }
        }
    }
    private fun observeArticles() {
        homeViewModel.articles.observe(viewLifecycleOwner) { articles ->
            articles?.let {
                articleAdapter.submitList(it)
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMoodCards()
        setupRecyclerView()
        observeBooks()
        observeMovies()
        observeArticles()
        homeViewModel.fetchBooks()
        homeViewModel.fetchMovies()
        homeViewModel.fetchArticles()

        binding.btnSave.setOnClickListener {
            selectedMood?.let { mood ->
                val comment = binding.editNote.text.toString()
                saveMoodToFirestore(mood, comment)
            }
        }
        // Start a Conversation butonuna tıklanınca AI sekmesine geç
        binding.btnStartConversation.setOnClickListener {
            val navView = requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.nav_view)
            navView.selectedItemId = R.id.navigation_ai
        }
    }

    private fun setupMoodCards() {
        binding.cardPerfect.setOnClickListener { onMoodSelected("Mükemmel", binding.cardPerfect) }
        binding.cardGood.setOnClickListener { onMoodSelected("İyi", binding.cardGood) }
        binding.cardAverage.setOnClickListener { onMoodSelected("Ortalama", binding.cardAverage) }
        binding.cardNeutral.setOnClickListener { onMoodSelected("Nötr", binding.cardNeutral) }
        binding.cardBad.setOnClickListener { onMoodSelected("Kötü", binding.cardBad) }
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

    private fun navigateToDetail(selectedItem: ContentItem) {
        try {
            val action = HomeFragmentDirections.actionHomeToMeditationDetail(
                meditationItem = selectedItem,
                title = selectedItem.title,
                audioResId = selectedItem.audioResId ?: -1,
                imageResId = selectedItem.imageResId
            )
            findNavController().navigate(action)
        } catch (e: Exception) {
            Log.e("HomeFragment", "Navigation hatası: ${e.message}")
            Toast.makeText(requireContext(), "Bir hata oluştu", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
