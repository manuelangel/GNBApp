package com.barney.gnbapp.features.catalogue.viewmodel

import androidx.lifecycle.MutableLiveData
import com.barney.gnbapp.base.BaseViewModel
import com.barney.gnbapp.features.catalogue.domain.GetProductsUC
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class ProductsCatalogueVM @Inject constructor(
    private val productsUseCase: GetProductsUC
) : BaseViewModel() {

    val products: MutableLiveData<List<String>> = MutableLiveData()

    fun loadProducts() {
        addDisposable(
            productsUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { productList -> products.value = productList }
                )
        )
    }
}