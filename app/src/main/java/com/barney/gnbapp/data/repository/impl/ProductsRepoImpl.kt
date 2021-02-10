package com.barney.gnbapp.data.repository.impl

import com.barney.gnbapp.data.repository.ProductsRepository
import com.barney.gnbapp.data.repository.datasource.ProductsCacheDS
import com.barney.gnbapp.data.repository.datasource.ProductsNetworkDS
import com.barney.gnbapp.data.repository.entity.ProductTransaction
import com.barney.gnbapp.data.repository.mapper.ProductMapper
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductsRepoImpl constructor(
    private val networkDS: ProductsNetworkDS,
    private val cacheDS: ProductsCacheDS
) : ProductsRepository {

    override fun getProducts(): Single<List<String>> {
        return if (cacheDS.hasData()) {
            cacheDS.getProducts()
                .subscribeOn(Schedulers.computation())
        } else {
            networkDS
                .getAllProductTransactions()
                .subscribeOn(Schedulers.io())
                .flatMap {
                    cacheDS.setData(it)
                    cacheDS.getProducts()
                }
        }
    }

    override fun getProductTransactions(productCode: String): Single<List<ProductTransaction>> {
        return if (cacheDS.hasData()) {
            cacheDS.getProductTransactions(productCode)
                .subscribeOn(Schedulers.computation())
        } else {
            networkDS
                .getAllProductTransactions()
                .subscribeOn(Schedulers.io())
                .flatMap {
                    cacheDS.setData(it)
                    cacheDS.getProductTransactions(it.first().productCode)
                }
        }
            .map { transaction -> transaction.map { ProductMapper.mapTo(it) } }
    }
}