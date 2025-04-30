package com.mahinurbulanikoglu.emotimate.ui.Testler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahinurbulanikoglu.emotimate.R

data class DepresyonAnksiyeteTest(val title: String)

class DepresyonAnksiyeteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DepresyonAnksiyeteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_depresyon_anksiyete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewDepresyonAnksiyete)

        val tests = listOf(
            DepresyonAnksiyeteTest("Beck Depresyon Ölçeği (BDÖ)"),
            DepresyonAnksiyeteTest("Beck Anksiyete Ölçeği (BAÖ)"),

        )

        adapter = DepresyonAnksiyeteAdapter(tests) { test ->
            when (test.title) {
                "Beck Depresyon Ölçeği (BDÖ)" -> {
                    findNavController().navigate(R.id.action_depresyonAnksiyeteFragment_to_beckDepresyonOlcegiFragment)
                }
                "Beck Anksiyete Ölçeği (BAÖ)" -> {
                    findNavController().navigate(R.id.action_depresyonAnksiyeteFragment_to_beckAnksiyeteOlcegiFragment)
                }
                // Diğer testler için ileride buraya ekleme yapabilirsin
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}