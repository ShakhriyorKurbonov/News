package com.k.shakhriyor.news.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.k.shakhriyor.news.presentation.intro_activity.IntroActivity
import com.k.shakhriyor.news.presentation.main_activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity:AppCompatActivity() {
    private val viewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setUiMode()

        viewModel.isLoggedIn.observe(this){isLoggedIn->
            val intent=if (isLoggedIn)
                Intent(this,MainActivity::class.java)
            else
                Intent(this,IntroActivity::class.java)

            startActivity(intent)
            finish()
        }




    }
}