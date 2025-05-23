package com.mahinurbulanikoglu.emotimate.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahinurbulanikoglu.emotimate.R
import android.util.Log

class SchemaTestAdapter(
    private val questionList: List<SchemaQuestion>
) : RecyclerView.Adapter<SchemaTestAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText: TextView = itemView.findViewById(R.id.questionText)
        val radioGroup: RadioGroup = itemView.findViewById(R.id.radioGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schema_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questionList[position]

        Log.d("SchemaTestAdapter", "Soru: ${question.questionText}")

        holder.questionText.text = question.questionText

        // RadioGroup önce eski dinleyiciden temizleniyor
        holder.radioGroup.setOnCheckedChangeListener(null)

        // Seçili olanı işaretle
        when (question.selectedScore) {
            1 -> holder.radioGroup.check(R.id.radio1)
            2 -> holder.radioGroup.check(R.id.radio2)
            3 -> holder.radioGroup.check(R.id.radio3)
            4 -> holder.radioGroup.check(R.id.radio4)
            5 -> holder.radioGroup.check(R.id.radio5)
            6 -> holder.radioGroup.check(R.id.radio6)
            else -> holder.radioGroup.clearCheck()
        }

        // Radio seçildiğinde skor kaydediliyor
        holder.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            question.selectedScore = when (checkedId) {
                R.id.radio1 -> 1
                R.id.radio2 -> 2
                R.id.radio3 -> 3
                R.id.radio4 -> 4
                R.id.radio5 -> 5
                R.id.radio6 -> 6
                else -> 0
            }
        }
    }
    override fun getItemCount(): Int {
        Log.d("SchemaTestAdapter", "getItemCount: ${questionList.size}")
        return questionList.size
    }

}
