package com.k.shakhriyor.news.presentation.main_activity.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.k.shakhriyor.news.databinding.NewsItemInCategoryBinding
import com.k.shakhriyor.news.domain.model.New

class NewsAdapter(private val newsList:List<New>,
    private val onItemCLick:(new:New)->Unit):Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: NewsItemInCategoryBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(new: New){
            Glide.with(binding.root.context).load(new.image).into(binding.image)
            binding.titleTv.text=new.title
            binding.postedDate.text=new.postedDate
            binding.authorTv.text=new.author
            binding.root.setOnClickListener {
                onItemCLick(new)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NewsItemInCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

}