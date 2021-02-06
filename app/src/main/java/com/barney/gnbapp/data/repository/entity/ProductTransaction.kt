package com.barney.gnbapp.data.repository.entity

import java.math.BigDecimal

data class ProductTransaction(
    val productCode: String,
    val amount: BigDecimal,
    val currencyCode: String
)
