package com.barney.gnbapp.dagger

import com.barney.gnbapp.data.repository.ProductsRepository
import com.barney.gnbapp.data.repository.RatesRepository
import com.barney.gnbapp.data.repository.datasource.ProductsNetworkDS
import com.barney.gnbapp.data.repository.datasource.RatesNetworkDS
import com.barney.gnbapp.data.repository.impl.ProductsRepoImpl
import com.barney.gnbapp.data.repository.impl.RatesRepoImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideProductsRepository(datasource: ProductsNetworkDS): ProductsRepository =
        ProductsRepoImpl(datasource)

    @Provides
    fun provideRatesRepository(datasource: RatesNetworkDS): RatesRepository =
        RatesRepoImpl(datasource)
}