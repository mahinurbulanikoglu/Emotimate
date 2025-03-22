package com.mahinurbulanikoglu.emotimate.ui.Profil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahinurbulanikoglu.emotimate.databinding.FragmentProfilePageBinding

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilePageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profilViewModel =
            ViewModelProvider(this).get(ProfilViewModel::class.java)

        _binding = FragmentProfilePageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.profileTitle
        profilViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}