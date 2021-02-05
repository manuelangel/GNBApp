package com.barney.gnbapp.features.navigation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.barney.gnbapp.R
import com.barney.gnbapp.base.getGNBAppApplication
import com.barney.gnbapp.dagger.DaggerViewModelFactory
import com.barney.gnbapp.features.navigation.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getGNBAppApplication().component.inject(this)

        viewModel = ViewModelProvider(this,daggerViewModelFactory).get(MainViewModel::class.java)
        val h = 2
    }
}