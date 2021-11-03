package com.example.vinilos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.vinilos.ui.AlbumFragment
import com.example.vinilos.ui.MusiciansFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        setFragment(AlbumFragment())
        bottomNavigation.setOnNavigationItemSelectedListener { menu ->

            when (menu.itemId) {

                R.id.navigation_albums -> {
                    setFragment(AlbumFragment())
                    true
                }

                R.id.navigation_musicos -> {
                    setFragment(MusiciansFragment())
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

    fun setFragment(fr : Fragment){
        val frag = supportFragmentManager.beginTransaction()
        frag.replace(R.id.nav_host_fragment,fr)
        frag.commit()
    }
}