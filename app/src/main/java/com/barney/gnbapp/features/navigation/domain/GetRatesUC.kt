package com.barney.gnbapp.features.navigation.domain

import com.barney.gnbapp.data.repository.RatesRepository
import com.barney.gnbapp.data.repository.entity.Rate
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetRatesUC @Inject constructor(private val repo: RatesRepository) {

    fun execute(): Single<List<Rate>> = repo.getRates()

}