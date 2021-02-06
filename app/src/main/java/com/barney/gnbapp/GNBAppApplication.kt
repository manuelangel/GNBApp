package com.barney.gnbapp

import android.app.Application
import com.barney.gnbapp.dagger.DaggerGNBAppComponent
import com.barney.gnbapp.dagger.GNBAppComponent

class GNBAppApplication : Application() {

    val component: GNBAppComponent by lazy {
        DaggerGNBAppComponent.create()
    }
}