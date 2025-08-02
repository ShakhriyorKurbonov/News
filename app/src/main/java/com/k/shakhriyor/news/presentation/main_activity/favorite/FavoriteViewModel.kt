package com.k.shakhriyor.news.presentation.main_activity.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k.shakhriyor.news.domain.model.LastedNew
import com.k.shakhriyor.news.domain.model.New
import com.k.shakhriyor.news.domain.repo.SearchRepository
import com.k.shakhriyor.news.presentation.main_activity.home.HomeViewModel
import com.k.shakhriyor.news.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {

    val loading= MutableLiveData<Boolean>(false)
    val liveData= MutableLiveData<List<LastedNew>>()
    val events= SingleLiveEvent<Event>()
    val error= MutableLiveData<Boolean>(false)

    fun getFavorite(){
        viewModelScope.launch {
            error.postValue(false)
            loading.postValue(true)
            try {
                val news=searchRepository.getFavorite()
                liveData.postValue(news)
            }catch (e: Exception){
                error.postValue(true)
                when (e){
                    is IOException->events.postValue(Event.ConnectionError)
                    else->events.postValue(Event.Error)
                }
            }finally {
                loading.postValue(false)
            }
        }
    }

    sealed class Event{
        object ConnectionError: Event()
        object Error: Event()
    }

}