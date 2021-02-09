package com.barney.gnbapp.features.catalogue.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.barney.gnbapp.R
import com.barney.gnbapp.base.getGNBAppApplication
import com.barney.gnbapp.dagger.DaggerViewModelFactory
import com.barney.gnbapp.features.catalogue.view.adapter.ProductsAdapter
import com.barney.gnbapp.features.catalogue.view.model.ProductCatalogueScreen
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
                val intent =
                    Intent(this@ProductsCatalogueActivity, ProductTransactionsActivity::class.java)
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
        viewModel.screenState.observe(this,
            { screenState ->
                onScreenStateChange(screenState)
            })
    }

    private fun onScreenStateChange(screenState: ProductCatalogueScreen?) {
        when (screenState) {
            is ProductCatalogueScreen.Error -> showError(screenState)
            is ProductCatalogueScreen.Loading -> showLoadingMode()
            is ProductCatalogueScreen.Result -> showResult(screenState)
        }
    }

    private fun showError(screenState: ProductCatalogueScreen.Error) {
        TODO("Not yet implemented")
    }

    private fun showLoadingMode() {
        products_catalogue_loading_progress_bar.visibility = View.VISIBLE
        products_catalogue_loading_text.visibility = View.VISIBLE
        products_catalogue_recycler_view.visibility = View.GONE
    }

    private fun hideLoadingMode() {
        products_catalogue_loading_progress_bar.visibility = View.GONE
        products_catalogue_loading_text.visibility = View.GONE
        products_catalogue_recycler_view.visibility = View.VISIBLE
    }

    private fun showResult(screenState: ProductCatalogueScreen.Result) {
        adapter.productList = screenState.productList
        hideLoadingMode();
    }
}