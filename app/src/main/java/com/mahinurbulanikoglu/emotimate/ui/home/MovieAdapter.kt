package com.mahinurbulanikoglu.emotimate.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahinurbulanikoglu.emotimate.databinding.ItemMovieBinding
import com.mahinurbulanikoglu.emotimate.model.Movie

class MovieAdapter(private val onClick: (Movie) -> Unit) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener { onClick(movie) }
    }

    class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.tvMovieTitle.text = movie.title
            binding.tvMovieDescription.text = movie.overview
            binding.tvMovieRating.text = "Rating: ${movie.voteAverage}/10"

            // Load movie poster using Glide
            movie.posterPath?.let { posterPath ->
                val imageUrl = "https://image.tmdb.org/t/p/w500$posterPath"
                Glide.with(binding.root.context)
                    .load(imageUrl)
                    .into(binding.ivMoviePoster)
            }
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}