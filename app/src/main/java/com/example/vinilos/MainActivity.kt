package com.example.vinilos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.vinilos.views.AlbumDetailsFragment
import com.example.vinilos.views.AlbumFragment
import com.example.vinilos.views.AlbumFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)
        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        bottomNavigation.setOnNavigationItemSelectedListener { menu ->

            when (menu.itemId) {

                R.id.navigation_albums -> {
                    navController.navigateUp()
                    true
                }

                R.id.navigation_musicos -> {
                    //setFragment(MusiciansFragment())
                    true
                }

                R.id.navigation_coleccionistas -> {
                    //setFragment(frag3())
                    true
                }
                R.id.navigation_premios ->{
                    //setFragment(frag3())
                    true
                }

                else -> false
            }
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    fun setFragment(fr : Fragment){
        val frag = supportFragmentManager.beginTransaction()
        frag.replace(R.id.nav_host_fragment,fr)
        frag.commit()
    }
}