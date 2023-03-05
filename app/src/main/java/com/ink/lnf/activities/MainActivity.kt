package com.ink.lnf.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.ink.lnf.R
import com.ink.lnf.fragments.FoundFragment
import com.ink.lnf.fragments.LostFragment
import com.ink.lnf.fragments.ProfileFragment
import kotlinx.android.synthetic.main.fragment_lost.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lostFragment = LostFragment()
        val foundFragment = FoundFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(lostFragment)

        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.lost -> {
                    setCurrentFragment(lostFragment)
                    // Respond to navigation item 1 click
                    true
                }
                R.id.found -> {
                    setCurrentFragment(foundFragment)
                    // Respond to navigation item 2 click
                    true
                }
                R.id.profile -> {
                    setCurrentFragment(profileFragment)
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

}