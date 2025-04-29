package com.mahinurbulanikoglu.emotimate.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahinurbulanikoglu.emotimate.databinding.ItemArticleBinding
import com.mahinurbulanikoglu.emotimate.model.Article

class ArticleAdapter(private val onClick: (Article) -> Unit) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var articleList: List<Article> = emptyList()

    fun submitList(list: List<Article>) {
        articleList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]
        holder.bind(article)
        holder.itemView.setOnClickListener { onClick(article) }
    }

    override fun getItemCount(): Int = articleList.size

    class ArticleViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.tvArticleTitle.text = article.title
            binding.tvArticleAuthors.text = article.authors?.joinToString(", ") ?: "Yazar bilgisi yok"
            binding.tvArticleJournal.text = article.journal ?: "Dergi bilgisi yok"
            binding.tvArticleAbstract.text = article.abstract ?: "Özet bilgisi yok"
        }
    }
}