package com.mahinurbulanikoglu.emotimate.ui.Testler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mahinurbulanikoglu.emotimate.R

class TestlerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_testler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnSchemaTests).setOnClickListener {
            findNavController().navigate(R.id.action_testlerFragment_to_semaFragment)
        }
    }
}