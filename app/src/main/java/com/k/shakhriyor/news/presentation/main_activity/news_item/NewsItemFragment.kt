package com.k.shakhriyor.news.presentation.main_activity.news_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.k.shakhriyor.news.R
import com.k.shakhriyor.news.data.common.Constants
import com.k.shakhriyor.news.databinding.FragmentNewsItemBinding
import com.k.shakhriyor.news.domain.model.LastedNew

class NewsItemFragment: Fragment() {
    private lateinit var binding: FragmentNewsItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNewsItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val bottomNav=activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.visibility= View.GONE
        bottomNav?.animation=null
        val news=arguments?.getParcelable<LastedNew>(Constants.GET_NEWS)
        Glide.with(this).load(news?.image).into(binding.newsImg)
        binding.postedDateTv.text=news?.postedDate
        binding.newsTitleTv.text=news?.title
        binding.news.text=news?.description
        binding.authorTv.text=news?.author
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}