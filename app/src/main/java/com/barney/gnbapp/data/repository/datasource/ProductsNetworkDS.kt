package com.barney.gnbapp.data.repository.datasource

import com.barney.gnbapp.data.repository.datasource.entity.ProductTransactionResponse
import io.reactivex.rxjava3.core.Single

interface ProductsNetworkDS {

    fun getAllProductTransactions() : Single<List<ProductTransactionResponse>>
}