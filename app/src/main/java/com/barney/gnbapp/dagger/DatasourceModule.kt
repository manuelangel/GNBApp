package com.barney.gnbapp.dagger

import com.barney.gnbapp.data.repository.datasource.ProductsNetworkDS
import com.barney.gnbapp.data.repository.datasource.RatesNetworkDS
import com.barney.gnbapp.data.repository.datasource.impl.ProductsNetworkDSImpl
import com.barney.gnbapp.data.repository.datasource.impl.RatesNetworkDSImpl
import dagger.Module
import dagger.Provides

@Module
class DatasourceModule {

    @Provides
    fun provideProductsDS(): ProductsNetworkDS = ProductsNetworkDSImpl()

    @Provides
    fun provideRatesDS(): RatesNetworkDS = RatesNetworkDSImpl()
}