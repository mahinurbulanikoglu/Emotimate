package com.mahinurbulanikoglu.emotimate.ui.Testler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mahinurbulanikoglu.emotimate.R

class TestlerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_testler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Şema Testleri butonuna tıklama işlevi
        view.findViewById<Button>(R.id.btnSchemaTests).setOnClickListener {
            findNavController().navigate(R.id.action_navigation_testler_to_semaFragment)
        }
        // Depresyon ve Anksiyete Ölçekleri butonuna tıklama işlevi
        view.findViewById<Button>(R.id.btnDepresyonAnksiyete).setOnClickListener {
            findNavController().navigate(R.id.action_navigation_testler_to_depresyonAnksiyeteFragment)
        }
        view.findViewById<Button>(R.id.btnDuyguRuhHali).setOnClickListener {
            findNavController().navigate(R.id.action_navigation_testler_to_duyguRuhHaliFragment)
        }
        view.findViewById<Button>(R.id.btnDikkatDurtusellik).setOnClickListener {
            findNavController().navigate(R.id.action_navigation_testler_to_dikkatDurtusellikFragment)
        }
        view.findViewById<Button>(R.id.btnTravma).setOnClickListener {
            findNavController().navigate(R.id.action_navigation_testler_to_travmaFragment)
        }
    }
}