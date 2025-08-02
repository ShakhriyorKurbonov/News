package com.k.shakhriyor.news.presentation.main_activity.news_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.k.shakhriyor.news.databinding.NewsListItemBinding
import com.k.shakhriyor.news.domain.model.LastedNew

class NewsListAdapter(private val newsList: List<LastedNew>): RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(NewsListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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

    inner class ViewHolder(private val binding: NewsListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: LastedNew){
            Glide.with(binding.root).load(item.image).into(binding.newsImg)
            binding.titleTv.text=item.title
            binding.authorTv.text=item.author
            binding.descriptionTv.text=item.description
            binding.postedDateTv.text=item.postedDate
        }
    }
}