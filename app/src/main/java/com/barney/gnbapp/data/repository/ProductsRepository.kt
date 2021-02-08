package com.barney.gnbapp.data.repository

import com.barney.gnbapp.data.repository.entity.ProductTransaction
import io.reactivex.rxjava3.core.Single

interface ProductsRepository {

    fun getProducts() : Single<List<String>>
    fun getProductTransactions(productCode: String): Single<List<ProductTransaction>>
}