package com.barney.gnbapp.features.transactions.domain

import android.util.Log
import com.barney.gnbapp.data.repository.ProductsRepository
import com.barney.gnbapp.data.repository.RatesRepository
import com.barney.gnbapp.data.repository.entity.ProductTransaction
import com.barney.gnbapp.data.repository.entity.Rate
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetProductTransactionsUC @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val ratesRepository: RatesRepository
) {

    fun execute(productCode: String): Single<List<ProductTransaction>> {
        return Single.zip(
            productsRepository.getProductTransactions(productCode),
            ratesRepository.getRates(),
            { productTransactions, rates ->
                prepareTransactions(productTransactions, rates)
            })
    }

    private fun prepareTransactions(
        productTransactions: List<ProductTransaction>,
        rates: List<Rate>
    ): List<ProductTransaction> {
        val ratesMap = generateRatesMap(rates)
        productTransactions.forEach {
            if (it.currencyCode != "EUR") {
                searchEURConversionPath(it, ratesMap)
            }
        }
        return productTransactions
    }

    private fun searchEURConversionPath(
        productTransaction: ProductTransaction,
        ratesMap: Map<String, List<String>>
    ) {
        getPath(mutableListOf("EUR"), productTransaction.currencyCode, ratesMap).let {
            Log.i(
                "TEST_MANU",
                "Origin: ${productTransaction.currencyCode} List:" + it.joinToString(separator = "->")
            )
        }
    }

    private fun getPath(
        currentPath: MutableList<String>,
        searchedCurrency: String,
        ratesMap: Map<String, List<String>>
    ): List<String> {
        if (ratesMap[currentPath.last()]!!.contains(searchedCurrency)) {
            return currentPath.apply { add(searchedCurrency) }
        } else {
            var foundPath = listOf<String>()
            ratesMap[currentPath.last()]!!.forEach { nextCurrency ->
                if (currentPath.contains(nextCurrency).not()) {
                    val newPossiblePath = currentPath.toMutableList().apply { add(nextCurrency) }

                    getPath(newPossiblePath, searchedCurrency, ratesMap).let {
                        if (it.isEmpty().not()) {
                            foundPath = it
                            return@forEach
                        }
                    }
                }
            }

            return foundPath
        }
    }

    private fun generateRatesMap(rates: List<Rate>): Map<String, List<String>> {
        val map = mutableMapOf<String, MutableList<String>>()
        rates.forEach {
            if (map.containsKey(it.toCurrency)) {
                if (map[it.toCurrency]!!.contains(it.fromCurrency).not()) {
                    map[it.toCurrency]!!.add(it.fromCurrency)
                }
            } else {
                mutableListOf(it.fromCurrency).let { mutableList ->
                    map[it.toCurrency] = mutableList
                }
            }
        }

        return map
    }
}