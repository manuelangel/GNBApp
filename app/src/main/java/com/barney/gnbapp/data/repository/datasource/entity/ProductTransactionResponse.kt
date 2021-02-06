package com.barney.gnbapp.data.repository.datasource.entity

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class ProductTransactionResponse(
    @SerializedName("sku") val productCode: String,
    @SerializedName("amount") val amount: BigDecimal,
    @SerializedName("currency") val currencyCode: String
)
