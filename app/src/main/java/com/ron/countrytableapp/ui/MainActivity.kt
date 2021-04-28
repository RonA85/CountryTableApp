package com.ron.countrytableapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ron.countrytableapp.R
import com.ron.countrytableapp.ui.main.CountriesListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val countriesListFragment = CountriesListFragment.newInstance();
        supportFragmentManager.beginTransaction().add(R.id.container,countriesListFragment,"CountriesListFragment").commit()
    }

}