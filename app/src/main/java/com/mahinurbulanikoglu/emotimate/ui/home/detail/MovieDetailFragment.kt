package com.mahinurbulanikoglu.emotimate.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mahinurbulanikoglu.emotimate.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movie
        binding.tvMovieTitle.text = movie.title
        binding.tvMovieOverview.text = movie.overview
        binding.tvMovieRelease.text = "Yayın Tarihi: ${movie.releaseDate}"
        binding.tvMovieRating.text = "IMDB: ${movie.voteAverage}/10"
        movie.posterPath?.let { path ->
            val url = "https://image.tmdb.org/t/p/w500$path"
            Glide.with(requireContext()).load(url).into(binding.ivMoviePoster)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}