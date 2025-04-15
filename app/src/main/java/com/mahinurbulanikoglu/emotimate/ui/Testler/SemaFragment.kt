package com.mahinurbulanikoglu.emotimate.ui.Testler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.mahinurbulanikoglu.emotimate.R
import com.mahinurbulanikoglu.emotimate.model.ShemaCategoryAdapter

class SemaFragment : Fragment() {

    private lateinit var recyclerView: Recycler
    private lateinit var adapter: ShemaCategoryAdapter.SchemaCategoryAdapter

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
            SchemaCategory("Kusurluluk / Utanç")
            // Diğer testleri buraya eklersin
        )

        adapter = SchemaCategoryAdapter(categories) { category ->
            when (category.title) {
                "Kusurluluk / Utanç" -> {
                    findNavController().navigate(R.id.action_semaFragment_to_kusurlulukTestiFragment)
                }
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}
