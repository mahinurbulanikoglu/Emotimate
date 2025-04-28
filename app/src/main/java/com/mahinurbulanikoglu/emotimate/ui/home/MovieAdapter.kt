package com.mahinurbulanikoglu.emotimate.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahinurbulanikoglu.emotimate.databinding.ItemMovieBinding
import com.mahinurbulanikoglu.emotimate.model.Movie

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.tvTitle.text = movie.title
            //binding.tvDirector.text = movie.director
            binding.tvDescription.text = movie.description
            // Görseli ekleme (imageUrl yerine doğru görseli ekleyebilirsiniz)
            // Picasso veya Glide kullanabilirsiniz
        }
    }
}