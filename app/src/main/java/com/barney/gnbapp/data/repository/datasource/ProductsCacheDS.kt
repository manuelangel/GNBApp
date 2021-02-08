package com.barney.gnbapp.data.repository.datasource

import com.barney.gnbapp.data.repository.datasource.entity.ProductTransactionResponse
import io.reactivex.rxjava3.core.Single

interface ProductsCacheDS {

    fun setData(transactionResponseList : List<ProductTransactionResponse>)
    fun hasData(): Boolean
    fun getProducts(): Single<List<String>>
    fun getProductTransactions(productCode: String): Single<List<ProductTransactionResponse>>

}