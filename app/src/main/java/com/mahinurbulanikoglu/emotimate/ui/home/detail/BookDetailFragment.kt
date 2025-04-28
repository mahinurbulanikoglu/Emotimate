package com.mahinurbulanikoglu.emotimate.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mahinurbulanikoglu.emotimate.databinding.FragmentBookDetailBinding

class BookDetailFragment : Fragment() {
    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!
    private val args: BookDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val book = args.book
        binding.tvBookTitle.text = book.title ?: ""
        binding.tvBookAuthor.text = book.authors?.joinToString { it.name ?: "" } ?: ""
        binding.tvBookDescription.text = book.description ?: "Açıklama yok."
        book.cover?.medium?.let { url ->
            Glide.with(requireContext()).load(url).into(binding.ivBookCover)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
