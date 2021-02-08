package com.barney.gnbapp.features.transactions.viewmodel

import androidx.lifecycle.MutableLiveData
import com.barney.gnbapp.base.BaseViewModel
import com.barney.gnbapp.data.repository.entity.ProductTransaction
import com.barney.gnbapp.features.transactions.domain.GetProductTransactionsUC
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class ProductTransactionsVM @Inject constructor(
    private val productTransactionsUC: GetProductTransactionsUC
) : BaseViewModel() {

    val transactionsLiveData: MutableLiveData<List<ProductTransaction>> = MutableLiveData()

    fun loadProductTransactions(productCode: String) {
        addDisposable(
            productTransactionsUC.execute(productCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {transactionList -> transactionsLiveData.value= transactionList}
                )
        )
    }

}