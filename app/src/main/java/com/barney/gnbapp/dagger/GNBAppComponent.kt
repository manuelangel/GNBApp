package com.barney.gnbapp.dagger

import com.barney.gnbapp.features.catalogue.view.ProductsCatalogueActivity
import com.barney.gnbapp.features.transactions.view.ProductTransactionsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [], modules = [ViewModelModule::class,RepositoryModule::class,DatasourceModule::class])
interface GNBAppComponent {

    fun inject(productsCatalogueActivity: ProductsCatalogueActivity)
    fun inject(productTransactionsActivity: ProductTransactionsActivity)
}