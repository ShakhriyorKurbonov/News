package com.k.shakhriyor.news.presentation.main_activity.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.k.shakhriyor.news.databinding.ItemBannerBinding
import com.k.shakhriyor.news.domain.model.LastedNew

class BannerAdapter(private val bannerList:List<LastedNew>,
                    private val onItemClick:(lastedNew: LastedNew)->Unit
):Adapter<BannerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding:ItemBannerBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(lastedNew: LastedNew){
            Glide.with(binding.root.context).load(lastedNew.image).into(binding.image)
            binding.authorTv.text=lastedNew.author
            binding.titleTv.text=lastedNew.title
            binding.descriptionTv.text=lastedNew.description.take(200)+"..."
            binding.root.setOnClickListener {
                onItemClick(lastedNew)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

}