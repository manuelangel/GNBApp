package com.barney.gnbapp.features.transactions.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.barney.gnbapp.R
import com.barney.gnbapp.base.getGNBAppApplication
import com.barney.gnbapp.dagger.DaggerViewModelFactory
import com.barney.gnbapp.features.transactions.view.adapter.TransactionsAdapter
import com.barney.gnbapp.features.transactions.view.model.ProductTransactionScreen
import com.barney.gnbapp.features.transactions.viewmodel.ProductTransactionsVM
import kotlinx.android.synthetic.main.activity_product_transactions.*
import javax.inject.Inject

class ProductTransactionsActivity : AppCompatActivity() {

    companion object {
        const val PRODUCT_CODE_BUNDLE_KEY = "PRODUCT_CODE_BUNDLE_KEY"
    }

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private lateinit var viewModel: ProductTransactionsVM
    private lateinit var productCode: String

    private val adapter: TransactionsAdapter = TransactionsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_transactions)

        getGNBAppApplication().component.inject(this)

        viewModel =
            ViewModelProvider(this, daggerViewModelFactory).get(ProductTransactionsVM::class.java)

        setObservers()
        configureToolBar()
        configureRecyclerView()
        loadBundleData()
    }

    private fun setObservers() {
        viewModel.screenState.observe(this, { screenState -> onScreenStateChange(screenState) })
    }

    private fun onScreenStateChange(screenState: ProductTransactionScreen?) {
        when (screenState) {
            is ProductTransactionScreen.Result -> showResult(screenState)
            is ProductTransactionScreen.Loading -> showLoadingMode()
            is ProductTransactionScreen.Error -> showErrorMessage()
        }
    }

    private fun showResult(result: ProductTransactionScreen.Result) {
        adapter.transactionsList = result.transactionList
        product_transactions_total_amount.text = "${result.totalAmount.amount} ${result.totalAmount.currency}"
        hideLoadingMode()
    }

    private fun hideLoadingMode() {
        product_transactions_loading_progress_bar.visibility = View.GONE
        product_transactions_loading_text.visibility = View.GONE
        product_transactions_recycler_view.visibility = View.VISIBLE
    }

    private fun showLoadingMode() {
        product_transactions_loading_progress_bar.visibility = View.VISIBLE
        product_transactions_loading_text.visibility = View.VISIBLE
        product_transactions_recycler_view.visibility = View.GONE
    }

    private fun showErrorMessage() {
        hideLoadingMode()
        AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.generic_error_message))
            setPositiveButton(getString(R.string.generic_error_message_retry_button)) { _, _ -> viewModel.loadProductTransactions(productCode) }
            setNegativeButton(getString(R.string.generic_error_message_close_button)) { _, _ -> onBackPressed() }
            setCancelable(false)
        }.show()
    }

    private fun configureToolBar() {
        setSupportActionBar(product_transactions_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun configureRecyclerView() {
        product_transactions_recycler_view.setHasFixedSize(true)
        product_transactions_recycler_view.layoutManager = LinearLayoutManager(this)
        product_transactions_recycler_view.adapter = adapter
    }

    private fun loadBundleData() {
        intent.extras?.getString(PRODUCT_CODE_BUNDLE_KEY)?.let {
            productCode = it
            supportActionBar?.title = "\"$it\" transactions"
            viewModel.loadProductTransactions(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}