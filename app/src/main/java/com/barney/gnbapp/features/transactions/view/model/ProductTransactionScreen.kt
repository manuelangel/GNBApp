package com.barney.gnbapp.features.transactions.view.model

import com.barney.gnbapp.features.transactions.domain.model.TransactionUI

sealed class ProductTransactionScreen{
    data class Result(val transactionList: List<TransactionUI>,val totalAmount: TransactionUI): ProductTransactionScreen()
    object Loading : ProductTransactionScreen()
    data class Error(val throwable: Throwable): ProductTransactionScreen()
}
