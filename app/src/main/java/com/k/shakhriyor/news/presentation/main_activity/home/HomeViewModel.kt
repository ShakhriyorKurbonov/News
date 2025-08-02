package com.k.shakhriyor.news.presentation.main_activity.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k.shakhriyor.news.domain.model.Home
import com.k.shakhriyor.news.domain.repo.HomeRepository
import com.k.shakhriyor.news.util.SingleLiveEvent
import com.k.shakhriyor.news.util.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
private val homeRepository: HomeRepository
):ViewModel() {

    val loading=MutableLiveData<Boolean>(false)
    val events=SingleLiveEvent<Event>()
    val error=MutableLiveData<Boolean>(false)
    val homePageNews=MutableLiveData<Home?>()

    init {
        getHomePageNews()
    }


    fun getHomePageNews(){
        viewModelScope.launch {
            loading.postValue(true)
            error.postValue(false)
            try {
               val news= homeRepository.getHomeNews()
                homePageNews.postValue(news)
            }catch (e:Exception){
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
        object ConnectionError:Event()
        object Error:Event()
    }


}