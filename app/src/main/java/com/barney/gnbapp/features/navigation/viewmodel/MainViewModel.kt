package com.barney.gnbapp.features.navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.barney.gnbapp.base.BaseViewModel
import com.barney.gnbapp.data.repository.entity.ProductTransaction
import com.barney.gnbapp.data.repository.entity.Rate
import com.barney.gnbapp.features.navigation.domain.GetAllProductTransactionsUC
import com.barney.gnbapp.features.navigation.domain.GetRatesUC
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val transactionsUsecase: GetAllProductTransactionsUC,
    private val rateUsecase: GetRatesUC
) : BaseViewModel() {

    val transactions: MutableLiveData<List<ProductTransaction>> = MutableLiveData()
    val rates: MutableLiveData<List<Rate>> = MutableLiveData()

    fun loadTransactions() {
        addDisposable(
            transactionsUsecase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { transactionList -> transactions.value = transactionList }
                )
        )
    }

    fun loadRates() {
        addDisposable(
            rateUsecase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { rateList -> rates.value = rateList }
                )
        )
    }
}