/*package com.mahinurbulanikoglu.emotimate.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahinurbulanikoglu.emotimate.databinding.ItemMeditationBinding
import com.mahinurbulanikoglu.emotimate.model.ContentItem

class MeditationAdapter(private val items: List<ContentItem>) : RecyclerView.Adapter<MeditationAdapter.MeditationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeditationViewHolder {
        val binding = ItemMeditationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeditationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeditationViewHolder, position: Int) {
        val meditation = items[position]
        holder.bind(meditation)
    }

    override fun getItemCount(): Int = items.size

    inner class MeditationViewHolder(private val binding: ItemMeditationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meditation: ContentItem) {
            binding.tvTitle.text = meditation.title
            binding.tvDescription.text = meditation.description
            // Görseli ekleme
        }
    }
}*/