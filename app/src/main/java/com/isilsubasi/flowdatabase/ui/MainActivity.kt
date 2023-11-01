package com.isilsubasi.flowdatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.isilsubasi.flowdatabase.R
import com.isilsubasi.flowdatabase.databinding.ActivityMainBinding
import com.isilsubasi.flowdatabase.ui.addFragment.AddContactFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            //setSupportActionBar(toolbar)

            btnShowDialog.setOnClickListener {
                AddContactFragment().show(supportFragmentManager,AddContactFragment().tag)
            }
        }


    }
}