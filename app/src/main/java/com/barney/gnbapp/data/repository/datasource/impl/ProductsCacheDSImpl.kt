package com.barney.gnbapp.data.repository.datasource.impl

import com.barney.gnbapp.data.repository.datasource.ProductsCacheDS
import com.barney.gnbapp.data.repository.datasource.entity.ProductTransactionResponse
import io.reactivex.rxjava3.core.Single

class ProductsCacheDSImpl : ProductsCacheDS {

    private var transactionResponseList: List<ProductTransactionResponse>? = null

    override fun setData(transactionResponseList: List<ProductTransactionResponse>) {
        this.transactionResponseList = transactionResponseList
    }

    override fun hasData(): Boolean {
        return transactionResponseList != null
    }

    override fun getProducts(): Single<List<String>> {
        return Single.create { emitter ->
            val productCodesList = transactionResponseList?.distinctBy { productTransactionResponse ->
                productTransactionResponse.productCode
            }?.
            map { it.productCode }

            if (!emitter.isDisposed) {
                emitter.onSuccess(productCodesList ?: mutableListOf())
            }
        }
    }

    override fun getProductTransactions(productCode: String): Single<List<ProductTransactionResponse>> {
        return Single.create { emitter ->
            val productTransactionList = transactionResponseList?.
            filter { productTransactionResponse ->
                productTransactionResponse.productCode == productCode
            }

            if (!emitter.isDisposed) {
                emitter.onSuccess(productTransactionList ?: mutableListOf())
            }
        }
    }
}