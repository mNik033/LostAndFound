package com.ink.lnf.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ink.lnf.R
import com.ink.lnf.firebase.FirestoreClass
import com.ink.lnf.fragments.LostFragment
import com.ink.lnf.fragments.ProfileFragmentViewModel
import com.ink.lnf.models.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_lost.*

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController

    private val pfViewModel : ProfileFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragContainer) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        setupWithNavController(bottomNavigationView, navController)

        FirestoreClass().signInUser(this)
    }

    override fun onResume() {
        super.onResume()

        val fragmentManager = this.supportFragmentManager
        fragmentManager.let {
            val currentFragment = fragmentManager.findFragmentById(R.id.fragContainer)
            currentFragment?.let {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.detach(it).commitNow()
                fragmentTransaction.attach(it).commitNow()
            }
        }

    }

    fun updateUserDetails(user: User) {
        pfViewModel.setName(user.name)
        pfViewModel.setEmail(user.email)
        pfViewModel.setImage(user.image)
        pfViewModel.setMobile(user.mobile)
    }

}