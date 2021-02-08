package com.barney.gnbapp.features.catalogue.view.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.compound_product.view.*

class ProductVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var productCode: String

    fun setOnProductClick(listener: OnProductClickListener) {
        itemView.product_layout.setOnClickListener {
            if(this::productCode.isInitialized){
                listener.onClick(productCode)
            }
        }
    }

    fun populate(productCode: String) {
        this.productCode = productCode
        itemView.product_text_code.text = productCode
    }

    interface OnProductClickListener {
        fun onClick(productCode:String)
    }
}