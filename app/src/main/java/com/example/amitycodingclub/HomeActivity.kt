package com.example.amitycodingclub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.toast


class HomeActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {


    private lateinit var navControler : NavController
    private lateinit var constants: Constants
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navControler = Navigation.findNavController(this,R.id.fragment)
        constants = Constants(this)
        bottomNav.setupWithNavController(navControler)
        drawer_navigation_view.setNavigationItemSelectedListener(this)
    }
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.profile ->{
                 toast("profile!")
            }
            R.id.settings ->{
                 toast("settings!")
            }
            R.id.about ->{
                 toast("about!")
            }
            R.id.exit ->{
                 toast("exit!")
            }
            R.id.logout ->{
                toast("You have been successfully logged out!")
                constants.logout()
            }
        }
        drawerlayout.closeDrawer(GravityCompat.START)
        return true
    }
}
