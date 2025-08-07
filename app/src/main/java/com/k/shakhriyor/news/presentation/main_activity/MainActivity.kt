package com.k.shakhriyor.news.presentation.main_activity

import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.k.shakhriyor.news.R
import com.k.shakhriyor.news.data.common.Constants
import com.k.shakhriyor.news.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigation=binding.bottomNavigation
        NavigationUI.setupWithNavController(bottomNavigation,navController)

        Log.d("QAZ", "onCreate: ${intent.getStringExtra(Constants.TITLE)}")

        if (intent.getStringExtra(Constants.TITLE)!=null){
            val bundle= Bundle().apply {
                putString(Constants.TITLE,intent.getStringExtra(Constants.TITLE))
                putString(Constants.DESCRIPTION,intent.getStringExtra(Constants.DESCRIPTION))
                putString(Constants.IMAGE,intent.getStringExtra(Constants.IMAGE))
                putString(Constants.POSTEDDATE,intent.getStringExtra(Constants.POSTEDDATE))
                putString(Constants.AUTHOR,intent.getStringExtra(Constants.AUTHOR))
                putBoolean(Constants.LIKE,intent.getBooleanExtra(Constants.LIKE,false))
            }
            navController.navigate(R.id.newsItemFragment,bundle)
        }



        binding.bottomNavigation.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = 0)
            insets
        }


    }
}