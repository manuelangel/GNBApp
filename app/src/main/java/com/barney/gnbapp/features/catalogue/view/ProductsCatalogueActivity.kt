package com.barney.gnbapp.features.catalogue.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.barney.gnbapp.R
import com.barney.gnbapp.base.getGNBAppApplication
import com.barney.gnbapp.dagger.DaggerViewModelFactory
import com.barney.gnbapp.features.catalogue.view.adapter.ProductsAdapter
import com.barney.gnbapp.features.catalogue.viewmodel.ProductsCatalogueVM
import com.barney.gnbapp.features.transactions.view.ProductTransactionsActivity
import kotlinx.android.synthetic.main.activity_products_catalogue.*
import javax.inject.Inject

class ProductsCatalogueActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private lateinit var viewModel: ProductsCatalogueVM

    private val adapter: ProductsAdapter = ProductsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_catalogue)

        getGNBAppApplication().component.inject(this)

        viewModel =
            ViewModelProvider(this, daggerViewModelFactory).get(ProductsCatalogueVM::class.java)

        setListeners()
        setObservers()
        configureRecyclerView()
        viewModel.loadProducts()
    }

    private fun setListeners() {
        adapter.listener = object : ProductsAdapter.OnProductSelectedListener {
            override fun onProductSelected(productCode: String) {
                //Toast.makeText(this@ProductsCatalogueActivity, "Product code: $productCode", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ProductsCatalogueActivity, ProductTransactionsActivity::class.java)
                        .apply {
                            putExtra(
                                ProductTransactionsActivity.PRODUCT_CODE_BUNDLE_KEY,
                                productCode
                            )
                        }
                startActivity(intent)
            }

        }
    }

    private fun configureRecyclerView() {
        products_catalogue_recycler_view.setHasFixedSize(true)
        products_catalogue_recycler_view.layoutManager = LinearLayoutManager(this)
        products_catalogue_recycler_view.adapter = adapter
    }

    private fun setObservers() {
        viewModel.products.observe(this,
            { products ->
                adapter.setData(products)
                Toast.makeText(this, "Numero de productos: " + products.size, Toast.LENGTH_LONG)
                    .show()
            })
    }
}