package com.barney.gnbapp.data.repository.datasource.entity

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class RateResponse(
    @SerializedName("from") val fromCurrency: String,
    @SerializedName("to") val toCurrency: String,
    @SerializedName("rate") val rate: BigDecimal,
)
