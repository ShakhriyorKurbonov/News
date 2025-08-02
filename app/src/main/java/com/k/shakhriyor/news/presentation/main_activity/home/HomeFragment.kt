package com.k.shakhriyor.news.presentation.main_activity.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.k.shakhriyor.news.R
import com.k.shakhriyor.news.data.common.Constants
import com.k.shakhriyor.news.databinding.FragmentHomeBinding
import com.k.shakhriyor.news.domain.model.LastedNew
import com.k.shakhriyor.news.presentation.main_activity.home.adapters.BannerAdapter
import com.k.shakhriyor.news.presentation.main_activity.home.adapters.CategoryAdapter
import com.k.shakhriyor.news.presentation.main_activity.home.adapters.NewsAdapter
import com.k.shakhriyor.news.util.hideViewBottomAnim
import com.k.shakhriyor.news.util.hideViewTop
import com.k.shakhriyor.news.util.makeStatusBarTransparent
import com.k.shakhriyor.news.util.showViewBottomAnim
import com.k.shakhriyor.news.util.showViewTop
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private var lastedNewsList=mutableListOf<LastedNew>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        subscribeToLiveData()
        makeStatusBarTransparent()
    }

    private fun subscribeToLiveData() {
        viewModel.homePageNews.observe(viewLifecycleOwner){ home ->
            home?:return@observe
            if (home.lastedNews.isNotEmpty()){
                lastedNewsList.addAll(home.lastedNews)
                val handler = Handler(Looper.getMainLooper())
                var currentPage = 0

                val runnable = object : Runnable {
                    override fun run() {
                        if (currentPage == home.lastedNews.size) currentPage = 0
                        binding.banners.setCurrentItem(currentPage++, true)
                        handler.postDelayed(this, 5000) // har 5 sekundda o'zgaradi
                    }
                }

                handler.post(runnable)


                binding.banners.adapter=BannerAdapter(home.lastedNews){itemClick->
                    val bundle= Bundle().apply {
                        putParcelable(Constants.GET_NEWS,itemClick)
                    }
                    findNavController().navigate(R.id.to_newsItemFragment,bundle)
                }
                binding.banners.isVisible=true
                binding.lastedNewsTv.isVisible=true
                binding.seeAllLayout.isVisible=true
            }else{
                binding.banners.isVisible=false
                binding.lastedNewsTv.isVisible=false
                binding.seeAllLayout.isVisible=false
            }

            binding.categories.adapter=CategoryAdapter(home.categories){news->
                binding.news.adapter=NewsAdapter(news){itemClick->
                    val bundle= Bundle().apply {
                        putParcelable(Constants.GET_NEWS,
                            LastedNew(itemClick.author,itemClick.description,
                                itemClick.image,itemClick.like,
                                itemClick.postedDate,itemClick.title))
                    }
                    findNavController().navigate(R.id.to_newsItemFragment,bundle)
                }
            }
        }
        viewModel.loading.observe(viewLifecycleOwner){
            if (binding.refreshLayout.isRefreshing){
                binding.refreshLayout.isRefreshing=it
                binding.searchContainer.isVisible=!it
            }else{
                binding.loading.root.isVisible=it
                binding.root.isVisible=!it
            }
        }
        viewModel.error.observe(viewLifecycleOwner){
            binding.error.root.isVisible=it
            binding.root.isVisible=!it
        }
        viewModel.events.observe(viewLifecycleOwner){
            when(it){
                HomeViewModel.Event.ConnectionError -> binding.error.errorText.text=getString(R.string.connection_error)
                HomeViewModel.Event.Error -> binding.error.errorText.text=getString(R.string.error)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun initUI()= with(binding){
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getHomePageNews()
        }
        binding.refreshLayout.setColorSchemeResources(R.color.red)

        val bottomNav=activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        var n=1
        homeScroll.setOnScrollChangeListener { _,_,scrollY,_,oldScrollY->

            if(scrollY==0){
                showViewBottomAnim(requireContext(),bottomNav)
                showViewTop(requireContext(),binding.searchContainer)
            } else if (scrollY<oldScrollY){
                if (n!=1){
                    showViewBottomAnim(requireContext(),bottomNav)
                    hideViewTop(requireContext(),binding.searchContainer)
                    n=1
                }
            }else if((scrollY>oldScrollY)){
                if (n==1){
                    hideViewBottomAnim(requireContext(),bottomNav)
                    showViewTop(requireContext(),binding.searchContainer)
                    n=0
                }
            }
        }


        error.retry.setOnClickListener {
            viewModel.getHomePageNews()
        }

        binding.seeAllLayout.setOnClickListener {
            val bundle= Bundle().apply {
                putParcelableArrayList(Constants.LASTED_NEWS, ArrayList(lastedNewsList))
                putString(Constants.NEWS_TYPE,"Hot Updates")
            }
            findNavController().navigate(R.id.to_newsListFragment,bundle)
        }



        banners.offscreenPageLimit = 3
        banners.clipToPadding = false
        banners.clipChildren = false

        banners.setPageTransformer { page, position ->
            page.translationX = -position * 50 // kartochkalar biroz ustma-ust
            page.scaleY = 1 - (0.3f * abs(position)) // kichikroq bo'ladi
            page.scaleX = 1 - (0.3f * abs(position)) // kichikroq bo'ladi
            page.alpha = 0.25f + (1 - abs(position)) // biroz fade effekti
        }


        searchEt.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus.not()) return@setOnFocusChangeListener
            findNavController().navigate(HomeFragmentDirections.toSearchFragment())
        }


    }


}