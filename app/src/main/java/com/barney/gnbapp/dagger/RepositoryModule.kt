package com.barney.gnbapp.dagger

import com.barney.gnbapp.data.repository.ProductsRepository
import com.barney.gnbapp.data.repository.RatesRepository
import com.barney.gnbapp.data.repository.datasource.ProductsCacheDS
import com.barney.gnbapp.data.repository.datasource.ProductsNetworkDS
import com.barney.gnbapp.data.repository.datasource.RatesCacheDS
import com.barney.gnbapp.data.repository.datasource.RatesNetworkDS
import com.barney.gnbapp.data.repository.impl.ProductsRepoImpl
import com.barney.gnbapp.data.repository.impl.RatesRepoImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class RepositoryModule {

    @Reusable
    @Provides
    fun provideProductsRepository(datasource: ProductsNetworkDS,cacheDatasource: ProductsCacheDS): ProductsRepository =
        ProductsRepoImpl(datasource,cacheDatasource)

    @Reusable
    @Provides
    fun provideRatesRepository(networkDS: RatesNetworkDS,cacheDS: RatesCacheDS): RatesRepository =
        RatesRepoImpl(networkDS,cacheDS)
}