package com.barney.gnbapp.data.repository

import com.barney.gnbapp.data.repository.entity.ProductTransaction
import io.reactivex.rxjava3.core.Single

interface ProductsRepository {

    fun getAllProductTransactions() : Single<List<ProductTransaction>>
}