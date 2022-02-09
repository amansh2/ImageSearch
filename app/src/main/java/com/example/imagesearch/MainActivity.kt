package com.example.imagesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController=findNavController(R.id.nav_host_fragment_main)
        val appBarConfiguration= AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController,appBarConfiguration)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),20)



    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()||navController.navigateUp()

    }
}