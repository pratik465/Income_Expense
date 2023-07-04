package com.pachchham.income_expense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.khatabook2.Fragment.Add_Fragment
import com.example.khatabook2.Fragment.Home_Fragment
import com.example.khatabook2.Fragment.Trans_Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pachchham.income_expense.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var database: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(Home_Fragment())
        binding.BNV.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {

            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                loadFragment(Home_Fragment())

                when (item.itemId) {
                    R.id.Home -> {
                        loadFragment(Home_Fragment())
                    }

                    R.id.Add -> {
                        loadFragment(Add_Fragment())
                    }

                    R.id.Chart -> {
                        loadFragment(Trans_Fragment())
                    }
                }

                return true
            }

        })

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(Home_Fragment())
        binding.BNV.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.Home -> loadFragment(Home_Fragment())
                R.id.Add -> loadFragment(Add_Fragment())
                R.id.Trans -> loadFragment(Trans_Fragment())
                else -> {

                }
            }
            true
        }

    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragfram, fragment).commit()

    }

    companion object {
        lateinit var binding: ActivityMainBinding
        fun change(i: Int) {
            binding.BNV.selectedItemId = R.id.Add
        }
    }

}