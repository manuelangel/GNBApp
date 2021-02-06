package com.barney.gnbapp.data.repository

import com.barney.gnbapp.data.repository.entity.Rate
import io.reactivex.rxjava3.core.Single

interface RatesRepository {

    fun getRates(): Single<List<Rate>>
}