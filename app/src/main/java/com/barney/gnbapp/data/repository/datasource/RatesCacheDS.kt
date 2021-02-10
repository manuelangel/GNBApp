package com.barney.gnbapp.data.repository.datasource

import com.barney.gnbapp.data.repository.datasource.entity.RateResponse
import io.reactivex.rxjava3.core.Single

interface RatesCacheDS {
    fun getRates(): Single<List<RateResponse>>
    fun hasData(): Boolean
    fun setData(rateResponseList:List<RateResponse>)
}