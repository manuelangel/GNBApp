package com.barney.gnbapp.features.transactions.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barney.gnbapp.R
import com.barney.gnbapp.features.transactions.domain.model.TransactionUI
import com.barney.gnbapp.features.transactions.view.adapter.viewholder.TransactionVH

class TransactionsAdapter : RecyclerView.Adapter<TransactionVH>() {

    var transactionsList: List<TransactionUI> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionVH {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.compound_transaction, parent, false)
            .let {
                TransactionVH(it)
            }
    }

    override fun onBindViewHolder(holder: TransactionVH, position: Int) {
        holder.populate(position+1, transactionsList[position])
    }

    override fun getItemCount(): Int {
        return transactionsList.size
    }
}