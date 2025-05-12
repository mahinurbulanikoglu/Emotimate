package com.mahinurbulanikoglu.emotimate.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahinurbulanikoglu.emotimate.databinding.ItemMeditationBinding
import com.mahinurbulanikoglu.emotimate.model.ContentItem
import com.mahinurbulanikoglu.emotimate.model.ContentType

class MeditationAdapter(
    private val items: List<ContentItem>,
    private val onItemClick: (ContentItem) -> Unit
) : RecyclerView.Adapter<MeditationAdapter.MeditationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeditationViewHolder {
        val binding = ItemMeditationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeditationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeditationViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = items.size

    inner class MeditationViewHolder(private val binding: ItemMeditationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentItem) {
            binding.textTitle.text = item.title
            binding.imageContent.setImageResource(item.imageResId)
            binding.textDescription.text = when(item.contentType) {
                ContentType.MEDITATION -> "Meditasyon"
                ContentType.BREATHING -> "Nefes Egzersizi"
                ContentType.RELAXING_MUSIC -> "Rahatlatıcı Müzik"
                else -> ""
            }
        }
    }
}