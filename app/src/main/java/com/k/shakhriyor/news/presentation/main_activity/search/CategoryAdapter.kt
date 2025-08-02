package com.k.shakhriyor.news.presentation.main_activity.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.k.shakhriyor.news.R
import com.k.shakhriyor.news.databinding.CategoryItemBinding
import com.k.shakhriyor.news.domain.model.Category
import com.k.shakhriyor.news.domain.model.New

class CategoryAdapter(private val categoryList:List<String>,
                      private val onCategoryClick:(String)->Unit): Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var context: Context
    var selectedPosition=-1
    private var lastSelectedPosition=-1

    inner class ViewHolder(private val binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(category: String,position: Int){
            binding.categoryItemTv.text=category



            binding.root.setOnClickListener {
                lastSelectedPosition=selectedPosition
                selectedPosition=position
                notifyItemChanged(selectedPosition)
                notifyItemChanged(lastSelectedPosition)
            }



            if (selectedPosition==position){
                onCategoryClick(category)
                binding.root.setBackgroundResource(R.drawable.red_rectangle_16)
                binding.categoryItemTv.setTextColor(context.resources.getColor(R.color.white))
            }else{
                binding.root.setBackgroundResource(R.drawable.gray_rectangle_16)
                binding.categoryItemTv.setTextColor(context.resources.getColor(R.color.font_black))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        return ViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryList[position],position)
    }
}