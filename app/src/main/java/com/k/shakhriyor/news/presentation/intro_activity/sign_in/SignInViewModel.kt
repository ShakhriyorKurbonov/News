package com.k.shakhriyor.news.presentation.intro_activity.sign_in

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.k.shakhriyor.news.data.store.TokenStore
import dagger.hilt.android.lifecycle.HiltViewModel
import com.k.shakhriyor.news.domain.model.AuthResponse
import com.k.shakhriyor.news.domain.model.User
import com.k.shakhriyor.news.domain.repo.AuthRepository
import com.k.shakhriyor.news.util.SingleLiveEvent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenStore: TokenStore
) : ViewModel(){

    private val _loading=MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> get() = _loading
    val events=SingleLiveEvent<Event>()
    val isLoggedIn=MutableLiveData<Boolean>()

    fun signIn(email:String,password:String){
        _loading.postValue(true)

        viewModelScope.launch {
            try {
               val fcmToken= FirebaseMessaging.getInstance().token.await()
                Log.d("QAZ", "signIn: $fcmToken")
                val s=authRepository.signIn(email, password,fcmToken)
                isLoggedIn.postValue(s)

            }catch (e:Exception){
                when{
                    e is IOException ->{
                        events.postValue(Event.ConnectionError)
                    }
                    else->{
                        events.postValue(Event.Error)
                    }
                }
            }finally {
                _loading.postValue(false)
            }
        }

    }


    sealed class Event{
        object ConnectionError: Event()
        object Error: Event()
    }


}