package com.barney.gnbapp.data.repository.datasource.retrofit

import com.barney.gnbapp.data.repository.datasource.entity.RateResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface RatesServices {

    @Headers("Accept: application/json")
    @GET("rates")
    fun getRates(): Single<List<RateResponse>>
}