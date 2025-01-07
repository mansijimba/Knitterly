package com.example.knitterly.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.knitterly.R
import com.example.knitterly.databinding.ActivityDashboardBinding
import com.example.mobileapplication.R
import com.example.mobileapplication.databinding.ActivityDashboardBinding
import com.example.mobileapplication.ui.fragment.CartFragment
import com.example.mobileapplication.ui.fragment.HomeFragment
import com.example.mobileapplication.ui.fragment.ProfileFragment
import com.example.mobileapplication.ui.fragment.sensorActivity

class DashboardActivity : AppCompatActivity() {

    lateinit var dashboardBinding: ActivityDashboardBinding
//    private lateinit var accelerometerHandler: sensorActivity.AccelerometerHandler

    private fun replaceFragemnt(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragementTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragementTransaction.replace(R.id.frameLayout, fragment)
        fragementTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        dashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(dashboardBinding.root)

        replaceFragemnt(HomeFragment())
        dashboardBinding.btnNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> replaceFragemnt(HomeFragment())
                R.id.menuCart -> replaceFragemnt(CartFragment())
                R.id.menuProfile -> replaceFragemnt(ProfileFragment())
                else -> {}
            }
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }
    override fun onDestroy() {
        super.onDestroy()
//        accelerometerHandler.cleanup()
    }


}