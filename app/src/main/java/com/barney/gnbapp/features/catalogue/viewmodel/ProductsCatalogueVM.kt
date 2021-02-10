package com.barney.gnbapp.features.catalogue.viewmodel

import androidx.lifecycle.MutableLiveData
import com.barney.gnbapp.base.BaseViewModel
import com.barney.gnbapp.features.catalogue.domain.GetProductsUC
import com.barney.gnbapp.features.catalogue.view.model.ProductCatalogueScreen
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class ProductsCatalogueVM @Inject constructor(
    private val productsUseCase: GetProductsUC
) : BaseViewModel() {

    val screenState: MutableLiveData<ProductCatalogueScreen> = MutableLiveData()

    fun loadProducts() {
        addDisposable(
            productsUseCase.execute()
                .doOnSubscribe { screenState.value = ProductCatalogueScreen.Loading }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { productList ->
                        screenState.value = ProductCatalogueScreen.Result(productList)
                    },
                    onError = { throwable ->
                        screenState.value = ProductCatalogueScreen.Error(throwable)
                    }
                )
        )
    }
}