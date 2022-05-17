package com.kuzyava.habittrackerapp

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kuzyava.habittrackerapp.databinding.ActivityMainBinding
import com.kuzyava.habittrackerapp.databinding.NavHeaderMainBinding
import com.kuzyava.habittrackerapp.di.ListComponent

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var listComponent: ListComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listComponent =
            (application as HabitsApplication).applicationComponent.listComponent().create()
        listComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_info), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val header = navView.getHeaderView(0)
        val bindingHeader: NavHeaderMainBinding = NavHeaderMainBinding.bind(header)
        Glide
            .with(this)
            .load("https://urfu.ru/fileadmin/user_upload/common_files/about/brand/UrFULogo_U.jpg")
            .circleCrop()
            .placeholder(R.drawable.ic_downloading)
            .error(R.drawable.ic_error)
            .into(bindingHeader.imageView)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}