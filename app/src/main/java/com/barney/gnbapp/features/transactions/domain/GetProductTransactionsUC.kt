package com.barney.gnbapp.features.transactions.domain

import android.util.Log
import com.barney.gnbapp.data.repository.ProductsRepository
import com.barney.gnbapp.data.repository.RatesRepository
import com.barney.gnbapp.data.repository.entity.ProductTransaction
import com.barney.gnbapp.data.repository.entity.Rate
import io.reactivex.rxjava3.core.Single
import java.math.BigDecimal
import javax.inject.Inject

class GetProductTransactionsUC @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val ratesRepository: RatesRepository
) {

    private val MAIN_CURRENCY: String = "EUR"

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
        val (ratesMap, directConversionsMap) = generateRatesMap(rates)
        productTransactions.forEach {
            if (directConversionsMap.containsKey("${it.currencyCode}$MAIN_CURRENCY")) {
                convertDirectlyAndRoundHalfEven(it, MAIN_CURRENCY, directConversionsMap)
            } else if (it.currencyCode != MAIN_CURRENCY) {
                convertIndirectly(it, MAIN_CURRENCY, ratesMap, directConversionsMap)
            }
        }
        return productTransactions
    }

    private fun convertIndirectly(
        transaction: ProductTransaction,
        goalCurrency: String,
        ratesMap: Map<String, List<String>>,
        directConversionsMap: Map<String, BigDecimal>
    ) {
        val path = searchEURConversionPath(transaction.currencyCode, goalCurrency, ratesMap)

        path.forEach { currentCurrency ->
            if (transaction.currencyCode != currentCurrency) {
                convertDirectly(transaction, currentCurrency, directConversionsMap)
            }
        }

        transaction.apply {
            amount = amount.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        }
    }

    private fun convertDirectlyAndRoundHalfEven(
        transaction: ProductTransaction,
        newCurrency: String,
        directConversionsMap: Map<String, BigDecimal>
    ) {
        convertDirectly(transaction, newCurrency, directConversionsMap)
        transaction.apply {
            amount = amount.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        }
    }

    private fun convertDirectly(
        transaction: ProductTransaction,
        newCurrency: String,
        directConversionsMap: Map<String, BigDecimal>
    ) {
        val rate = directConversionsMap["${transaction.currencyCode}$newCurrency"]!!
        transaction.apply {
            this.amount = (amount * rate)
            this.currencyCode = newCurrency
        }
    }

    private fun searchEURConversionPath(
        initialCurrency: String,
        goalCurrency: String,
        ratesMap: Map<String, List<String>>
    ): List<String> {
        return getPath(mutableListOf(goalCurrency), initialCurrency, ratesMap).let {
            Log.i(
                "TEST_MANU",
                "Origin: $initialCurrency List:" + it.joinToString(separator = "->")
            )
            it
        }.asReversed()
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

    private fun generateRatesMap(rates: List<Rate>): Pair<Map<String, List<String>>, Map<String, BigDecimal>> {
        val map = mutableMapOf<String, MutableList<String>>()
        val directConversions = mutableMapOf<String, BigDecimal>()
        rates.forEach {
            directConversions["${it.fromCurrency}${it.toCurrency}"] = it.rate
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

        return Pair(map, directConversions)
    }
}