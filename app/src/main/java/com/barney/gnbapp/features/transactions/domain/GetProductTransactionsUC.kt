package com.barney.gnbapp.features.transactions.domain

import com.barney.gnbapp.data.repository.ProductsRepository
import com.barney.gnbapp.data.repository.RatesRepository
import com.barney.gnbapp.data.repository.entity.ProductTransaction
import com.barney.gnbapp.data.repository.entity.Rate
import com.barney.gnbapp.features.transactions.domain.model.TransactionUI
import com.barney.gnbapp.tools.AmountFormatter
import io.reactivex.rxjava3.core.Single
import java.math.BigDecimal
import javax.inject.Inject

class GetProductTransactionsUC @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val ratesRepository: RatesRepository
) {

    private val MAIN_CURRENCY: String = "EUR"

    fun execute(productCode: String): Single<Pair<List<TransactionUI>, TransactionUI>> {
        return Single.zip(
            productsRepository.getProductTransactions(productCode),
            ratesRepository.getRates(),
            { productTransactions, rates -> prepareTransactions(productTransactions, rates) })
    }

    private fun prepareTransactions(
        productTransactions: List<ProductTransaction>,
        rates: List<Rate>
    ): Pair<List<TransactionUI>, TransactionUI> {
        val (ratesMap, directConversionsMap) = generateRatesMap(rates)
        var totalAmount = BigDecimal(0)
        val transactionUIList = mutableListOf<TransactionUI>()

        productTransactions.forEach {

            if (directConversionsMap.containsKey("${it.currencyCode}$MAIN_CURRENCY")) {
                convertDirectlyAndRoundHalfEven(it, MAIN_CURRENCY, directConversionsMap)
            } else if (it.currencyCode != MAIN_CURRENCY) {
                convertIndirectly(it, MAIN_CURRENCY, ratesMap, directConversionsMap)
            }

            totalAmount += it.amount
            transactionUIList.add(
                TransactionUI(
                    AmountFormatter.formatAmountToString(it.amount),
                    it.currencyCode
                )
            )
        }

        return Pair(
            transactionUIList,
            TransactionUI(
                AmountFormatter.formatAmountToString(totalAmount),
                MAIN_CURRENCY
            )
        )
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
    ): List<String> = getPath(mutableListOf(goalCurrency), initialCurrency, ratesMap).asReversed()

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

    /**
     * This method generate 2 different maps.
     * - Map<String,List<String>> contains a currency code as a key and a list of possible origin currencies as value.
     * It's a simplified representation of a decision tree rates
     *
     * - Map<String,BigDecimal> contains a combination of currency codes for a rate
     * (ORIGIN_CURRENCY+DESTINY_CURRENCY) as key, and  it's rate as value.
     */
    private fun generateRatesMap(rates: List<Rate>): Pair<Map<String, List<String>>, Map<String, BigDecimal>> {
        val rateOptionMap = mutableMapOf<String, MutableList<String>>()
        val directConversions = mutableMapOf<String, BigDecimal>()
        rates.forEach {
            directConversions["${it.fromCurrency}${it.toCurrency}"] = it.rate
            if (rateOptionMap.containsKey(it.toCurrency)) {
                if (rateOptionMap[it.toCurrency]!!.contains(it.fromCurrency).not()) {
                    rateOptionMap[it.toCurrency]!!.add(it.fromCurrency)
                }
            } else {
                mutableListOf(it.fromCurrency).let { mutableList ->
                    rateOptionMap[it.toCurrency] = mutableList
                }
            }
        }

        return Pair(rateOptionMap, directConversions)
    }
}