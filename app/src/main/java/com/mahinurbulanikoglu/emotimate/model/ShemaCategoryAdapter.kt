package com.mahinurbulanikoglu.emotimate.model

class ShemaCategoryAdapter {
    class SchemaCategoryAdapter(
        private val categoryList: List<SchemaCategory>,
        private val onItemClick: (SchemaCategory) -> Unit
    ) : RecyclerView.Adapter<SchemaCategoryAdapter.CategoryViewHolder>() {

        inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val categoryText: TextView = itemView.findViewById(R.id.textViewCategory)

            fun bind(category: SchemaCategory) {
                categoryText.text = category.title
                itemView.setOnClickListener {
                    onItemClick(category)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_row, parent, false)
            return CategoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            holder.bind(categoryList[position])
        }

        override fun getItemCount(): Int = categoryList.size
    }

}