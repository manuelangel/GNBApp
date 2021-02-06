package com.barney.gnbapp.data.repository.datasource.impl

import com.barney.gnbapp.data.repository.datasource.RatesNetworkDS
import com.barney.gnbapp.data.repository.datasource.entity.RateResponse
import com.barney.gnbapp.data.repository.datasource.retrofit.RatesServices
import com.barney.gnbapp.data.repository.datasource.tools.GNBAppRetrofit
import io.reactivex.rxjava3.core.Single

class RatesNetworkDSImpl: RatesNetworkDS {

    override fun getRates(): Single<List<RateResponse>> =
        GNBAppRetrofit.getClient(RatesServices::class.java)
        .getRates()

}