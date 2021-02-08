package com.barney.gnbapp.dagger

import com.barney.gnbapp.data.repository.datasource.ProductsCacheDS
import com.barney.gnbapp.data.repository.datasource.ProductsNetworkDS
import com.barney.gnbapp.data.repository.datasource.RatesCacheDS
import com.barney.gnbapp.data.repository.datasource.RatesNetworkDS
import com.barney.gnbapp.data.repository.datasource.impl.ProductsCacheDSImpl
import com.barney.gnbapp.data.repository.datasource.impl.ProductsNetworkDSImpl
import com.barney.gnbapp.data.repository.datasource.impl.RatesCacheDSImpl
import com.barney.gnbapp.data.repository.datasource.impl.RatesNetworkDSImpl
import dagger.Module
import dagger.Provides

@Module
class DatasourceModule {

    @Provides
    fun provideProductsNetworkDS(): ProductsNetworkDS = ProductsNetworkDSImpl()

    @Provides
    fun provideProductsCacheDS(): ProductsCacheDS = ProductsCacheDSImpl()

    @Provides
    fun provideRatesNetworkDS(): RatesNetworkDS = RatesNetworkDSImpl()

    @Provides
    fun provideRatesCacheDS(): RatesCacheDS = RatesCacheDSImpl()
}