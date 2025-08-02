package com.k.shakhriyor.news.presentation.main_activity.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.k.shakhriyor.news.R
import com.k.shakhriyor.news.databinding.FragmentFavoriteBinding
import com.k.shakhriyor.news.domain.model.LastedNew
import com.k.shakhriyor.news.presentation.main_activity.news_list.NewsListAdapter
import com.k.shakhriyor.news.util.hideViewBottomAnim
import com.k.shakhriyor.news.util.showViewBottomAnim
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment: Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavoriteBinding.inflate(layoutInflater)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        subscribeToLiveData()
        viewModel.getFavorite()

    }

    private fun subscribeToLiveData() {
        viewModel.liveData.observe (viewLifecycleOwner){
            setAdapter(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            binding.error.root.isVisible=it
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (binding.refreshLayout.isRefreshing){
                binding.refreshLayout.isRefreshing=it
            }else{
                binding.loading.root.isVisible=it
            }
            if (it){
                setAdapter(emptyList())
            }
        }

        viewModel.events.observe(viewLifecycleOwner) {
            when(it){
                FavoriteViewModel.Event.ConnectionError -> binding.error.errorText.text=getString(R.string.connection_error)
                FavoriteViewModel.Event.Error -> binding.error.errorText.text=getString(R.string.error)
            }
        }
    }

    private fun initUI() {
        val bottomNav=activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        var n=1
        binding.newsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // Foydalanuvchi pastga scroll qilyapti
                    if (n==1){
                        hideViewBottomAnim(requireContext(),bottomNav)
                        n=0
                    }
                } else if (dy < 0) {
                    // Foydalanuvchi tepaga scroll qilyapti
                    if (n!=1){
                        showViewBottomAnim(requireContext(),bottomNav)
                        n=1
                    }
                }
            }

        })
        binding.error.retry.setOnClickListener {
            viewModel.getFavorite()
        }
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getFavorite()
        }
        binding.refreshLayout.setColorSchemeResources(R.color.red)
    }

    private fun setAdapter(news: List<LastedNew>){
        binding.newsList.adapter= NewsListAdapter(news)
    }
}