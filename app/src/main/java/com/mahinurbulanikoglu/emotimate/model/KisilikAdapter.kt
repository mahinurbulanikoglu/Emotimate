 package com.mahinurbulanikoglu.emotimate.adapter

    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.recyclerview.widget.RecyclerView
    import com.mahinurbulanikoglu.emotimate.databinding.ItemKisilikQuestionBinding
    import com.mahinurbulanikoglu.emotimate.model.KisilikQuestion

    class KisilikAdapter(
        private val questions: List<KisilikQuestion>,
        private val onAnswerSelected: (Int, Int) -> Unit
    ) : RecyclerView.Adapter<KisilikAdapter.KisilikViewHolder>() {

        inner class KisilikViewHolder(val binding: ItemKisilikQuestionBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KisilikViewHolder {
            val binding = ItemKisilikQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return KisilikViewHolder(binding)
        }

        override fun onBindViewHolder(holder: KisilikViewHolder, position: Int) {
            val question = questions[position]
            holder.binding.questionText.text = question.question

            holder.binding.radioGroup.setOnCheckedChangeListener(null)
            holder.binding.radioGroup.clearCheck()
            when (question.answer) {
                1 -> holder.binding.radio1.isChecked = true
                2 -> holder.binding.radio2.isChecked = true
                3 -> holder.binding.radio3.isChecked = true
                4 -> holder.binding.radio4.isChecked = true
                5 -> holder.binding.radio5.isChecked = true
            }

            holder.binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
                val answer = when (checkedId) {
                    holder.binding.radio1.id -> 1
                    holder.binding.radio2.id -> 2
                    holder.binding.radio3.id -> 3
                    holder.binding.radio4.id -> 4
                    holder.binding.radio5.id -> 5
                    else -> -1
                }
                if (answer != -1) onAnswerSelected(position, answer)
            }
        }

        override fun getItemCount() = questions.size
    }
