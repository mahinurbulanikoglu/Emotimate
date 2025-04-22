package com.mahinurbulanikoglu.emotimate.ui.home


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.mahinurbulanikoglu.emotimate.R
import com.mahinurbulanikoglu.emotimate.databinding.ItemContentBinding
import com.mahinurbulanikoglu.emotimate.model.ContentItem
import com.mahinurbulanikoglu.emotimate.model.ContentType

class ContentAdapter(
    private val items: List<ContentItem>,
    private val onItemClick: (ContentItem) -> Unit
) : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val binding = ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val item = items[position]

        // Burada tıklama olayını set ediyoruz
        holder.itemView.setOnClickListener {
            onItemClick(item) // Tıklanan öğeyi onItemClick fonksiyonuna gönderiyoruz
        }

        // Diğer binding işlemleri (görünümün yerleştirilmesi vb.)
        holder.bind(item)
    }


    override fun getItemCount(): Int = items.size

    inner class ContentViewHolder(val binding: ItemContentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentItem) {
            binding.textTitle.text = item.title
            binding.imageContent.setImageResource(item.imageResId)

            // Açıklama sadece bazı içerik türlerinde gösterilsin
            binding.textDescription.visibility = if (item.contentType == ContentType.MEDITATION) {
                binding.textDescription.text = "Meditasyon"
                View.VISIBLE
            } else if (item.description != null) {
                binding.textDescription.text = item.description
                View.VISIBLE
            } else {
                View.GONE
            }

            // Tıklanabilir kart
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}
