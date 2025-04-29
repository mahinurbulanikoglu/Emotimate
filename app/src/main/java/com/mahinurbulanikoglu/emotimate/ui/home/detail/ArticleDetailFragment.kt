package com.mahinurbulanikoglu.emotimate.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mahinurbulanikoglu.emotimate.databinding.FragmentArticleDetailBinding

class ArticleDetailFragment : Fragment() {
    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ArticleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.article

        binding.tvArticleTitle.text = article.title
        binding.tvArticleAuthors.text = article.authors?.joinToString(", ") ?: "Yazar bilgisi yok"
        binding.tvArticleJournal.text = article.journal ?: "Dergi bilgisi yok"
        binding.tvArticleAbstract.text = article.abstract ?: "Özet bilgisi yok"
        binding.tvArticleDate.text = article.publicationDate ?: "Tarih bilgisi yok"
        binding.tvArticleDoi.text = article.doi ?: "DOI bilgisi yok"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}