package com.k.shakhriyor.news.presentation

import android.util.Log
import android.util.Printer
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.k.shakhriyor.news.data.store.TokenStore
import com.k.shakhriyor.news.data.store.UiModeStore
import com.k.shakhriyor.news.util.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenStore: TokenStore,
    private val uiModeStore: UiModeStore
) : ViewModel() {



   val isLoggedIn= liveData {
       val token=tokenStore.getToken().firstOrNull()
       emit(!token.isNullOrEmpty()||!token.isNullOrBlank())
   }

    fun setUiMode()= viewModelScope.launch{
       val modeType= uiModeStore.getModeType().firstOrNull()?:AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        AppCompatDelegate.setDefaultNightMode(modeType)
    }
}