package com.k.shakhriyor.news.presentation.main_activity.news_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.k.shakhriyor.news.R
import com.k.shakhriyor.news.data.common.Constants
import com.k.shakhriyor.news.databinding.FragmentNewsListBinding
import com.k.shakhriyor.news.domain.model.LastedNew

class NewsListFragment: Fragment() {
    private lateinit var binding: FragmentNewsListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNewsListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        val bottomNav=activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.visibility= View.GONE
        bottomNav?.animation=null
        val news=arguments?.getParcelableArrayList<LastedNew>(Constants.LASTED_NEWS)
        val newsType=arguments?.getString(Constants.NEWS_TYPE)
        binding.newsType.text=newsType
        binding.newsList.adapter= NewsListAdapter(news?:emptyList())
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}