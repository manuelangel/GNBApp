package com.barney.gnbapp.features.catalogue.domain

import com.barney.gnbapp.data.repository.ProductsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetProductsUC @Inject constructor(private val productsRepo: ProductsRepository) {

    fun execute(): Single<List<String>> = productsRepo.getProducts()
}