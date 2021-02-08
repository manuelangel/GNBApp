package com.barney.gnbapp.features.catalogue.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.barney.gnbapp.R
import com.barney.gnbapp.features.catalogue.view.adapter.viewholder.ProductVH

class ProductsAdapter : RecyclerView.Adapter<ProductVH>() {

    @LayoutRes
    private val productLayout = R.layout.compound_product

    private var productList: List<String>? = null

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

    fun setData(productList: List<String>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        return LayoutInflater
            .from(parent.context)
            .inflate(productLayout, parent, false)
            .let {
                ProductVH(it).apply {
                    setOnProductClick(productListener)
                }
            }
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.populate(productList!![position])
    }

    override fun getItemCount(): Int {
        return productList?.size ?: 0
    }

    interface OnProductSelectedListener{
        fun onProductSelected(productCode: String)
    }
}