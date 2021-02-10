package com.barney.gnbapp.data.repository.datasource.impl

import com.barney.gnbapp.data.repository.datasource.ProductsNetworkDS
import com.barney.gnbapp.data.repository.datasource.entity.ProductTransactionResponse
import com.barney.gnbapp.data.repository.datasource.retrofit.ProductsServices
import com.barney.gnbapp.data.repository.datasource.tools.GNBAppRetrofit
import io.reactivex.rxjava3.core.Single

class ProductsNetworkDSImpl: ProductsNetworkDS {

    override fun getAllProductTransactions():
            Single<List<ProductTransactionResponse>> =
        GNBAppRetrofit.getClient(ProductsServices::class.java)
        .getProductTransactions()
}