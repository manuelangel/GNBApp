package com.barney.gnbapp.features.catalogue.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.barney.gnbapp.R
import com.barney.gnbapp.features.catalogue.view.adapter.viewholder.ProductVH

class ProductsAdapter : RecyclerView.Adapter<ProductVH>() {

    var productList: List<String> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    lateinit var listener: OnProductSelectedListener
    private val productListener = object:ProductVH.OnProductClickListener{
        override fun onClick(productCode: String) {
            onProductSelected(productCode)
        }
    }

    private fun onProductSelected(productCode: String) {
        if(this::listener.isInitialized){
            listener.onProductSelected(productCode)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.compound_product, parent, false)
            .let {
                ProductVH(it).apply {
                    setOnProductClick(productListener)
                }
            }
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.populate(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    interface OnProductSelectedListener{
        fun onProductSelected(productCode: String)
    }
}