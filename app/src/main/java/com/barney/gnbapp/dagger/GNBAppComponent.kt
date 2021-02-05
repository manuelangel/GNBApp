package com.barney.gnbapp.dagger

import com.barney.gnbapp.features.navigation.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [], modules = [ViewModelModule::class,RepositoryModule::class,DatasourceModule::class])
interface GNBAppComponent {

    fun inject(listActivity: MainActivity)
}