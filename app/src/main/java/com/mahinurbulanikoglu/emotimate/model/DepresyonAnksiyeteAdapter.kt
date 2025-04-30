package com.mahinurbulanikoglu.emotimate.ui.Testler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahinurbulanikoglu.emotimate.R

class DepresyonAnksiyeteAdapter(
    private val tests: List<DepresyonAnksiyeteTest>,
    private val onItemClick: (DepresyonAnksiyeteTest) -> Unit
) : RecyclerView.Adapter<DepresyonAnksiyeteAdapter.TestViewHolder>() {

    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.textViewCategory)
        init {
            itemView.setOnClickListener {
                onItemClick(tests[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_row, parent, false)
        return TestViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.titleText.text = tests[position].title
    }

    override fun getItemCount(): Int = tests.size
}