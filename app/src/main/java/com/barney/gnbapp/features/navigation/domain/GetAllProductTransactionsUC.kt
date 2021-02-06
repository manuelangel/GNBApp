package com.barney.gnbapp.features.navigation.domain

import com.barney.gnbapp.data.repository.ProductsRepository
import com.barney.gnbapp.data.repository.entity.ProductTransaction
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAllProductTransactionsUC @Inject constructor(private val repo : ProductsRepository) {

    fun execute(): Single<List<ProductTransaction>> = repo.getAllProductTransactions()
}