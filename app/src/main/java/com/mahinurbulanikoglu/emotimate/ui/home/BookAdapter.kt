// BookAdapter.kt
package com.mahinurbulanikoglu.emotimate.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahinurbulanikoglu.emotimate.R
import com.mahinurbulanikoglu.emotimate.model.Book
import com.mahinurbulanikoglu.emotimate.databinding.ItemBookBinding
import com.bumptech.glide.Glide

class BookAdapter : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var bookList: List<Book> = emptyList()

    fun submitList(list: List<Book>) {
        bookList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bind(book)

    }

    override fun getItemCount(): Int = bookList.size

    class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.title.text = book.title
            //binding.authors.text = book.authors.joinToString(", ")
            binding.description.text = book.description
            Glide.with(binding.root.context)
                .load(book.cover.medium)
                .into(binding.coverImage)
        }
    }
}
