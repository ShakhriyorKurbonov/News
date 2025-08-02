package com.k.shakhriyor.news.presentation.intro_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.k.shakhriyor.news.R
import com.k.shakhriyor.news.util.makeStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)




    }
}