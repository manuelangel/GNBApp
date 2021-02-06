package com.barney.gnbapp.data.repository.entity

import java.math.BigDecimal

data class Rate(
    val fromCurrency: String,
    val toCurrency: String,
    val rate: BigDecimal
)
