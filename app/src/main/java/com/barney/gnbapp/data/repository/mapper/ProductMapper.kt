package com.barney.gnbapp.data.repository.mapper

import com.barney.gnbapp.data.repository.datasource.entity.ProductTransactionResponse
import com.barney.gnbapp.data.repository.entity.ProductTransaction

object ProductMapper {

    fun mapTo(transactionResponse: ProductTransactionResponse)
            : ProductTransaction =
        ProductTransaction(
            transactionResponse.productCode,
            transactionResponse.amount,
            transactionResponse.currencyCode
        )

}