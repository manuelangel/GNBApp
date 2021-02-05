package com.barney.gnbapp.data.repository.impl

import com.barney.gnbapp.data.repository.ProductsRepository
import com.barney.gnbapp.data.repository.datasource.ProductsDatasource

class ProductsRepoImpl constructor(private val datasource: ProductsDatasource): ProductsRepository {

    override fun getProducts() {
        TODO("Not yet implemented")
    }
}