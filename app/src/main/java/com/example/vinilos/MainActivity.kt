package com.example.vinilos

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.vinilos.views.AlbumFragmentDirections
import com.example.vinilos.views.CollectorsListFragmentDirections
import com.example.vinilos.views.MusiciansListFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: ActionBar
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        //setSupportActionBar(findViewById(R.id.my_toolbar))

        NavigationUI.setupActionBarWithNavController(this, navController)
        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        bottomNavigation.setOnNavigationItemSelectedListener { menu ->

            when (menu.itemId) {

                R.id.navigation_albums -> {
                    val action = AlbumFragmentDirections.actionGlobalAlbumFragment()
                    navController.navigate(action)
                    true
                }

                R.id.navigation_musicos -> {
                    val action = MusiciansListFragmentDirections.actionGlobalMusiciansListFragment()
                    navController.navigate(action)
                    true
                }

                R.id.navigation_coleccionistas -> {
                    val action = CollectorsListFragmentDirections.actionGlobalCollectorsListFragment()
                    navController.navigate(action)
                    true
                }
                R.id.navigation_premios -> {
                    //setFragment(frag3())
                    true
                }

                else -> false
            }
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar, menu)

        val expandListener = MenuItem.OnMenuItemClickListener {
            val action = AlbumFragmentDirections.actionAlbumFragment2ToCreateAlbumFragment()
            navController.navigate(action)

            true
        }

        // Get the MenuItem for the action item
        val actionMenuItem = menu?.findItem(R.id.add_album)

        // Assign the listener to that action item
        actionMenuItem?.setOnMenuItemClickListener(expandListener)

        return super.onCreateOptionsMenu(menu)
    }

}