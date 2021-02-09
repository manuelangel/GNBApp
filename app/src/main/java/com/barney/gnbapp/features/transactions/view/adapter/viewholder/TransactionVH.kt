package com.barney.gnbapp.features.transactions.view.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.barney.gnbapp.features.transactions.domain.model.TransactionUI
import kotlinx.android.synthetic.main.compound_transaction.view.*

class TransactionVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun populate(index: Int,transactionUI: TransactionUI){
        itemView.transaction_number.text = "Nº $index"
        itemView.transaction_amount.text = "${transactionUI.amount} ${transactionUI.currency}"
    }
}