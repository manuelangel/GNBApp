package com.barney.gnbapp.dagger

import com.barney.gnbapp.data.repository.datasource.ProductsDatasource
import com.barney.gnbapp.data.repository.datasource.impl.ProductsDSImpl
import dagger.Module
import dagger.Provides

@Module
class DatasourceModule {

    @Provides
    fun provideProductsDS(): ProductsDatasource = ProductsDSImpl()
}