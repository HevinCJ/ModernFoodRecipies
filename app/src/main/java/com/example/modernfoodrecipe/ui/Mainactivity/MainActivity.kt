package com.example.modernfoodrecipe.ui.Mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.modernfoodrecipe.R
import com.example.modernfoodrecipe.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class   MainActivity : AppCompatActivity() {

    private  var binding: ActivityMainBinding ?=null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        navController = findNavController(R.id.nav_host_fragment)
        val appconfig = AppBarConfiguration(setOf(
            R.id.recipe_fragment, R.id.favourite_fragment, R.id.foodjoke_fragment
        ))
        binding?.bottomNavigationView?.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appconfig)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()||super.onSupportNavigateUp()
    }
}