package com.k.shakhriyor.news.presentation.main_activity.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.k.shakhriyor.news.databinding.NewsItemInCategoryBinding
import com.k.shakhriyor.news.domain.model.New

class NewsAdapter(private val newsList: List<New>,
                  private val onItemClick:(new: New)-> Unit): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(NewsItemInCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ViewHolder(private val binding: NewsItemInCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(new: New){
            Glide.with(binding.root).load(new.image).into(binding.image)
            binding.titleTv.text=new.title
            binding.authorTv.text=new.author
            binding.postedDate.text=new.postedDate
            binding.root.setOnClickListener {
                onItemClick(new)
            }
        }
    }
}