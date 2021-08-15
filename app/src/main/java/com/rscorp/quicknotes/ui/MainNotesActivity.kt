package com.rscorp.quicknotes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rscorp.quicknotes.R
import com.rscorp.quicknotes.databinding.ActivityMainNewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNotesActivity : AppCompatActivity() {


    private lateinit var navController: NavController
    private lateinit var binding : ActivityMainNewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_main_new)

        navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
               R.id.homeNotesFragment,
                R.id.foodJokeFragment
            )
        )
        NavigationUI.setupWithNavController(binding.bottomNavigationView , navController)
//        binding.bottomNavigationView.setupWithNavController(navController)
//        setupActionBarWithNavController(navController , appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() ||  super.onSupportNavigateUp()
    }
}