package com.mahinurbulanikoglu.emotimate.ui.testler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahinurbulanikoglu.emotimate.R
import com.mahinurbulanikoglu.emotimate.model.SchemaCategory
import com.mahinurbulanikoglu.emotimate.model.SchemaCategoryAdapter

class SemaFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SchemaCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sema, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewSchemaCategories)

        val categories = listOf(
            SchemaCategory("Kusurluluk / Utanç"),
            SchemaCategory("Terkedilme"),
            SchemaCategory("Bağımlılık"),
            SchemaCategory("Duygusal Yoksunluk"),
            SchemaCategory("Sosyal İzolasyon / Yabancılık"),
            SchemaCategory("Kendini Feda"),
            SchemaCategory("Boyun Eğicilik"),
            SchemaCategory("Duygusal Bastırma"),
            SchemaCategory("Karamsarlık / Felaketçilik"),
            SchemaCategory("Başarısızlık"),
            SchemaCategory("Haklılık") // yeni test
        )

        adapter = SchemaCategoryAdapter(categories) { category ->
            when (category.title) {
                "Kusurluluk / Utanç" -> {
                    findNavController().navigate(R.id.action_semaFragment_to_kusurlulukTestiFragment)
                }
                "Terkedilme" -> {
                    findNavController().navigate(R.id.action_semaFragment_to_terkedilmeTestiFragment)
                }
                "Bağımlılık" -> {
                    findNavController().navigate(R.id.action_semaFragment_to_bagimlilikTestiFragment)
                }
                "Duygusal Yoksunluk" -> {
                    findNavController().navigate(R.id.action_semaFragment_to_duygusalYoksunlukTestiFragment)
                }
                "Sosyal İzolasyon / Yabancılık" -> {
                    findNavController().navigate(R.id.action_semaFragment_to_sosyalIzolasyonTestiFragment)
                }
                "Kendini Feda" -> {
                    findNavController().navigate(R.id.action_semaFragment_to_kendiniFedaTestiFragment)
                }
                "Boyun Eğicilik" -> {
                    findNavController().navigate(R.id.action_semaFragment_to_boyunEgicilikTestiFragment)
                }
                "Duygusal Bastırma" -> {
                    findNavController().navigate(R.id.action_semaFragment_to_duygusalBastirmaTestiFragment)
                }
                "Karamsarlık / Felaketçilik" -> {
                    findNavController().navigate(R.id.action_semaFragment_to_karamsarlikTestiFragment)
                }
                "Başarısızlık" -> {
                    findNavController().navigate(R.id.action_semaFragment_to_basarisizlikTestiFragment)
                }
                "Haklılık" -> {
                    findNavController().navigate(R.id.action_semaFragment_to_haklilikTestiFragment)
                }
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}