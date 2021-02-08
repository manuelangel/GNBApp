package com.barney.gnbapp.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.barney.gnbapp.features.catalogue.viewmodel.ProductsCatalogueVM
import com.barney.gnbapp.features.transactions.viewmodel.ProductTransactionsVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(impl: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductsCatalogueVM::class)
    abstract fun bindProductsCatalogueVM(viewModel: ProductsCatalogueVM): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ProductTransactionsVM::class)
    abstract fun bindProductTransactionsVM(viewModel: ProductTransactionsVM): ViewModel
}