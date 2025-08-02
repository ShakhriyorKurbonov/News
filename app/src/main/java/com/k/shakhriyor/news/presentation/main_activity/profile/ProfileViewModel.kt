package com.k.shakhriyor.news.presentation.main_activity.profile

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k.shakhriyor.news.domain.model.User
import com.k.shakhriyor.news.domain.repo.PearsonRepository
import com.k.shakhriyor.news.presentation.main_activity.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel

class ProfileViewModel @Inject constructor(
    private val pearsonRepository: PearsonRepository
): ViewModel() {

    val loading= MutableLiveData<Boolean>(false)
    val error= MutableLiveData<Boolean>(false)
    val event= MutableLiveData<HomeViewModel.Event>()
    val liveData= MutableLiveData<User>()
    val modeType=MutableLiveData<Int>()

    fun getPearson(){
        viewModelScope.launch {
            loading.postValue(true)
            error.postValue(false)
            try {
                val response=pearsonRepository.getPearson()
                liveData.postValue(response)
            }catch (e: Exception){
                error.postValue(true)
                when(e){
                    is IOException->{event.postValue(HomeViewModel.Event.ConnectionError)}
                    else -> {event.postValue(HomeViewModel.Event.Error)}
                }
            }finally {
                loading.postValue(false)
            }
        }
    }

    fun getUiModeType():Int{
        return pearsonRepository.getUiModeType()
    }

    fun changeUiModeType(modeId:Int){
        viewModelScope.launch { pearsonRepository.changeUiModeType(modeId) }
    }

}