package com.barney.gnbapp.features.catalogue.view.model

sealed class ProductCatalogueScreen {
    data class Result(val productList: List<String>) : ProductCatalogueScreen()
    object Loading : ProductCatalogueScreen()
    data class Error(val throwable: Throwable) : ProductCatalogueScreen()
}
