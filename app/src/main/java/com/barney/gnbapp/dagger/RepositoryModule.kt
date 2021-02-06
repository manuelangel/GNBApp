package com.barney.gnbapp.dagger

import com.barney.gnbapp.data.repository.ProductsRepository
import com.barney.gnbapp.data.repository.datasource.ProductsDatasource
import com.barney.gnbapp.data.repository.impl.ProductsRepoImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideProductsRepository(datasource: ProductsDatasource):ProductsRepository = ProductsRepoImpl(datasource)
}