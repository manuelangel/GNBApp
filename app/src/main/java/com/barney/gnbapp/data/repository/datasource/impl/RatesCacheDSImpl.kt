package com.barney.gnbapp.data.repository.datasource.impl

import com.barney.gnbapp.data.repository.datasource.RatesCacheDS
import com.barney.gnbapp.data.repository.datasource.entity.RateResponse
import io.reactivex.rxjava3.core.Single

class RatesCacheDSImpl: RatesCacheDS {

    private var rateResponseList: List<RateResponse>? = null

    override fun getRates(): Single<List<RateResponse>> {
        return Single.just(rateResponseList ?: mutableListOf())
    }

    override fun hasData(): Boolean = rateResponseList != null

    override fun setData(rateResponseList: List<RateResponse>) {
        this.rateResponseList = rateResponseList
    }

}