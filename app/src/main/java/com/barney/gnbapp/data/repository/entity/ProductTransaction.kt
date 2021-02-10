package com.barney.gnbapp.data.repository.entity

import java.math.BigDecimal

data class ProductTransaction(
    val productCode: String,
    var amount: BigDecimal,
    var currencyCode: String
)
