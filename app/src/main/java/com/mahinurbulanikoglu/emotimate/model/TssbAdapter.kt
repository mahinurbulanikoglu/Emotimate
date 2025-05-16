package com.mahinurbulanikoglu.emotimate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahinurbulanikoglu.emotimate.databinding.ItemTssbQuestionBinding
import com.mahinurbulanikoglu.emotimate.model.TssbQuestion

class TssbAdapter(
    private val questions: List<TssbQuestion>,
    private val onAnswerSelected: (Int, Int) -> Unit
) : RecyclerView.Adapter<TssbAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(val binding: ItemTssbQuestionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemTssbQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val currentQuestion = questions[position]
        holder.binding.questionText.text = currentQuestion.questionText

        // Önceki seçimi temizle
        holder.binding.radioGroup.setOnCheckedChangeListener(null)

        // Mevcut cevabı göster
        when (currentQuestion.answer) {
            0 -> holder.binding.radio0.isChecked = true
            1 -> holder.binding.radio1.isChecked = true
            2 -> holder.binding.radio2.isChecked = true
            3 -> holder.binding.radio3.isChecked = true
            4 -> holder.binding.radio4.isChecked = true
            else -> holder.binding.radioGroup.clearCheck()
        }

        // Yeni cevap seçimini dinle
        holder.binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val answer = when (checkedId) {
                holder.binding.radio0.id -> 0
                holder.binding.radio1.id -> 1
                holder.binding.radio2.id -> 2
                holder.binding.radio3.id -> 3
                holder.binding.radio4.id -> 4
                else -> -1
            }
            onAnswerSelected(position, answer)
        }
    }

    override fun getItemCount(): Int = questions.size
}