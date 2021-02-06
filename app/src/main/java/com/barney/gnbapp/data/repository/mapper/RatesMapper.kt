package com.barney.gnbapp.data.repository.mapper

import com.barney.gnbapp.data.repository.datasource.entity.RateResponse
import com.barney.gnbapp.data.repository.entity.Rate

object RatesMapper {

    fun mapTo(rateResponse: RateResponse)
            : Rate =
        Rate(
            rateResponse.fromCurrency,
            rateResponse.toCurrency,
            rateResponse.rate
        )

}