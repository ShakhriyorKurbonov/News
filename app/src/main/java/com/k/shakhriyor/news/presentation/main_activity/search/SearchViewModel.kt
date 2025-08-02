package com.k.shakhriyor.news.presentation.main_activity.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k.shakhriyor.news.domain.model.New
import com.k.shakhriyor.news.domain.model.search_options.SearchOption
import com.k.shakhriyor.news.domain.model.search_options.SearchResponse
import com.k.shakhriyor.news.domain.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class SearchViewModel @Inject constructor(
   private val searchRepository: SearchRepository
):ViewModel() {

    val liveSearchResponse= MutableLiveData<SearchResponse>()
    val liveSearchOption= MutableLiveData<SearchOption>()
    val loadSearchOption= MutableLiveData<Boolean>(false)
    val loading= MutableLiveData<Boolean>(false)

    fun searchOption(){
        viewModelScope.launch {
            loadSearchOption.postValue(false)
            try {
                val searchOption=searchRepository.getSearchOption()
                liveSearchOption.postValue(searchOption)
                loadSearchOption.postValue(true)
            }catch (e: Exception){

            }finally {

            }
        }
    }

    fun getSearchNews(search:String){
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val searchResponse=searchRepository.getSearchNews(search)
                liveSearchResponse.postValue(searchResponse)
            }catch (e:Exception){
            }finally {
                loading.postValue(false)
            }
        }
    }
    fun getSearchNewsByFilter(filter:String,search:String){
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val searchResponse=searchRepository.getSearchNewsByFilter(filter, search)
                liveSearchResponse.postValue(searchResponse)
            }catch (e:Exception){
            }finally {
                loading.postValue(false)
            }
        }
    }




    fun getSearchNewsByCategory(category:String,search:String){
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val searchResponse=searchRepository.getSearchNewsByCategory(category, search)
                liveSearchResponse.postValue(searchResponse)
            }catch (e:Exception){
            }finally {
                loading.postValue(false)
            }
        }
    }



}