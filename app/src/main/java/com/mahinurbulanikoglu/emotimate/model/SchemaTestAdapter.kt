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

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        Log.d("Adapter", "Soru: ${question.questionText}")
        Log.d("AdapterKontrol", "Soru: ${question.questionText}")
        Log.d("TestlerFragment", "Soru sayısı: ${questionList.size}")
        holder.questionText.text = question.questionText

        holder.radioGroup.setOnCheckedChangeListener(null)

        when (question.selectedScore) {
            1 -> holder.radioGroup.check(R.id.radio1)
            2 -> holder.radioGroup.check(R.id.radio2)
            3 -> holder.radioGroup.check(R.id.radio3)
            4 -> holder.radioGroup.check(R.id.radio4)
            5 -> holder.radioGroup.check(R.id.radio5)
            6 -> holder.radioGroup.check(R.id.radio6)
            else -> holder.radioGroup.clearCheck()
        }

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

    override fun getItemCount(): Int = questionList.size
}
