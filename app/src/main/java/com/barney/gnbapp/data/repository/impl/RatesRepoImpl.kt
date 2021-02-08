package com.barney.gnbapp.data.repository.impl

import com.barney.gnbapp.data.repository.RatesRepository
import com.barney.gnbapp.data.repository.datasource.RatesCacheDS
import com.barney.gnbapp.data.repository.datasource.RatesNetworkDS
import com.barney.gnbapp.data.repository.entity.Rate
import com.barney.gnbapp.data.repository.mapper.RatesMapper
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RatesRepoImpl @Inject constructor(
    private val networkDS: RatesNetworkDS,
    private val cacheDS: RatesCacheDS) : RatesRepository {

    override fun getRates(): Single<List<Rate>> {
        return if(cacheDS.hasData()){
            cacheDS.getRates()
                .subscribeOn(Schedulers.computation())
        }
        else{
            networkDS
                .getRates()
                .subscribeOn(Schedulers.io())
                .map {
                    cacheDS.setData(it)
                    it
                }

        }.map { rateList ->
            rateList.map { rateResponse -> RatesMapper.mapTo(rateResponse) }
        }
    }

}