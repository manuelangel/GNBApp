package com.barney.gnbapp.features.navigation.viewmodel

import com.barney.gnbapp.base.BaseViewModel
import com.barney.gnbapp.data.repository.ProductsRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repo: ProductsRepository) : BaseViewModel() {

}