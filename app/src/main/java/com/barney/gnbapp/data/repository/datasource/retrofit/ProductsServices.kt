package com.barney.gnbapp.data.repository.datasource.retrofit

import com.barney.gnbapp.data.repository.datasource.entity.ProductTransactionResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface ProductsServices {

    @Headers("Accept: application/json")
    @GET("transactions")
    fun getProductTransactions() : Single<List<ProductTransactionResponse>>
}