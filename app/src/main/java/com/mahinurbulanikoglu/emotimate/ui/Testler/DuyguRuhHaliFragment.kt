package com.mahinurbulanikoglu.emotimate.ui.Testler
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mahinurbulanikoglu.emotimate.R

class DuyguRuhHaliFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_duygu_ruh_hali, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnPanas).setOnClickListener {
            findNavController().navigate(R.id.action_duyguRuhHaliFragment_to_panasFragment)
        }
        view.findViewById<Button>(R.id.btnPoms).setOnClickListener {
            findNavController().navigate(R.id.action_duyguRuhHaliFragment_to_pomsFragment)
        }
    }
}