package com.barney.gnbapp.features.transactions.viewmodel

import androidx.lifecycle.MutableLiveData
import com.barney.gnbapp.base.BaseViewModel
import com.barney.gnbapp.features.transactions.domain.GetProductTransactionsUC
import com.barney.gnbapp.features.transactions.view.model.ProductTransactionScreen
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class ProductTransactionsVM @Inject constructor(
    private val productTransactionsUC: GetProductTransactionsUC
) : BaseViewModel() {

    val screenState: MutableLiveData<ProductTransactionScreen> = MutableLiveData()

    fun loadProductTransactions(productCode: String) {
        addDisposable(
            productTransactionsUC.execute(productCode)
                .doOnSubscribe{
                    screenState.value = ProductTransactionScreen.Loading
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { (transactionList, totalAmountTransaction) ->
                        screenState.value = ProductTransactionScreen.Result(transactionList,totalAmountTransaction)
                    },
                    onError = {
                        screenState.value = ProductTransactionScreen.Error(it)
                    }
                )
        )
    }

}