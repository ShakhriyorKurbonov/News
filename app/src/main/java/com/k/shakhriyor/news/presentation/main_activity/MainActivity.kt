package com.k.shakhriyor.news.presentation.main_activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.k.shakhriyor.news.R
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

//        navController.addOnDestinationChangedListener { _,destination,_->
//            when(destination.id){
//                R.id.homeFragment,
//                    R.id.profileFragment,
//                    R.id.favoriteFragment->bottomNavigation.visibility= View.VISIBLE
//                else -> bottomNavigation.visibility= View.GONE
//            }
//        }


        binding.bottomNavigation.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = 0)
            insets
        }


    }
}