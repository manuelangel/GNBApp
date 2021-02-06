package com.barney.gnbapp.data.repository.impl

import com.barney.gnbapp.data.repository.ProductsRepository
import com.barney.gnbapp.data.repository.datasource.ProductsNetworkDS
import com.barney.gnbapp.data.repository.datasource.entity.ProductTransactionResponse
import com.barney.gnbapp.data.repository.entity.ProductTransaction
import com.barney.gnbapp.data.repository.mapper.ProductMapper
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductsRepoImpl constructor(private val datasource: ProductsNetworkDS): ProductsRepository {

    override fun getAllProductTransactions(): Single<List<ProductTransaction>> {
        return datasource
            .getAllProductTransactions()
            .subscribeOn(Schedulers.io())
            .map { list ->
                list.map {
                    ProductMapper.mapTo(it)
                }
            }
    }
}