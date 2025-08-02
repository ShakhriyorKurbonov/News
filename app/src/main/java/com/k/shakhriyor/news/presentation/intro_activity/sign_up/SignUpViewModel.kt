package com.k.shakhriyor.news.presentation.intro_activity.sign_up

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.k.shakhriyor.news.data.store.TokenStore
import com.k.shakhriyor.news.domain.repo.AuthRepository
import com.k.shakhriyor.news.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.IOException


@HiltViewModel

class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenStore: TokenStore
):ViewModel() {

     val loading=MutableLiveData<Boolean>(false)
     val events=SingleLiveEvent<Event>()
     val isLoggedIn=MutableLiveData<Boolean>()

    fun signUp(fullName:String,email:String,password:String){
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val l=authRepository.signUp(email, fullName, password)
                isLoggedIn.postValue(l)
            }catch (e:Exception){
                Log.d("TAG", "signUp: $e")
                when(e){
                   is IOException ->events.postValue(Event.ConnectionError)
                    else->events.postValue(Event.Error)
                }
            }finally {
                loading.postValue(false)
            }
        }
    }

    sealed class Event{
        data object ConnectionError:Event()
        data object Error:Event()

    }

}